package com.example.ecommerceonebox.controller;

import com.example.ecommerceonebox.dto.CartDTO;
import com.example.ecommerceonebox.dto.ProductDTO;
import com.example.ecommerceonebox.exception.CartServiceCustomException;
import com.example.ecommerceonebox.service.CartServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartServiceImpl cartServiceImpl;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Map<Integer, CartDTO> listAllCarts() {
        // Obtener todos los carritos y mapear cada uno a su DTO correspondiente
        return cartServiceImpl.getAllCarts();
    }

    @PostMapping
    public ResponseEntity<CartDTO> createCart(@Valid @RequestBody HashMap<Integer, ProductDTO> productDTOs) {
        CartDTO cartResponse = cartServiceImpl.createCart(productDTOs);
        return new ResponseEntity<CartDTO>(cartResponse, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<CartDTO> getCartById(@Validated @PathVariable int id) {
        CartDTO cartResponse = cartServiceImpl.getCartInformation(id);
        return ResponseEntity.ok().body(cartResponse);

    }

    @PostMapping("{cartId}/products")
    public ResponseEntity<CartDTO> addProductToCart(@Validated @PathVariable int cartId, @Valid @RequestBody HashMap<Integer, ProductDTO> productDTOs) {
        try {
            CartDTO cartResponse = cartServiceImpl.addProductToCart(cartId, productDTOs);
            return ResponseEntity.ok(cartResponse);
        } catch (CartServiceCustomException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("{cartId}/products")
    public ResponseEntity<CartDTO> updateProductInCart(@Validated @PathVariable int cartId, @Valid @RequestBody ProductDTO productDTO) {
        try {
            CartDTO cartResponse = cartServiceImpl.updateProductFromCart(cartId, productDTO);
            return ResponseEntity.ok(cartResponse);
        } catch (CartServiceCustomException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("{cartId}/products")
    public ResponseEntity<CartDTO> deleteProductFromCart(@Validated @PathVariable int cartId, @Valid @RequestBody ProductDTO productDTO) {
        try {
            CartDTO cartResponse = cartServiceImpl.deleteProductFromCart(cartId, productDTO);
            return ResponseEntity.ok(cartResponse);
        } catch (CartServiceCustomException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("{cartId}")
    public ResponseEntity<CartDTO> deleteCart(@Validated @PathVariable int cartId){
        try {
            CartDTO cartResponse = cartServiceImpl.deleteCart(cartId);
            return ResponseEntity.ok(cartResponse);
        } catch (CartServiceCustomException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
