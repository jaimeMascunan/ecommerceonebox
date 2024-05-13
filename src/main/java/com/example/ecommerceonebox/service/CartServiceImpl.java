package com.example.ecommerceonebox.service;

import com.example.ecommerceonebox.dto.CartDTO;
import com.example.ecommerceonebox.dto.ProductDTO;
import com.example.ecommerceonebox.exception.CartServiceCustomException;
import com.example.ecommerceonebox.model.Cart;
import com.example.ecommerceonebox.model.Product;
import com.example.ecommerceonebox.repository.CartRepository;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Log4j2
@EnableScheduling
public class CartServiceImpl implements CartService{
    @Autowired
    CartRepository cartRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Map<Integer, CartDTO> getAllCarts() {
        log.info("CartServiceImpl | getAllCarts is called");
        return cartRepository.getAllCarts().values().stream()
                .collect(Collectors.toMap(Cart::getId, cart -> modelMapper.map(cart, CartDTO.class)));
    }

    @Override
    public CartDTO createCart(HashMap<Integer, ProductDTO> productDTOs) {
        log.info("CartServiceImpl | createCartWithProducts is called");
        Cart newCart = cartRepository.createCart();
        CartDTO cartDTO = modelMapper.map(newCart, CartDTO.class);
        addProductToCart(cartDTO.getId(), productDTOs);
        log.info("CartServiceImpl | createCartWithProducts | Cart Id : {}", cartDTO.getId());
        return cartDTO;
    }

    @Override
    public CartDTO getCartInformation(int id) {
        log.info("CartServiceImpl | getCartInformation is called");
        Cart cart = cartRepository.getCartInformation(id);
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        if (cartDTO == null) {
            throw new CartServiceCustomException("Cart with given Id not found", "CART_NOT_FOUND");
        }
        log.info("CartServiceImpl | getCartInformation | Cart Id : {}", cartDTO.getId());
        return cartDTO;
    }

    @Override
    public CartDTO addProductToCart(int id, HashMap<Integer, ProductDTO> productDTOs) {
        log.info("CartServiceImpl | addProductToCart is called");
        HashMap<Integer, Product> products = new HashMap<>();
        productDTOs.forEach((productId, productDTO) -> {
            Product product = modelMapper.map(productDTO, Product.class);
            products.put(productId, product);
        });

        Cart cart = cartRepository.addProductToCart(id, products);
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        log.info("CartServiceImpl | addProductToCart | Cart Id : {}", cartDTO.getId());
        return cartDTO;
    }

    @Override
    public CartDTO updateProductFromCart(int cartId, ProductDTO productDTO) {
        log.info("CartServiceImpl | updateProductFromCart is called");
        Product product = modelMapper.map(productDTO, Product.class);
        Cart cart = cartRepository.updateProductFromCart(cartId, product);
        if (cart.getProducts().get(product.getId()) == null) {
            throw new CartServiceCustomException("Product in cart not updated", "PRODUCT_NOT_UPDATED");
        }
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        log.info("CartServiceImpl | updateProductFromCart | Cart Id : {}", cartDTO.getId());
        return cartDTO;
    }

    @Override
    public CartDTO deleteProductFromCart(int cartId, ProductDTO productDTO) {
        log.info("CartServiceImpl | deleteProductFromCart is called");
        Product product = modelMapper.map(productDTO, Product.class);
        Cart cart = cartRepository.deleteProductFromCart(cartId, product);
        if (cart == null) {
            throw new CartServiceCustomException("Product in cart not removed", "PRODUCT_NOT_DELETED");
        }
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        log.info("CartServiceImpl | deleteProductFromCart | Cart Id : {}", cartDTO.getId());
        return cartDTO;
    }

    @Override
    public CartDTO deleteCart(int cartId) {
        log.info("CartServiceImpl | deleteCart is called");
        Cart cartDeleted = cartRepository.deleteCart(cartId);
        if (cartDeleted == null) {
            throw new CartServiceCustomException("Cart with given Id not found", "CART_NOT_FOUND");
        }
        CartDTO cartDeletedDTO = modelMapper.map(cartDeleted, CartDTO.class);
        log.info("CartServiceImpl | deleteCart | Cart Id : {}", cartDeletedDTO.getId());
        return cartDeletedDTO;
    }

    @Override
    public void deleteInactiveCarts() {
        log.info("CartServiceImpl | deleteInactiveCarts is called");
        cartRepository.removeInactiveCarts();
    }

    @Scheduled(fixedDelay = 60000) // Se ejecuta cada minuto (60000 ms)
    public void handleInactiveCarts() {
        log.info("CartServiceImpl | handleInactiveCarts is called");
        deleteInactiveCarts();
    }
}
