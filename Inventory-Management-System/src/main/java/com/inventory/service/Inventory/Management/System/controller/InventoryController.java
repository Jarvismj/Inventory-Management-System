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

import com.inventory.service.Inventory.Management.System.dto.AuthRequest;
import com.inventory.service.Inventory.Management.System.entity.Inventory;
import com.inventory.service.Inventory.Management.System.service.InventoryService;
import com.inventory.service.Inventory.Management.System.service.JwtService;

@RestController
@RequestMapping("/inventoryData")
public class InventoryController {

	Logger log = LoggerFactory.getLogger(InventoryController.class);
	@Autowired
	private InventoryService service;

	@Autowired
	private JwtService jwtService;

	@GetMapping("/findOrderByProductCode/{productCode}")
	public ResponseEntity<Inventory> findOrderByProductCode(@PathVariable Long productCode) {
		return ResponseEntity.ok(service.findOrderByProductCode(productCode));
	}

	@PostMapping("/placeOrder")
	public ResponseEntity<String> placeOrder(@RequestBody Inventory inventory) {
		log.info("Placing order request:" + inventory.getProductCode());
		String mssg = service.placeNewOrder(inventory);
		return ResponseEntity.ok(mssg);

	}

	@PostMapping("/returnOrder")
	public ResponseEntity<String> returnOrder(@RequestBody Inventory inventory) {
		String mssg = service.returnOrder(inventory);
		return ResponseEntity.ok(mssg);
	}

	@PostMapping("/saveNewStock")
	public ResponseEntity<Inventory> saveNewStock(@RequestBody Inventory inventory) {
		Inventory data = service.saveNewStock(inventory);
		return ResponseEntity.ok(data);
	}

	@PostMapping("/auth/login")
	public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
		return jwtService.generateToken(authRequest.getUserName());
	}

}
