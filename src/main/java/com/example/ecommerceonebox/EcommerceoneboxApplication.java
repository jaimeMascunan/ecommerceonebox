package com.example.ecommerceonebox;

import com.example.ecommerceonebox.model.Cart;
import com.example.ecommerceonebox.model.Product;
import com.example.ecommerceonebox.service.CartService;
import org.springframework.boot.CommandLineRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
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
	CommandLineRunner runner(CartService cartService) {
		return args -> {
			Cart cart = cartService.createCart();
			IntStream.rangeClosed(1, 30)
					.mapToObj(i -> {
						Product product = new Product();
						product.setId(i);
						product.setDescription("Product" + i);
						product.setAmount(10 * i);
						return product;
					})
					.forEach(product -> cartService.addProductToCart(cart.getId(), product));
		};
	}

}
