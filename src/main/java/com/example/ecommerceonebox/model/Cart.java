package com.example.ecommerceonebox.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    private int Id;
    private HashMap<Integer, Product> products = new HashMap<>();
    private long lastAccessTime;

    public Cart(int cartId) {
        this.setId(cartId);
        updateLastAccessTime();
    }

    public void addProduct(Product product) {
        if (!products.containsKey(product.getId())) {
            products.put(product.getId(), product);
            updateLastAccessTime();
        }
    }

    public void updateProduct(Product product) {
        products.put(product.getId(), product);
        updateLastAccessTime();
    }

    public void removeProduct(Product product) {
        if (products.containsKey(product.getId())) {
            products.remove(product.getId());
            updateLastAccessTime();
        }
    }

    private void updateLastAccessTime() {
        this.setLastAccessTime(System.currentTimeMillis());
    }
}
