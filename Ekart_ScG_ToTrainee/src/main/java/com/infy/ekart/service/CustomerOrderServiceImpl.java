package com.infy.ekart.service;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infy.ekart.dto.CustomerDTO;
import com.infy.ekart.dto.OrderDTO;
import com.infy.ekart.dto.OrderStatus;
import com.infy.ekart.dto.OrderedProductDTO;
import com.infy.ekart.dto.PaymentThrough;
import com.infy.ekart.dto.ProductDTO;
import com.infy.ekart.entity.Order;
import com.infy.ekart.entity.OrderedProduct;
import com.infy.ekart.exception.EKartException;
//import com.infy.ekart.exception.EKartException;
import com.infy.ekart.repository.CustomerOrderRepository;

//Add the missing annotation
@Service(value = "customerOrderService")
@Transactional
public class CustomerOrderServiceImpl implements CustomerOrderService {
@Autowired
	private CustomerOrderRepository orderRepository;
@Autowired
	private CustomerService customerService;

	@Override
	public Integer placeOrder(OrderDTO orderDTO) throws EKartException {
		CustomerDTO customerDTO = customerService.getCustomerByEmailId(orderDTO.getCustomerEmailId());
		if (customerDTO.getAddress().isBlank() || customerDTO.getAddress() == null) {
			throw new EKartException("OrderService.ADDRESS_NOT_AVAILABLE");
		}

		Order order = new Order();
		order.setDeliveryAddress(customerDTO.getAddress());
		order.setCustomerEmailId(orderDTO.getCustomerEmailId());
		order.setDateOfDelivery(orderDTO.getDateOfDelivery());
		order.setDateOfOrder(LocalDateTime.now());
		order.setPaymentThrough(PaymentThrough.valueOf(orderDTO.getPaymentThrough()));
		if (order.getPaymentThrough().equals(PaymentThrough.CREDIT_CARD)) {
			order.setDiscount(10.00d);
		} else {
			order.setDiscount(5.00d);

		}

		order.setOrderStatus(OrderStatus.PLACED);
		Double price = 0.0;
		List<OrderedProduct> orderedProducts = new ArrayList<OrderedProduct>();

		for (OrderedProductDTO orderedProductDTO : orderDTO.getOrderedProducts()) {
			if (orderedProductDTO.getProduct().getAvailableQuantity() < orderedProductDTO.getQuantity()) {
				throw new EKartException("OrderService.INSUFFICIENT_STOCK");
			}

			OrderedProduct orderedProduct = new OrderedProduct();
			orderedProduct.setProductId(orderedProductDTO.getProduct().getProductId());
			orderedProduct.setQuantity(orderedProductDTO.getQuantity());
			orderedProducts.add(orderedProduct);
			price = price + orderedProductDTO.getQuantity() * orderedProductDTO.getProduct().getPrice();

		}

		order.setOrderedProducts(orderedProducts);

		order.setTotalPrice(price * (100 - order.getDiscount()) / 100);

		orderRepository.save(order);

		return order.getOrderId();
	}

	// Get the Order details by using the OrderId
	// If not found throw EKartException with message OrderService.ORDER_NOT_FOUND
	// Else return the order details along with the ordered products
	@Override
	public OrderDTO getOrderDetails(Integer orderId) throws EKartException {

		// write your logic here
		Optional<Order> optional = orderRepository.findById(orderId);
		Order order = optional.orElseThrow(()-> new EKartException("OrderService.ORDER_NOT_FOUND"));
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setOrderId(order.getOrderId());
		orderDTO.setCustomerEmailId(order.getCustomerEmailId());
		orderDTO.setDateOfOrder(order.getDateOfOrder());
		orderDTO.setDiscount(order.getDiscount());
		orderDTO.setTotalPrice(order.getTotalPrice());
		orderDTO.setOrderStatus(order.getOrderStatus().toString());
		orderDTO.setPaymentThrough(order.getPaymentThrough().toString());
		orderDTO.setDateOfDelivery(order.getDateOfDelivery());
		orderDTO.setDeliveryAddress(order.getDeliveryAddress());
		
		List<OrderedProductDTO> orderedProductsDtos = new ArrayList<>();
		for(OrderedProduct oP: order.getOrderedProducts())   {
			OrderedProductDTO o = new OrderedProductDTO();
			o.setOrderedProductId(oP.getOrderedProductId());
			ProductDTO productDTO = new ProductDTO();
			productDTO.setProductId(oP.getProductId());
			o.setProduct(productDTO);
			o.setQuantity(oP.getQuantity());
			
			orderedProductsDtos.add(o);
			
		}
		orderDTO.setOrderedProducts(orderedProductsDtos);
		return orderDTO;
	}

