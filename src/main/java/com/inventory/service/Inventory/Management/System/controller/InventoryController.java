package com.inventory.service.Inventory.Management.System.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.service.Inventory.Management.System.entity.Inventory;
import com.inventory.service.Inventory.Management.System.service.InventoryService;

@RestController
@RequestMapping("/v1/product") // Version 1 URL for Inventory Controller
/* Rest Controller for managing Inventory and placing order */
public class InventoryController {
	private final String ProductCode = "{productCode}";
	private final String Order = "/placeOrder";
	private final String Return = "/returnOrder";
	private final String Stock = "/save";

	Logger log = LoggerFactory.getLogger(InventoryController.class);

	@Autowired
	private InventoryService service;

	// Getting All Stocks
	@GetMapping()
	public ResponseEntity<List<Inventory>> getAllProducts() {
		List<Inventory> products = service.getAllProducts();
		return ResponseEntity.status(HttpStatus.OK).body(products);

	}

	// Checking the Stock With Product Code. For e.g. - Product Code - 10004
	@GetMapping(ProductCode)
	public ResponseEntity<Object> findOrderByProductCode(@PathVariable Long productCode) {
		Inventory data = service.findOrderByProductCode(productCode);
		if (data != null) {
			return ResponseEntity.status(HttpStatus.OK).body(data);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("Stock is not available with productCode" + productCode);
	}

	// Placing a New Order from Inventory
	@PostMapping(Order)
	public ResponseEntity<String> placeOrder(@RequestBody Inventory inventory) {
		if (inventory.getProductCode() != null && inventory.getQuantity() != null && inventory.getProductName() != null
				&& inventory.getProductPrice() != null) {
			log.info("Placing order request:" + inventory.getProductCode() + " Quantity " + inventory.getQuantity());
			String mssg = service.placeNewOrder(inventory);
			return ResponseEntity.status(HttpStatus.OK).body(mssg);
		} else {
			log.error("Some request Fields are missing in the order. Please check and resubmit");
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Some request Fields are missing in the order. Please check and resubmit");
		}

	}

	// Returning the Stock
	@PostMapping(Return)
	public ResponseEntity<String> returnOrder(@RequestBody Inventory inventory) {
		if (inventory.getProductCode() != null && inventory.getQuantity() != null && inventory.getProductName() != null
				&& inventory.getProductPrice() != null) {
			log.info("Returning Order:" + inventory.getProductCode() + " Quantity " + inventory.getQuantity());
			String mssg = service.returnOrder(inventory);
			return ResponseEntity.status(HttpStatus.OK).body(mssg);
		} else {
			log.error("Some request Fields are missing in the order. Please check and resubmit");
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Some request Fields are missing in the order. Please check and resubmit");
		}
	}

	// Saving New Stock Endpoint
	@PostMapping(Stock)
	public ResponseEntity<Inventory> save(@RequestBody Inventory inventory) {
		log.info("New Stock Request" + inventory);
		Inventory data = service.save(inventory);
		return ResponseEntity.status(HttpStatus.CREATED).body(data);
	}

}
