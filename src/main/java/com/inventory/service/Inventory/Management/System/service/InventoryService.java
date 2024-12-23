package com.inventory.service.Inventory.Management.System.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.inventory.service.Inventory.Management.System.entity.Inventory;
import com.inventory.service.Inventory.Management.System.repository.InventoryRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.jsonwebtoken.lang.Arrays;

@Service
public class InventoryService {

	@Autowired
	private InventoryRepository repository;

	@Autowired
	private KafkaTemplate<String, Inventory> kafkaTemplate;

	Logger log = LoggerFactory.getLogger(InventoryService.class);

	// Predefined Custom Messges
	private final String ORDER_TOPIC = "inventory-order";
	private final String ORDER_PLACED = "Order is placed successfully.It will be delivered in 10-15 working days.";
	private final String RETURN_TOPIC = "inventory-return";
	private final String ORDER_RETURNED = "Order is returned successfully.Payment amount will be credited to the official ordered account.";

	// Finding Order on the Basis of Product Code
	@CircuitBreaker(name = "inventoryService", fallbackMethod = "fallbackNewOrder")
	public Inventory findOrderByProductCode(Long productCode) {
		return repository.findById(productCode)
				.orElseThrow(() -> new RuntimeException("Product is not found. Stock will be available soon!"));
	}

	// Placing New Order and updating stock by calling kafka
	@CircuitBreaker(name = "inventoryService", fallbackMethod = "fallbackOrder") // Fallback used when there is a issue
																					// in kafka communication
	public String placeNewOrder(Inventory inventory) {
		log.info("Kafka Producer Message is: " + inventory);
		this.kafkaTemplate.send(ORDER_TOPIC, inventory);
		return ORDER_PLACED;
	}

	// Returning Order and updating stock by calling kafka
	@CircuitBreaker(name = "inventoryService", fallbackMethod = "fallbackOrder")
	public String returnOrder(Inventory inventory) {

		log.info("Kafka Producer Message is: " + inventory);
		this.kafkaTemplate.send(RETURN_TOPIC, inventory);
		return ORDER_RETURNED;

	}

	// Updating The Stock Details from Kafka Consumer
	public Inventory updateStock(Inventory inventory, String orderType) {
		Inventory obj = new Inventory();
		try {
			if (inventory.getQuantity() != 0) {
				Inventory data = findOrderByProductCode(inventory.getProductCode());
				if (orderType != null && orderType.equalsIgnoreCase("newOrder") && data.getQuantity()>0) {
					data.setQuantity(data.getQuantity() - inventory.getQuantity());
				} else if (orderType != null && orderType.equalsIgnoreCase("returnOrder")) {
					data.setQuantity(data.getQuantity() - inventory.getQuantity());
				}
				obj = repository.save(data);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return obj;
	}

	// Saving New Inventory
	public Inventory save(Inventory inventory) {
		log.info("Creating New Stock Data in saveNewStock method: " + inventory);
		return repository.save(inventory);
	}

	// Fallabck Method
	public String fallbackOrder(Inventory inventory, Throwable t) {
		log.error("Fallback triggered in order  due to Exception:" + t.getMessage());
		return "Fallback Response : Some intermittent issue has occured in the application due to Kafka.Please try again later."
				+ t.getMessage();

	}

	// Get All Stocks
	public List<Inventory> getAllProducts() {
		return repository.findAll();
	}
	
	

}
