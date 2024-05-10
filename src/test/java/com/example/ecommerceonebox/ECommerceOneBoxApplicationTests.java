package com.example.ecommerceonebox;

import com.example.ecommerceonebox.controller.CartController;
import com.example.ecommerceonebox.dto.CartDTO;
import com.example.ecommerceonebox.dto.ProductDTO;
import com.example.ecommerceonebox.model.Cart;
import com.example.ecommerceonebox.model.Product;
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

import java.util.HashMap;
import java.util.Map;

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
		HashMap<Integer, Cart> carts = new HashMap<>();
		carts.put(1, new Cart(1, new HashMap<>(), System.currentTimeMillis()));
		when(cartService.getAllCarts()).thenReturn(carts);

		// Mocking the mapping from Cart to CartDTO
		CartDTO cartDTO = new CartDTO();
		when(modelMapper.map(any(Cart.class), eq(CartDTO.class))).thenReturn(cartDTO);

		// Calling the controller method
		Map<Integer, CartDTO> result = cartController.listAllCarts();

		// Verifying the result
		assertEquals(1, result.size());
		assertEquals(cartDTO, result.get(1));
	}

	@Test
	void testCreateCart() {
		// Mocking the cart creation
		Cart cart = new Cart(1, new HashMap<>(), System.currentTimeMillis());
		when(cartService.createCart()).thenReturn(cart);

		// Mocking the mapping from Cart to CartDTO
		CartDTO cartDTO = new CartDTO();
		when(modelMapper.map(cart, CartDTO.class)).thenReturn(cartDTO);

		// Calling the controller method
		ResponseEntity<CartDTO> response = cartController.createCart();

		// Verifying the response
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(cartDTO, response.getBody());
	}

	@Test
	void testGetCartById() {
		// Mocking the cart retrieval
		Cart cart = new Cart(1, new HashMap<>(), System.currentTimeMillis());
		when(cartService.getCartInformation(1)).thenReturn(cart);

		// Mocking the mapping from Cart to CartDTO
		CartDTO cartDTO = new CartDTO();
		when(modelMapper.map(cart, CartDTO.class)).thenReturn(cartDTO);

		// Calling the controller method
		ResponseEntity<CartDTO> response = cartController.getCartById(1);

		// Verifying the response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(cartDTO, response.getBody());
	}

	@Test
	void testAddProductToCart() {
		// Mocking the product addition to cart
		ProductDTO productDTO = new ProductDTO();
		Product product = new Product();
		Cart cart = new Cart(1, new HashMap<>(), System.currentTimeMillis());
		when(cartService.addProductToCart(1, product)).thenReturn(cart);

		// Mocking the mapping from Product to ProductDTO with specific arguments
		when(modelMapper.map(productDTO, Product.class)).thenReturn(product);

		// Mocking the mapping from Cart to CartDTO
		CartDTO cartDTO = new CartDTO();
		when(modelMapper.map(cart, CartDTO.class)).thenReturn(cartDTO);

		// Calling the controller method
		ResponseEntity<CartDTO> response = cartController.addProductToCart(1, productDTO);

		// Verifying the response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(cartDTO, response.getBody());
	}

	@Test
	void testUpdateProductInCart() {
		// Mocking the product update in cart
		ProductDTO productDTO = new ProductDTO();
		Product product = new Product();
		Cart cart = new Cart(1, new HashMap<>(), System.currentTimeMillis());
		when(cartService.updateProductFromCart(1, product)).thenReturn(cart);

		// Mocking the mapping from Product to ProductDTO with specific arguments
		when(modelMapper.map(productDTO, Product.class)).thenReturn(product);

		// Mocking the mapping from Cart to CartDTO
		CartDTO cartDTO = new CartDTO();
		when(modelMapper.map(cart, CartDTO.class)).thenReturn(cartDTO);

		// Calling the controller method
		ResponseEntity<CartDTO> response = cartController.updateProductInCart(1, productDTO);

		// Verifying the response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(cartDTO, response.getBody());
	}

	@Test
	void testDeleteProductFromCart() {
		// Mocking the product deletion from cart
		ProductDTO productDTO = new ProductDTO();
		Product product = new Product();
		Cart cart = new Cart(1, new HashMap<>(), System.currentTimeMillis());
		when(cartService.deleteProductFromCart(1, product)).thenReturn(cart);

		// Mocking the mapping from Product to ProductDTO with specific arguments
		when(modelMapper.map(productDTO, Product.class)).thenReturn(product);

		// Mocking the mapping from Cart to CartDTO
		CartDTO cartDTO = new CartDTO();
		when(modelMapper.map(cart, CartDTO.class)).thenReturn(cartDTO);

		// Calling the controller method
		ResponseEntity<CartDTO> response = cartController.deleteProductFromCart(1, productDTO);

		// Verifying the response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(cartDTO, response.getBody());
	}

	@Test
	void testDeleteCart() {
		// Mocking the cart deletion
		Cart cart = new Cart(1, new HashMap<>(), System.currentTimeMillis());
		when(cartService.deleteCart(1)).thenReturn(cart);

		// Mocking the mapping from Cart to CartDTO
		CartDTO cartDTO = new CartDTO();
		when(modelMapper.map(cart, CartDTO.class)).thenReturn(cartDTO);

		// Calling the controller method
		ResponseEntity<CartDTO> response = cartController.deleteCart(1);

		// Verifying the response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(cartDTO, response.getBody());
	}
}
