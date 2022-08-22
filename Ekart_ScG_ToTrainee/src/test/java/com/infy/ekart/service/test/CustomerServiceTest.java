package com.infy.ekart.service.test;

import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.infy.ekart.dto.CustomerDTO;
import com.infy.ekart.entity.Customer;
import com.infy.ekart.exception.EKartException;
import com.infy.ekart.repository.CustomerRepository;
import com.infy.ekart.service.CustomerService;
import com.infy.ekart.service.CustomerServiceImpl;

@SpringBootTest
public class CustomerServiceTest {
	//Write testcases here
	@Mock
	private CustomerRepository customerRepository;
	@InjectMocks
	CustomerService customerService = new CustomerServiceImpl();
	@Test
	void authenticateCustomerValidTest() throws EKartException{
		Customer customer = new Customer();
		customer.setPassword("Tom@123");
		customer.setEmailId("tom@infosys.com");
		String emailId = "tom@infosys.com";
		String password = "Tom@123";
		Mockito.when(customerRepository.findById(Mockito.anyString().toLowerCase())).thenReturn(Optional.of(customer));
		CustomerDTO customerDTO = customerService.authenticateCustomer(emailId, password);
		Assertions.assertEquals(customer.getEmailId(), customerDTO.getEmailId());
	}
	@Test
	void authenticateCustomerInValidTest() throws EKartException{
		String emailId="tom@infosys.com";
				String password="Tom@123";
				Mockito.when(customerRepository.findById(Mockito.anyString().toLowerCase())).thenReturn(Optional.empty());
				Exception ex=Assertions.assertThrows(EKartException.class,()->customerService.authenticateCustomer(emailId, password));
				Assertions.assertEquals("CustomerService.CUSTOMER_NOT_FOUND", ex.getMessage());
	}
	@Test
	void authenticateCustomerInvalid2Test()throws EKartException
	{
		Customer customer=new Customer();
		customer.setEmailId("tom@infosys.com");
		customer.setPassword("hary@123");
		String emailId = "tom@infosys.com";
		String password = "Tom@123";
		Mockito.when(customerRepository.findById(Mockito.anyString().toLowerCase())).thenReturn(Optional.of(customer));
		Exception ex=Assertions.assertThrows(EKartException.class, ()->customerService.authenticateCustomer(emailId, password));
		Assertions.assertEquals("CustomerService.INVALID_CREDENTIALS", ex.getMessage());
	}
	@Test
	void registerNewCustomerValidTest() throws EKartException {
		Customer customer = new Customer();
		customer.setEmailId("sam@infosys.com");
		customer.setPassword("Sam@123");
		customer.setAddress("abc");
		customer.setPhoneNumber("994445670");
		customer.setName("Sam");
		
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setPhoneNumber("994445670");
		customerDTO.setEmailId("sam@infosys.com");
		customerDTO.setAddress("abc");
		customerDTO.setName("Sam");
		customerDTO.setPassword("Sam@123");
		
		Mockito.when(customerRepository.findById(customerDTO.getEmailId())).thenReturn(Optional.empty());
		Mockito.when(customerRepository.findByPhoneNumber(Mockito.anyString())).thenReturn("");
		Mockito.when(customerRepository.save(Mockito.any())).thenReturn(customer);
		
		
		Assertions.assertEquals(customer.getEmailId(), customerService.registerNewCustomer(customerDTO));
	}
	@Test
	void registerNewCustomerInvalidTest() throws EKartException {
		Customer customer = new Customer();
		customer.setEmailId("sam@infosys.com");
		customer.setPassword("Sam@123");
		customer.setAddress("abc");
		customer.setPhoneNumber("994445670");
		customer.setName("Sam");
		
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setPhoneNumber("994445670");
		customerDTO.setEmailId("sam@infosys.com");
		customerDTO.setAddress("abc");
		customerDTO.setName("Sam");
		customerDTO.setPassword("Sam@123");
		
		Mockito.when(customerRepository.findById(customerDTO.getEmailId())).thenReturn(Optional.of(customer));
		Mockito.when(customerRepository.findByPhoneNumber(customerDTO.getPhoneNumber())).thenReturn(Boolean.TRUE.toString());
		
		EKartException e = Assertions.assertThrows(EKartException.class, ()-> customerService.registerNewCustomer(customerDTO));
		Assertions.assertEquals("CustomerService.EMAIL_ID_ALREADY_IN_USE", e.getMessage());
	}
	
