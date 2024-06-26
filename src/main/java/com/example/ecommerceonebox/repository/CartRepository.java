package com.example.ecommerceonebox.repository;

import com.example.ecommerceonebox.model.Cart;
import com.example.ecommerceonebox.model.Product;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.TimeUnit;

@Repository
public class CartRepository {
    private final HashMap<Integer, Cart> cartsMap = new HashMap<>();
    private final AtomicInteger cartIdGenerator = new AtomicInteger(1);

    public HashMap<Integer, Cart> getAllCarts() {
       return cartsMap;
    }

    public Cart createCart() {
        int cartId = cartIdGenerator.getAndIncrement();
        Cart cart = new Cart(cartId);
        cartsMap.put(cartId, cart);
        return cart;
    }

    public Cart getCartInformation(int id) {
        return cartsMap.get(id);
    }

    public Cart addProductToCart(int id, HashMap<Integer, Product> product) {
        Cart cart = cartsMap.get(id);
        cart.addProduct(product);
        return cart;
    }

    public Cart updateProductFromCart(int id, Product product) {
        Cart cart = cartsMap.get(id);
        cart.updateProduct(product);
        return cart;
    }

    public Cart deleteProductFromCart(int id, Product product) {
        Cart cart = cartsMap.get(id);
        cart.removeProduct(product);
        return cart;
    }

    public Cart deleteCart(int cartId)  {
        Cart cart = cartsMap.get(cartId);
        cartsMap.remove(cartId);
        return cart;
    }

    public void removeInactiveCarts() {
        long currentTime = System.currentTimeMillis();
        cartsMap.entrySet().removeIf(entry -> currentTime - entry.getValue().getLastAccessTime() > TimeUnit.MINUTES.toMillis(10));
    }

}
