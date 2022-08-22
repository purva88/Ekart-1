package com.infy.ekart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.infy.ekart.entity.Order;

public interface CustomerOrderRepository extends CrudRepository<Order, Integer> {
	// add methods if required

	List<Order> findByCustomerEmailId(String emailId);
	Optional<Order> findById(Integer orderId);
}