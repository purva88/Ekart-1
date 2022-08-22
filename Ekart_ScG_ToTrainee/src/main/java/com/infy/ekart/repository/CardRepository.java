package com.infy.ekart.repository;

import java.util.List;


import org.springframework.data.repository.CrudRepository;

import com.infy.ekart.entity.Card;



public interface CardRepository extends CrudRepository<Card, Integer> {
	
	// add methods if required
	List<Card> findByCustomerEmailId(String CustomerEmailId);
	List<Card> findByCustomerEmailIdAndCardType(String customerEmailId,String cardType);
}
