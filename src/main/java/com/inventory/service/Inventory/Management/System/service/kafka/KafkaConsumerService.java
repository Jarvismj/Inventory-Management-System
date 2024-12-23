package com.inventory.service.Inventory.Management.System.service.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.inventory.service.Inventory.Management.System.entity.Inventory;
import com.inventory.service.Inventory.Management.System.service.InventoryService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class KafkaConsumerService {

	Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);

	@Autowired
	private InventoryService service;

	// Order Events
	@KafkaListener(topics = "inventory-order", groupId = "inventory-data", containerFactory = "inventoryListener")
	public Inventory placeNewOrder(Inventory inventory) {
		String orderType = "newOrder";
		log.info("Calling Update Stock Method for Placing The New Order From Kafka Consumer..." + inventory);
		return service.updateStock(inventory, orderType);
	}

	// Return Events
	@KafkaListener(topics = "inventory-return", groupId = "inventory-data", containerFactory = "inventoryListener")
	public Inventory returnOrder(Inventory inventory) {
		String orderType = "returnOrder";
		log.info("Calling Update Stock Method for Returning The Order From Kafka Consumer..." + inventory);
		return service.updateStock(inventory, orderType);
	}

}
