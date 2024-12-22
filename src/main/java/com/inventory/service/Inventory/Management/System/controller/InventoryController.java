package com.inventory.service.Inventory.Management.System.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.service.Inventory.Management.System.dto.InventoryRequest;
import com.inventory.service.Inventory.Management.System.entity.Inventory;
import com.inventory.service.Inventory.Management.System.service.InventoryService;

@RestController
@RequestMapping("/v1/inventoryData") // Version 1 URL for Inventory Controller
/* Rest Controller for managing Inventory and placing order */
public class InventoryController {
	private final String ProductCode = "{productCode}";
	private final String Order = "/placeOrder";
	private final String Return = "/returnOrder";
	private final String Stock = "/Stock";

	Logger log = LoggerFactory.getLogger(InventoryController.class);

	@Autowired
	private InventoryService service;

	// Checking the Stock With Product Code. For e.g. - Product Code - 10004
	@GetMapping(ProductCode)
	public ResponseEntity<Inventory> findOrderByProductCode(@PathVariable Long productCode) {
		return ResponseEntity.ok(service.findOrderByProductCode(productCode));
	}

	// Placing a New Order from Inventory
	@PostMapping(Order)
	public ResponseEntity<String> placeOrder(@RequestBody InventoryRequest inventory) {
		log.info("Placing order request:" + inventory.getProductCode() + " Quantity " + inventory.getNewQuantity());
		String mssg = service.placeNewOrder(inventory);
		return ResponseEntity.ok(mssg);

	}

	// Returning the Stock
	@PostMapping(Return)
	public ResponseEntity<String> returnOrder(@RequestBody InventoryRequest inventory) {
		log.info("Returning Order:" + inventory.getProductCode() + " Quantity " + inventory.getNewQuantity());
		String mssg = service.returnOrder(inventory);
		return ResponseEntity.ok(mssg);
	}

	// Saving New Stock Endpoint
	@PostMapping(Stock)
	public ResponseEntity<Inventory> save(@RequestBody Inventory inventory) {
		log.info("New Stock Request" + inventory);
		Inventory data = service.save(inventory);
		return ResponseEntity.ok(data);
	}

}
