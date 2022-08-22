package com.infy.ekart.service.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.infy.ekart.dto.ProductDTO;
import com.infy.ekart.entity.Product;
import com.infy.ekart.exception.EKartException;
import com.infy.ekart.repository.ProductRepository;
import com.infy.ekart.service.CustomerProductService;
import com.infy.ekart.service.CustomerProductServiceImpl;

@SpringBootTest
class CustomerProductServiceTest {
	@Mock 
	private ProductRepository productRepository;
	@InjectMocks
	private CustomerProductService customerProductService=new CustomerProductServiceImpl();
	
	@Test
	public void getAllProductsVaildTest()throws EKartException
	{
		List<Product> products=new ArrayList<Product>();
		Product product1=new Product();
		product1.setProductId(1);
		product1.setAvailableQuantity(12);
		product1.setBrand("moto");
		product1.setName("mobile");
		product1.setPrice(2500.0);
		Product product2=new Product();
		product2.setProductId(2);
		product2.setAvailableQuantity(12);
		product2.setBrand("samsung");
		product2.setName("mobile");
		product2.setPrice(2500.0);
		products.add(product1);
		products.add(product2);
		Mockito.when(productRepository.findAll()).thenReturn(products);
		List<ProductDTO> productDTOs=new ArrayList<ProductDTO>();
		ProductDTO productDTO1=new ProductDTO();
		productDTO1.setProductId(1);
		productDTO1.setAvailableQuantity(12);
		productDTO1.setBrand("moto");
		productDTO1.setName("mobile");
		productDTO1.setPrice(2500.0);
		productDTOs.add(productDTO1);
		ProductDTO productDTO2=new ProductDTO();
		productDTO2.setProductId(2);
		productDTO2.setAvailableQuantity(12);
		productDTO2.setBrand("samsung");
		productDTO2.setName("mobile");
		productDTO2.setPrice(2500.0);
		productDTOs.add(productDTO2);
		List<ProductDTO> list=customerProductService.getAllProducts();
		Assertions.assertEquals(productDTOs.get(0).getProductId(), list.get(0).getProductId());
		Assertions.assertEquals(productDTOs.get(1).getProductId(), list.get(1).getProductId());
	}
	@Test
	public void getProductByIdValidTest()throws EKartException
	{
		Product product=new Product();
		product.setProductId(1);
		product.setAvailableQuantity(12);
		product.setBrand("vivo");
		product.setName("mobile");
		product.setPrice(2500.0);
		Mockito.when(productRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(product));
		ProductDTO productDTO=new ProductDTO();
		productDTO.setProductId(1);
		productDTO.setAvailableQuantity(12);
		productDTO.setBrand("vivo");
		productDTO.setName("mobile");
		productDTO.setPrice(2500.0);
		
		Assertions.assertEquals(productDTO.getProductId(), customerProductService.getProductById(1).getProductId());
	}
	@Test
	public void getProductByIdInvalidTest()throws EKartException
	{
		Mockito.when(productRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		EKartException e=Assertions.assertThrows(EKartException.class,()->customerProductService.getProductById(1));
		Assertions.assertEquals("ProductService.PRODUCT_NOT_AVAILABLE", e.getMessage());
	}
	
	
	@Test
	public void reduceAvailableQuantityValidTest()throws Exception
	{
		Product product=new Product();
		product.setProductId(1);
		product.setAvailableQuantity(12);
		product.setBrand("moto");
		product.setName("mobile");
		product.setPrice(2500.0);
		
		Mockito.when(productRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(product));
		customerProductService.reduceAvailableQuantity(1, 2);
		Assertions.assertEquals(product.getAvailableQuantity(), 10);
	}
	
	@Test
	public void reduceAvailableQuantityInvalidTest()throws EKartException
	{
		Mockito.when(productRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		EKartException e=Assertions.assertThrows(EKartException.class, ()->customerProductService.reduceAvailableQuantity(1,12));
		Assertions.assertEquals("ProductService.PRODUCT_NOT_AVAILABLE", e.getMessage());
		
		


	// Write testcases here

}}