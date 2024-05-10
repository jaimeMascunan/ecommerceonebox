package com.example.ecommerceonebox.service;

import com.example.ecommerceonebox.exception.CartServiceCustomException;
import com.example.ecommerceonebox.model.Cart;
import com.example.ecommerceonebox.model.Product;
import com.example.ecommerceonebox.repository.CartRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Log4j2
@EnableScheduling
public class CartServiceImpl implements CartService{
    @Autowired
    CartRepository cartRepository;

    @Override
    public HashMap<Integer, Cart> getAllCarts() {
        log.info("CartServiceImpl | getAllCarts is called");
        return cartRepository.getAllCarts();
    }

    @Override
    public Cart createCart() {
        log.info("CartServiceImpl | createCart is called");
        Cart newCart = cartRepository.createCart();
        log.info("CartServiceImpl | createCart | Cart Id : {}", newCart.getId());
        return newCart;
    }

    @Override
    public Cart getCartInformation(int id) {
        log.info("CartServiceImpl | getCartInformation is called");
        Cart cart = cartRepository.getCartInformation(id);
        if (cart == null) {
            throw new CartServiceCustomException("Cart with given Id not found", "CART_NOT_FOUND");
        }
        log.info("CartServiceImpl | getCartInformation | Cart Id : {}", cart.getId());
        return cart;
    }

    @Override
    public Cart addProductToCart(int id, Product product) {
        log.info("CartServiceImpl | addProductToCart is called");
        Cart cart = cartRepository.addProductToCart(id, product);
        if (cart.getProducts().get(product.getId()) == null) {
            throw new CartServiceCustomException("Product not added to cart", "PRODUCT_NOT_ADDED");
        }
        log.info("CartServiceImpl | addProductToCart | Cart Id : {}", cart.getId());
        return cart;
    }

    @Override
    public Cart updateProductFromCart(int cartId, Product product) {
        log.info("CartServiceImpl | updateProductFromCart is called");
        Cart cart = cartRepository.updateProductFromCart(cartId, product);
        if (cart.getProducts().get(product.getId()) == null) {
            throw new CartServiceCustomException("Product in cart not updated", "PRODUCT_NOT_UPDATED");
        }
        log.info("CartServiceImpl | updateProductFromCart | Cart Id : {}", cart.getId());
        return cart;
    }

    @Override
    public Cart deleteProductFromCart(int cartId, Product product) {
        log.info("CartServiceImpl | deleteProductFromCart is called");
        Cart cart = cartRepository.deleteProductFromCart(cartId, product);
        if (cart == null) {
            throw new CartServiceCustomException("Product in cart not removed", "PRODUCT_NOT_DELETED");
        }
        log.info("CartServiceImpl | deleteProductFromCart | Cart Id : {}", cart.getId());
        return cart;
    }

    @Override
    public Cart deleteAllProductsFromCart(int cartId) {
        log.info("CartServiceImpl | deleteAllProductFromCart is called");
        Cart cart = cartRepository.deleteAllProductsFromCart(cartId);
        if (cart == null) {
            throw new CartServiceCustomException("Cart with given Id not found", "CART_NOT_FOUND");
        }
        log.info("CartServiceImpl | deleteAllProductFromCart | Cart Id : {}", cart.getId());
        return cart;
    }

    @Override
    public Cart deleteCart(int cartId) {
        log.info("CartServiceImpl | deleteCart is called");
        Cart cartDeleted = cartRepository.deleteCart(cartId);
        if (cartDeleted == null) {
            throw new CartServiceCustomException("Cart with given Id not found", "CART_NOT_FOUND");
        }
        log.info("CartServiceImpl | deleteCart | Cart Id : {}", cartDeleted.getId());
        return cartDeleted;
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
