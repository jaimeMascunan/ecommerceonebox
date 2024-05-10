package com.example.ecommerceonebox.service;

import com.example.ecommerceonebox.model.Cart;
import com.example.ecommerceonebox.model.Product;

import java.util.HashMap;

public interface CartService {

    HashMap<Integer, Cart> getAllCarts();

    Cart createCart();

    Cart getCartInformation(int cartId);

    Cart addProductToCart(int cartId, Product product);

    Cart updateProductFromCart(int cartId, Product product);

    Cart deleteProductFromCart(int cartId, Product product);

    Cart deleteAllProductsFromCart(int cartId);

    Cart deleteCart(int cartId);

    void deleteInactiveCarts();
}