	@Test
	void registerNewCustomerInvalid2Test() throws EKartException {
		Customer customer = new Customer();
		customer.setEmailId("sam@infosys.com");
		customer.setPassword("Sam@123");
		customer.setAddress("abc");
		customer.setPhoneNumber("994445670");
		customer.setName("Sam");
		
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setPhoneNumber("994445670");
		customerDTO.setEmailId("sam@infosys.com");
		customerDTO.setAddress("abc");
		customerDTO.setName("Sam");
		customerDTO.setPassword("Sam@123");
		
		Mockito.when(customerRepository.findById(customerDTO.getEmailId())).thenReturn(Optional.empty());
		Mockito.when(customerRepository.findByPhoneNumber(customerDTO.getPhoneNumber())).thenReturn(Boolean.FALSE.toString());
		
		EKartException e = Assertions.assertThrows(EKartException.class, ()-> customerService.registerNewCustomer(customerDTO));
		Assertions.assertEquals("CustomerService.PHONE_NUMBER_ALREADY_IN_USE", e.getMessage());
	}
	@Test
	public void deleteShippingAddressValidTest()throws EKartException
	{
		Customer customer = new Customer();
		customer.setEmailId("nick@infosys.com");
		customer.setPassword("Sam@123");
		customer.setAddress("abc");
		customer.setPhoneNumber("994445670");
		customer.setName("Sam");
		
		Mockito.when(customerRepository.findById(Mockito.anyString())).thenReturn(Optional.of(customer));
		customerService.deleteShippingAddress(customer.getEmailId());
		Assertions.assertNull(customer.getAddress());
	}
	@Test
	public void deleteShippingAddressInvalidTest()throws EKartException
	{
		
		Customer customer = new Customer();
		customer.setEmailId("nick@infosys.com");
		customer.setPassword("Sam@123");
		customer.setAddress("abc");
		customer.setPhoneNumber("994445670");
		customer.setName("Sam");
		Mockito.when(customerRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
		EKartException e=Assertions.assertThrows(EKartException.class, ()->customerService.deleteShippingAddress(customer.getAddress()));
		Assertions.assertEquals("CustomerService.CUSTOMER_NOT_FOUND", e.getMessage());
	}
	@Test
	public void updateShippingAddressInvalidTest()
	{
		Customer customer = new Customer();
		customer.setEmailId("sam@infosys.com");
		customer.setPassword("Sam@123");
		customer.setAddress("abc");
		customer.setPhoneNumber("994445670");
		customer.setName("Sam");
		
		Mockito.when(customerRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
		EKartException e=Assertions.assertThrows(EKartException.class, ()->customerService.updateShippingAddress(customer.getEmailId(),customer.getAddress()));
		Assertions.assertEquals("CustomerService.CUSTOMER_NOT_FOUND", e.getMessage());
	}
	@Test
	public void updateShippingAddressValidTes() throws EKartException{
		Customer customer = new Customer();
		customer.setEmailId("sam@infosys.com");
		customer.setPassword("Sam@123");
		customer.setAddress("abc");
		customer.setPhoneNumber("994445670");
		customer.setName("Sam");
		
		Mockito.when(customerRepository.findById(Mockito.anyString())).thenReturn(Optional.of(customer));
		customerService.updateShippingAddress(customer.getEmailId(), "xyz");
		Assertions.assertEquals("xyz", customer.getAddress());
	}
	@Test
	public void getCustomerByEmailIdValidTest() throws EKartException
	{
		Customer customer = new Customer();
		customer.setEmailId("sam@infosys.com");
		customer.setPassword("Sam@123");
		customer.setAddress("abc");
		customer.setPhoneNumber("994445670");
		customer.setName("Sam");
		
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setPhoneNumber("994445670");
		customerDTO.setEmailId("sam@infosys.com");
		customerDTO.setAddress("abc");
		customerDTO.setName("Sam");
		customerDTO.setPassword("Sam@123");
		
		Mockito.when(customerRepository.findByEmailId(Mockito.anyString())).thenReturn(customer);
		Assertions.assertEquals(customerDTO.getEmailId(), customerService.getCustomerByEmailId(customer.getEmailId()).getEmailId());
	}
	@Test
	public void getCustomerByEmailIdInvalidTest() throws EKartException
	{
		
		Customer customer = new Customer();
		customer.setEmailId("sam@infosys.com");
		customer.setPassword("Sam@123");
		customer.setAddress("abc");
		customer.setPhoneNumber("994445670");
		customer.setName("Sam");
		
		Mockito.when(customerRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
		EKartException e=Assertions.assertThrows(EKartException.class,()->customerService.getCustomerByEmailId(customer.getEmailId()));
		Assertions.assertEquals("CustomerService.CUSTOMER_NOT_FOUND", e.getMessage());
		
				
				 
	}
}