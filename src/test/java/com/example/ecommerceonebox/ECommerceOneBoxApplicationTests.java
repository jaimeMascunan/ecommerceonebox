package com.example.ecommerceonebox;

import com.example.ecommerceonebox.controller.CartController;
import com.example.ecommerceonebox.dto.CartDTO;
import com.example.ecommerceonebox.dto.ProductDTO;
import com.example.ecommerceonebox.service.CartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ECommerceOneBoxApplicationTests {

	@Mock
	private CartServiceImpl cartService;

	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private CartController cartController;

	@BeforeEach
	void setUp() {
		// Configuración de mocks específicos antes de cada prueba
		reset(cartService, modelMapper);
	}

	@Test
	void testListAllCarts() {
		// Mocking the cart data
		Map<Integer, CartDTO> carts = new HashMap<>();
		CartDTO cartDTO = new CartDTO();
		carts.put(1, cartDTO);
		when(cartService.getAllCarts()).thenReturn(carts);

		// Calling the controller method
		Map<Integer, CartDTO> result = cartController.listAllCarts();

		// Verifying the result
		assertEquals(1, result.size());
		assertEquals(cartDTO, result.get(1));
	}

	@Test
	void testCreateCart() {
		// Mocking the cart creation
		ProductDTO productDTO = new ProductDTO(1, "Random", 10);
		HashMap<Integer, ProductDTO> productMap = new HashMap<>();
		productMap.put(productDTO.getId(), productDTO);
		CartDTO cartDTO = new CartDTO(1, new HashMap<>());
		when(cartService.createCart(productMap)).thenReturn(cartDTO);

		// Calling the controller method
		ResponseEntity<CartDTO> response = cartController.createCart(productMap);

		// Verifying the response
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(cartDTO, response.getBody());
	}

	@Test
	void testGetCartById() {
		// Mocking the cart retrieval
		CartDTO cartDTO = new CartDTO(1, new HashMap<>());
		when(cartService.getCartInformation(1)).thenReturn(cartDTO);

		// Calling the controller method
		ResponseEntity<CartDTO> response = cartController.getCartById(1);

		// Verifying the response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(cartDTO, response.getBody());
	}

	@Test
	void testAddProductToCart() {
		// Mocking the product addition to cart
		ProductDTO productDTO = new ProductDTO(1, "Random", 10);
		HashMap<Integer, ProductDTO> productDTOMap = new HashMap<>();
		productDTOMap.put(productDTO.getId(), productDTO);

		CartDTO cartDTO = new CartDTO(1, new HashMap<>());
		when(cartService.addProductToCart(1, productDTOMap)).thenReturn(cartDTO);

		// Calling the controller method
		ResponseEntity<?> response = cartController.addProductToCart(1, productDTOMap);

		// Verifying the response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(cartDTO, response.getBody());
	}

	@Test
	void testUpdateProductInCart() {
		// Mocking the product update in cart
		ProductDTO productDTO = new ProductDTO(1, "Random", 10);
		CartDTO cartDTO = new CartDTO(1, new HashMap<>());
		when(cartService.updateProductFromCart(1, productDTO)).thenReturn(cartDTO);

		// Calling the controller method
		ResponseEntity<CartDTO> response = cartController.updateProductInCart(1, productDTO);

		// Verifying the response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(cartDTO, response.getBody());
	}

	@Test
	void testDeleteProductFromCart() {
		// Mocking the product deletion from cart
		ProductDTO productDTO = new ProductDTO(1, "Random", 10);
		CartDTO cartDTO = new CartDTO(1, new HashMap<>());
		when(cartService.deleteProductFromCart(1, productDTO)).thenReturn(cartDTO);

		// Calling the controller method
		ResponseEntity<CartDTO> response = cartController.deleteProductFromCart(1, productDTO);

		// Verifying the response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(cartDTO, response.getBody());
	}

	@Test
	void testDeleteCart() {
		// Mocking the cart deletion
		CartDTO cartDTO = new CartDTO(1, new HashMap<>());
		when(cartService.deleteCart(1)).thenReturn(cartDTO);

		// Calling the controller method
		ResponseEntity<CartDTO> response = cartController.deleteCart(1);

		// Verifying the response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(cartDTO, response.getBody());
	}
}
