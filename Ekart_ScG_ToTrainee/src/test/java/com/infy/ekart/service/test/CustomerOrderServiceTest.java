package com.infy.ekart.service.test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.infy.ekart.dto.CustomerDTO;
import com.infy.ekart.dto.OrderDTO;
import com.infy.ekart.dto.OrderStatus;
import com.infy.ekart.dto.OrderedProductDTO;
import com.infy.ekart.dto.PaymentThrough;
import com.infy.ekart.dto.ProductDTO;
import com.infy.ekart.entity.Order;
import com.infy.ekart.entity.OrderedProduct;
import com.infy.ekart.exception.EKartException;
import com.infy.ekart.repository.CustomerOrderRepository;
import com.infy.ekart.service.CustomerOrderService;
import com.infy.ekart.service.CustomerOrderServiceImpl;
import com.infy.ekart.service.CustomerService;

@SpringBootTest
public class CustomerOrderServiceTest {
	//Write testcases here
	
	@Mock
	private CustomerOrderRepository customerOrderRepository;
	@Mock 
	private CustomerService customerService;
	@InjectMocks
	private CustomerOrderService customerOrderService = new CustomerOrderServiceImpl();
	
	@Test
	public void getOrderDetailsValidTest() throws Exception {
		Order order = new Order();
		order.setOrderId(1);
		order.setCustomerEmailId("tom@infosys.com");
		order.setDateOfOrder(LocalDateTime.now());
		order.setTotalPrice(35263.0);
		order.setOrderStatus(OrderStatus.PLACED);
		order.setDiscount(3456.0);
		order.setPaymentThrough(PaymentThrough.DEBIT_CARD);
		order.setDateOfDelivery(LocalDateTime.now());
		order.setDeliveryAddress("C-12 Sanwal Nagar Near Sadiq Nagar");
		List<OrderedProduct> orderedProducts = new ArrayList<>();
		OrderedProduct orderedProduct = new OrderedProduct();
		
		
		orderedProduct.setOrderedProductId(1);
		orderedProduct.setProductId(1);
		orderedProduct.setQuantity(2);
		
		orderedProducts.add(orderedProduct);
		order.setOrderedProducts(orderedProducts);
		
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setOrderId(1);
		orderDTO.setCustomerEmailId("tom@infosys.com");
		order.setDateOfOrder(LocalDateTime.now());
		order.setTotalPrice(35263.0);
		order.setOrderStatus(OrderStatus.PLACED);
		order.setDiscount(3456.0);
		order.setPaymentThrough(PaymentThrough.DEBIT_CARD);
		order.setDateOfDelivery(LocalDateTime.now());
		order.setDeliveryAddress("C-12 Sanwal Nagar Near Sadiq Nagar");
		List<OrderedProductDTO> orderedProductsDtos = new ArrayList<>();
		
		OrderedProductDTO o = new OrderedProductDTO();
		o.setOrderedProductId(1);
		ProductDTO productDTO = new ProductDTO();
		productDTO.setProductId(1);
		
	o.setProduct(productDTO);
	o.setQuantity(2);
	orderedProductsDtos.add(o);
	orderDTO.setOrderedProducts(orderedProductsDtos);
	Mockito.when(customerOrderRepository.findById(1)).thenReturn(Optional.of(order));
	Assertions.assertTrue(orderDTO.getOrderId().equals(customerOrderService.getOrderDetails(1).getOrderId()));
	
	}
	@Test
	public void getOrderDetailsInvalidTest() throws EKartException{
		Order order = new Order();
		order.setOrderId(1);
		order.setCustomerEmailId("tom@infosys.com");
		order.setDateOfOrder(LocalDateTime.now());
		order.setTotalPrice(35263.0);
		order.setOrderStatus(OrderStatus.PLACED);
		order.setDiscount(3456.0);
		order.setPaymentThrough(PaymentThrough.DEBIT_CARD);
		order.setDateOfDelivery(LocalDateTime.now());
		order.setDeliveryAddress("C-12 Sanwal Nagar Near Sadiq Nagar");
		List<OrderedProduct> orderedProducts = new ArrayList<>();
		
		
		OrderedProduct orderedProduct = new OrderedProduct();
		
		orderedProduct.setOrderedProductId(1);
		orderedProduct.setProductId(1);
		orderedProduct.setQuantity(2);
		
		orderedProducts.add(orderedProduct);
		order.setOrderedProducts(orderedProducts);
		
		Mockito.when(customerOrderRepository.findById(2)).thenReturn(Optional.empty());
		EKartException e = Assertions.assertThrows(EKartException.class, ()-> customerOrderService.getOrderDetails(2));
		Assertions.assertEquals("OrderService.ORDER_NOT_FOUND", e.getMessage());
		
	}
	