	// Get the Order details by using the OrderId
	// If not found throw EKartException with message OrderService.ORDER_NOT_FOUND
	// Else update the order status with the given order status
	@Override
	public void updateOrderStatus(Integer orderId, OrderStatus orderStatus) throws EKartException {
		// write your logic here
		Optional<Order> optional= orderRepository.findById(orderId);
		Order order=optional.orElseThrow(()-> new EKartException("OrderService.ORDER_NOT_FOUND"));
		order.setOrderStatus(orderStatus);
	}

	// Get the Order details by using the OrderId
	// If not found throw EKartException with message OrderService.ORDER_NOT_FOUND
	// Else check if the order status is already confirmed, if yes then throw
	// EKartException with message OrderService.TRANSACTION_ALREADY_DONE
	// Else update the paymentThrough with the given paymentThrough option
	@Override
	public void updatePaymentThrough(Integer orderId, PaymentThrough paymentThrough) throws EKartException {

		// write your logic here
		Optional<Order> optional = orderRepository.findById(orderId);
		Order order = optional.orElseThrow(()-> new EKartException("OrderService.ORDER_NOT_FOUND"));
		
		if (order.getOrderStatus().equals(OrderStatus.CONFIRMED)) {
			throw new EKartException("OrderService.TRANSACTION_ALREADY_DONE");
			
		}
		order.setPaymentThrough(paymentThrough);
	}

	// Get the list of Order details by using the emailId
	// If the list is empty throw EKartException with message
	// OrderService.NO_ORDERS_FOUND
	// Else populate the order details along with ordered products and return that
	// list

	@Override
	public List<OrderDTO> findOrdersByCustomerEmailId(String emailId) throws EKartException {
		// write your logic here
		List<Order> list = orderRepository.findByCustomerEmailId(emailId);
		if (list.isEmpty()) {
			throw new EKartException("OrderService.NO_ORDERS_FOUND");
			
			
		}
		
		List<OrderDTO> orderDTOs = new ArrayList<OrderDTO>();
		for(Order order: list) {
			OrderDTO orderDTO = new OrderDTO();
			orderDTO.setOrderId(order.getOrderId());
			orderDTO.setCustomerEmailId(order.getCustomerEmailId());
			orderDTO.setDateOfOrder(order.getDateOfOrder());
			orderDTO.setDiscount(order.getDiscount());
			orderDTO.setTotalPrice(order.getTotalPrice());
			orderDTO.setOrderStatus(order.getOrderStatus().toString());
			orderDTO.setPaymentThrough(order.getPaymentThrough().toString());
			orderDTO.setDateOfDelivery(order.getDateOfDelivery());
			orderDTO.setDeliveryAddress(order.getDeliveryAddress());
			
			List<OrderedProductDTO> orderedProductsDtos = new ArrayList<>();
			for(OrderedProduct oP: order.getOrderedProducts())   {
				OrderedProductDTO o = new OrderedProductDTO();
				o.setOrderedProductId(oP.getOrderedProductId());
				ProductDTO productDTO = new ProductDTO();
				productDTO.setProductId(oP.getProductId());
				o.setProduct(productDTO);
				o.setQuantity(oP.getQuantity());
				
				orderedProductsDtos.add(o);
				
			}
			orderDTO.setOrderedProducts(orderedProductsDtos);
			orderDTOs.add(orderDTO);
		
		
			
		}
		
		
		return orderDTOs; 
	}

}