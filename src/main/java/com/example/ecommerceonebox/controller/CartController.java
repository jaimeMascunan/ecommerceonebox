package com.example.ecommerceonebox.controller;

import com.example.ecommerceonebox.dto.CartDTO;
import com.example.ecommerceonebox.dto.ProductDTO;
import com.example.ecommerceonebox.exception.CartServiceCustomException;
import com.example.ecommerceonebox.model.Cart;
import com.example.ecommerceonebox.model.Product;
import com.example.ecommerceonebox.service.CartServiceImpl;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CartServiceImpl cartServiceImpl;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Map<Integer, CartDTO> listAllCarts() {
        // Obtener todos los carritos y mapear cada uno a su DTO correspondiente
        return cartServiceImpl.getAllCarts().values().stream()
                .collect(Collectors.toMap(Cart::getId, cart -> modelMapper.map(cart, CartDTO.class)));
    }

    @PostMapping
    public ResponseEntity<CartDTO> createCart() {
        Cart cart = cartServiceImpl.createCart();
        //Convert entity to DTO
        CartDTO cartResponse = modelMapper.map(cart, CartDTO.class);
        return new ResponseEntity<CartDTO>(cartResponse, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<CartDTO> getCartById(@Validated @PathVariable int id) {
        Cart cart = cartServiceImpl.getCartInformation(id);
        //Convert entity to DTO
        CartDTO cartResponse = modelMapper.map(cart, CartDTO.class);
        return ResponseEntity.ok().body(cartResponse);

    }

    @PostMapping("{cartId}/products")
    public ResponseEntity<CartDTO> addProductToCart(@Validated @PathVariable int cartId, @Valid @RequestBody ProductDTO productDTO) {
        try {
            Product product = modelMapper.map(productDTO, Product.class);
            Cart cart = cartServiceImpl.addProductToCart(cartId, product);
            CartDTO cartResponse = modelMapper.map(cart, CartDTO.class);
            return ResponseEntity.ok(cartResponse);
        } catch (CartServiceCustomException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("{cartId}/products")
    public ResponseEntity<CartDTO> updateProductInCart(@Validated @PathVariable int cartId, @Valid @RequestBody ProductDTO productDTO) {
        try {
            Product product = modelMapper.map(productDTO, Product.class);
            Cart cart = cartServiceImpl.updateProductFromCart(cartId, product);
            CartDTO cartResponse = modelMapper.map(cart, CartDTO.class);
            return ResponseEntity.ok(cartResponse);
        } catch (CartServiceCustomException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("{cartId}/products")
    public ResponseEntity<CartDTO> deleteProductFromCart(@Validated @PathVariable int cartId, @ Valid @RequestBody ProductDTO productDTO) {
        try {
            Product product = modelMapper.map(productDTO, Product.class);
            Cart cart = cartServiceImpl.deleteProductFromCart(cartId, product);
            CartDTO cartResponse = modelMapper.map(cart, CartDTO.class);
            return ResponseEntity.ok(cartResponse);
        } catch (CartServiceCustomException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("{cartId}")
    public ResponseEntity<CartDTO> deleteCart(@Validated @PathVariable int cartId){
        try {
            Cart cart = cartServiceImpl.deleteCart(cartId);
            CartDTO cartResponse = modelMapper.map(cart, CartDTO.class);
            return ResponseEntity.ok(cartResponse);
        } catch (CartServiceCustomException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