	@Test
	public void updateOrderStatusValidTest() throws EKartException {
		Order order = new Order();
		order.setOrderId(1);
		order.setCustomerEmailId("tom@infosys.com");
		order.setDateOfOrder(LocalDateTime.now());
		order.setTotalPrice(35263.0);
		order.setOrderStatus(OrderStatus.PLACED);
		order.setDiscount(3456.0);
		order.setPaymentThrough(PaymentThrough.DEBIT_CARD);
		order.setDateOfDelivery(LocalDateTime.now());
		order.setDeliveryAddress("C-12 Sanwal Nagar Near Sadiq Nagar");
		List<OrderedProduct> orderedProducts=new ArrayList<>();
		OrderedProduct orderedProduct=new OrderedProduct();
		
		orderedProduct.setOrderedProductId(1);
		orderedProduct.setProductId(1);
		orderedProduct.setQuantity(2);
		
		orderedProducts.add(orderedProduct);
		order.setOrderedProducts(orderedProducts);
		Mockito.when(customerOrderRepository.findById(1)).thenReturn(Optional.of(order));
		Assertions.assertDoesNotThrow(()-> customerOrderService.updateOrderStatus(1, OrderStatus.CONFIRMED));
	}
	@Test public void updateOrderStatusInvalidTest() throws EKartException{
		Order order=new Order();
		order.setOrderId(1);
		order.setCustomerEmailId("tom@infosys.com");
		order.setDateOfOrder(LocalDateTime.now());
		order.setTotalPrice(35263.0);
		order.setOrderStatus(OrderStatus.PLACED);
		order.setDiscount(3456.0);
		order.setPaymentThrough(PaymentThrough.DEBIT_CARD);
		order.setDateOfDelivery(LocalDateTime.now());
		order.setDeliveryAddress("C-12 Sanwal Nagar Near Sadiq Nagar");
		
		List<OrderedProduct> orderedProducts=new ArrayList<>();
        OrderedProduct orderedProduct=new OrderedProduct();
		
		orderedProduct.setOrderedProductId(1);
		orderedProduct.setProductId(1);
		orderedProduct.setQuantity(2);
		
		orderedProducts.add(orderedProduct);
		order.setOrderedProducts(orderedProducts);
		Mockito.when(customerOrderRepository.findById(2)).thenReturn(Optional.empty());
		EKartException e=Assertions.assertThrows(EKartException.class, ()->customerOrderService.updateOrderStatus(2, OrderStatus.CONFIRMED));
		Assertions.assertEquals("OrderService.ORDER_NOT_FOUND", e.getMessage());
	}
	@Test
	public void updatePaymentThriughValidTest() throws EKartException {
		
		Order order=new Order();
		order.setOrderId(1);
		order.setCustomerEmailId("tom@infosys.com");
		order.setDateOfOrder(LocalDateTime.now());
		order.setTotalPrice(35263.0);
		order.setOrderStatus(OrderStatus.PLACED);
		order.setDiscount(3456.0);
		order.setPaymentThrough(PaymentThrough.DEBIT_CARD);
		order.setDateOfDelivery(LocalDateTime.now());
		order.setDeliveryAddress("C-12 Sanwal Nagar Near Sadiq Nagar");
		
		List<OrderedProduct> orderedProducts=new ArrayList<>();
        OrderedProduct orderedProduct=new OrderedProduct();
		
		orderedProduct.setOrderedProductId(1);
		orderedProduct.setProductId(1);
		orderedProduct.setQuantity(2);
		orderedProducts.add(orderedProduct);
		order.setOrderedProducts(orderedProducts);
		Mockito.when(customerOrderRepository.findById(1)).thenReturn(Optional.of(order));
		Assertions.assertDoesNotThrow(()->customerOrderService.updatePaymentThrough(1, PaymentThrough.CREDIT_CARD));
	}
	@Test
	public void updatePaymentThroughInvalidTest() throws EKartException{
		Order order=new Order();
		order.setOrderId(1);
		order.setCustomerEmailId("tom@infosys.com");
		order.setDateOfOrder(LocalDateTime.now());
		order.setTotalPrice(35263.0);
		order.setOrderStatus(OrderStatus.PLACED);
		order.setDiscount(3456.0);
		order.setPaymentThrough(PaymentThrough.DEBIT_CARD);
		order.setDateOfDelivery(LocalDateTime.now());
		order.setDeliveryAddress("C-12 Sanwal Nagar Near Sadiq Nagar");
		
		List<OrderedProduct> orderedProducts=new ArrayList<>();
        OrderedProduct orderedProduct=new OrderedProduct();
		
		orderedProduct.setOrderedProductId(1);
		orderedProduct.setProductId(1);
		orderedProduct.setQuantity(2);
		orderedProducts.add(orderedProduct);
		order.setOrderedProducts(orderedProducts);
		Mockito.when(customerOrderRepository.findById(2)).thenReturn(Optional.empty());
		EKartException e= Assertions.assertThrows(EKartException.class,()->customerOrderService.updatePaymentThrough(2,PaymentThrough.CREDIT_CARD));
		Assertions.assertEquals("OrderService.ORDER_NOT_FOUND", e.getMessage());
	}
	@Test
	public void updatePaymentThroughInvalidTest2()throws EKartException {
		Order order=new Order();
		order.setOrderId(1);
		order.setCustomerEmailId("tom@infosys.com");
		order.setDateOfOrder(LocalDateTime.now());
		order.setTotalPrice(35263.0);
		order.setOrderStatus(OrderStatus.CONFIRMED);
		order.setDiscount(3456.0);
		order.setPaymentThrough(PaymentThrough.DEBIT_CARD);
		order.setDateOfDelivery(LocalDateTime.now());
		order.setDeliveryAddress("C-12 Sanwal Nagar Near Sadiq Nagar");
		
		List<OrderedProduct> orderedProducts=new ArrayList<>();
        OrderedProduct orderedProduct=new OrderedProduct();
		
		orderedProduct.setOrderedProductId(1);
		orderedProduct.setProductId(1);
		orderedProduct.setQuantity(2);
		orderedProducts.add(orderedProduct);
		order.setOrderedProducts(orderedProducts);
		Mockito.when(customerOrderRepository.findById(1)).thenReturn(Optional.of(order));
		EKartException e = Assertions.assertThrows(EKartException.class, ()-> customerOrderService.updatePaymentThrough(1,  PaymentThrough.CREDIT_CARD));
		Assertions.assertEquals("OrderService.TRANSACTION_ALREADY_DONE", e.getMessage());
	}
	@Test
	public void findOrdersByCustomerEmailIdValidTest() throws EKartException{
		
		Order order=new Order();
		order.setOrderId(1);
		order.setCustomerEmailId("tom@infosys.com");
		order.setDateOfOrder(LocalDateTime.now());
		order.setTotalPrice(35263.0);
		order.setOrderStatus(OrderStatus.CONFIRMED);
		order.setDiscount(3456.0);
		order.setPaymentThrough(PaymentThrough.DEBIT_CARD);
		order.setDateOfDelivery(LocalDateTime.now());
		order.setDeliveryAddress("C-12 Sanwal Nagar Near Sadiq Nagar");
		
		List<OrderedProduct> orderedProducts=new ArrayList<>();
        OrderedProduct orderedProduct=new OrderedProduct();
		
		orderedProduct.setOrderedProductId(1);
		orderedProduct.setProductId(1);
		orderedProduct.setQuantity(2);
		orderedProducts.add(orderedProduct);
		order.setOrderedProducts(orderedProducts);
		
		Mockito.when(customerOrderRepository.findByCustomerEmailId("tom@infosys.com")).thenReturn(List.of(order));
		
		Assertions.assertEquals(1,  customerOrderService.findOrdersByCustomerEmailId("tom@infosys.com").size());
	}
	@Test
	public void findOrdersByCustomerEmailIdInvalidTest() throws EKartException {
		Order order=new Order();
		order.setOrderId(1);
		order.setCustomerEmailId("tom@infosys.com");
		order.setDateOfOrder(LocalDateTime.now());
		order.setTotalPrice(35263.0);
		order.setOrderStatus(OrderStatus.CONFIRMED);
		order.setDiscount(3456.0);
		order.setPaymentThrough(PaymentThrough.DEBIT_CARD);
		order.setDateOfDelivery(LocalDateTime.now());
		order.setDeliveryAddress("C-12 Sanwal Nagar Near Sadiq Nagar");
		
		List<OrderedProduct> orderedProducts=new ArrayList<>();
        OrderedProduct orderedProduct=new OrderedProduct();
		
		orderedProduct.setOrderedProductId(1);
		orderedProduct.setProductId(1);
		orderedProduct.setQuantity(2);
		orderedProducts.add(orderedProduct);
		order.setOrderedProducts(orderedProducts);
		Mockito.when(customerOrderRepository.findByCustomerEmailId("john@infosys.com")).thenReturn(List.of());
		EKartException e=Assertions.assertThrows(EKartException.class, ()->customerOrderService.findOrdersByCustomerEmailId("john@infosys.com"));
		Assertions.assertEquals("OrderService.NO_ORDERS_FOUND", e.getMessage());
	}
	@Test
	public void placeOrderInvalidTest()throws EKartException
	{
		OrderDTO orderDTO=new OrderDTO();
		orderDTO.setCustomerEmailId("ram@infosys.com");
		
		CustomerDTO customerDTO=new CustomerDTO();
		customerDTO.setAddress("");
		Mockito.when(customerService.getCustomerByEmailId(Mockito.anyString())).thenReturn(customerDTO);
		EKartException e=Assertions.assertThrows(EKartException.class, ()->customerOrderService.placeOrder(orderDTO));
		Assertions.assertEquals("OrderService.ADDRESS_NOT_AVAILABLE", e.getMessage());
	}
	@Test
	public void placeOrderInvalidTest2()throws EKartException
	{
		OrderDTO orderDTO=new OrderDTO();
		orderDTO.setCustomerEmailId("ram@infosys.com");
		orderDTO.setDateOfDelivery(LocalDateTime.now().plusMonths(2));
		orderDTO.setPaymentThrough(PaymentThrough.CREDIT_CARD.toString());
		OrderedProductDTO orderedProductDTO=new OrderedProductDTO();
		orderedProductDTO.setQuantity(1000);
		List<OrderedProductDTO> list=new ArrayList<OrderedProductDTO>();
		ProductDTO productDTO=new ProductDTO();
		productDTO.setAvailableQuantity(10);
		productDTO.setQuantity(20);
		orderedProductDTO.setProduct(productDTO);
		list.add(orderedProductDTO);
		orderDTO.setOrderedProducts(list);
		CustomerDTO customerDTO=new CustomerDTO();
		customerDTO.setAddress("abc");
		Mockito.when(customerService.getCustomerByEmailId(Mockito.anyString())).thenReturn(customerDTO);
		
		
		EKartException e=Assertions.assertThrows(EKartException.class, ()->customerOrderService.placeOrder(orderDTO));
		Assertions.assertEquals("OrderService.INSUFFICIENT_STOCK", e.getMessage());
	}
	@Test
	public void placeOrderValidTest()throws EKartException {
		OrderDTO orderDTO=new OrderDTO();
		orderDTO.setCustomerEmailId("ram@infosys.com");
		orderDTO.setDateOfDelivery(LocalDateTime.now().plusMonths(2));
		orderDTO.setPaymentThrough(PaymentThrough.CREDIT_CARD.toString());
		OrderedProductDTO orderedProductDTO=new OrderedProductDTO();
		orderedProductDTO.setQuantity(10);
		List<OrderedProductDTO> list=new ArrayList<OrderedProductDTO>();
		ProductDTO productDTO=new ProductDTO();
		productDTO.setProductId(1);
		productDTO.setPrice(10000.0);
		productDTO.setAvailableQuantity(10);
		productDTO.setQuantity(2);
		orderedProductDTO.setProduct(productDTO);
		list.add(orderedProductDTO);
		orderDTO.setOrderedProducts(list);
		CustomerDTO customerDTO=new CustomerDTO();
		customerDTO.setAddress("abc");
		Order o = new Order();
		o.setOrderId(1000);
		
		Mockito.when(customerService.getCustomerByEmailId(Mockito.anyString())).thenReturn(customerDTO);
		Mockito.when(customerOrderRepository.save(Mockito.any())).thenReturn(o);
		customerOrderService.placeOrder(orderDTO);
		Assertions.assertNull(customerOrderService.placeOrder(orderDTO));
		
		
	}
	
}