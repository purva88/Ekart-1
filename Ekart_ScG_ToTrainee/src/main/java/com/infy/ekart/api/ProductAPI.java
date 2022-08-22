package com.infy.ekart.api;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.ekart.dto.ProductDTO;
import com.infy.ekart.exception.EKartException;
import com.infy.ekart.service.CustomerProductService;
@RestController
@CrossOrigin
@Validated
@RequestMapping(value = "/product-api")
public class ProductAPI {
    @Autowired
	private CustomerProductService customerProductService;
    @Autowired
	private Environment environment;

	Log logger = LogFactory.getLog(ProductAPI.class);

	// Get all the product details by calling getAllProducts() of
	// CustomerProductService and return the same

	@GetMapping(value = "/products")
	public ResponseEntity<List<ProductDTO>> getAllProducts() throws EKartException {

		// write your logic here
		List<ProductDTO> productDTO=customerProductService.getAllProducts();
		
		return new ResponseEntity<>(productDTO,HttpStatus.OK);
		

	}

	// Get the specific product by calling getProductById() of
	// CustomerProductService and return the same
	@GetMapping(value = "/product/{productId}")
	public ResponseEntity<ProductDTO> getProductById(@PathVariable Integer productId) throws EKartException {
		// write your logic here
		 ProductDTO productDTO=customerProductService.getProductById(productId);
		 
		return new ResponseEntity<>(productDTO,HttpStatus.OK);
	}

	// Reduce the available quantity of product by calling reduceAvailableQuantity()
	// of CustomerProductService and return the message with property as
	// ProductAPI.REDUCE_QUANTITY_SUCCESSFULL
	@PutMapping(value = "/update/{productId}")
	public ResponseEntity<String> reduceAvailableQuantity(@PathVariable Integer productId, @RequestBody Integer quantity)
			throws EKartException {

		// write your logic here
		customerProductService.reduceAvailableQuantity(productId, quantity);
		String message=environment.getProperty("ProductAPI.REDUCE_QUANTITY_SUCCESSFULL");
		
		return new ResponseEntity<>(message,HttpStatus.OK);

	}
}