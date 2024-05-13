package com.example.ecommerceonebox.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer Id;
    private HashMap<Integer, Product> products = new HashMap<>();
    private Long lastAccessTime;

    public Cart(int cartId) {
        this.setId(cartId);
        updateLastAccessTime();
    }

    public void addProduct(HashMap<Integer, Product> product) {
        product.forEach((productId, newProduct) -> {
            if (!products.containsKey(productId)) {
                products.put(productId, newProduct);
                updateLastAccessTime();
            }
        });
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