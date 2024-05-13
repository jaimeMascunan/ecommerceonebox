package com.example.ecommerceonebox;

import com.example.ecommerceonebox.model.Cart;
import com.example.ecommerceonebox.model.Product;
import com.example.ecommerceonebox.repository.CartRepository;
import org.springframework.boot.CommandLineRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.stream.IntStream;

@SpringBootApplication
public class EcommerceoneboxApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(EcommerceoneboxApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(CartRepository cartRepository) {
		return args -> {
			Cart cart = cartRepository.createCart();
			// Construir un mapa de productos
			HashMap<Integer, Product> productMap = new HashMap<>();
			IntStream.rangeClosed(1, 5)
					.forEach(i -> {
						Product product = new Product();
						product.setId(i);
						product.setDescription("Product random" + i);
						product.setAmount(10);
						productMap.put(i, product);
					});


			// Añadir el mapa de productos al carrito
			cartRepository.addProductToCart(cart.getId(), productMap);
		};
	}

}
