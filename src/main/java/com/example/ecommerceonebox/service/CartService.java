package com.example.ecommerceonebox.service;

import com.example.ecommerceonebox.dto.CartDTO;
import com.example.ecommerceonebox.dto.ProductDTO;

import java.util.HashMap;
import java.util.Map;

public interface CartService {

    Map<Integer, CartDTO> getAllCarts();

    CartDTO createCart(HashMap<Integer,ProductDTO> productDTOs);

    CartDTO getCartInformation(int cartId);

    CartDTO addProductToCart(int cartId, HashMap<Integer,ProductDTO> productDTOs);

    CartDTO updateProductFromCart(int cartId, ProductDTO productDTO);

    CartDTO deleteProductFromCart(int cartId, ProductDTO productDTO);

    CartDTO deleteCart(int cartId);

    void deleteInactiveCarts();

}
