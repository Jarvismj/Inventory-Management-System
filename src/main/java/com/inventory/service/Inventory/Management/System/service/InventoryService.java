package com.inventory.service.Inventory.Management.System.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.inventory.service.Inventory.Management.System.dto.InventoryRequest;
import com.inventory.service.Inventory.Management.System.entity.Inventory;
import com.inventory.service.Inventory.Management.System.repository.InventoryRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class InventoryService {

	@Autowired
	private InventoryRepository repository;

	@Autowired
	private KafkaTemplate<String, InventoryRequest> kafkaTemplate;

	Logger log = LoggerFactory.getLogger(InventoryService.class);

	// Predefined Custom Messges
	private final String ORDER_TOPIC = "order-events";
	private final String ORDER_PLACED = "Order is placed successfully.It will be delivered in 10-15 working days.";
	private final String RETURN_TOPIC = "return-events";
	private final String ORDER_RETURNED = "Order is returned successfully.Payment amount will be credited to the official ordered account.";

	// Finding Order on the Basis of Product Code
	public Inventory findOrderByProductCode(Long productCode) {
		return repository.findById(productCode)
				.orElseThrow(() -> new RuntimeException("Product is not found. Stock will be available soon!"));
	}

	// Placing New Order and updating stock by calling kafka
	@CircuitBreaker(name = "inventoryService", fallbackMethod = "fallbackNewOrder")
	public String placeNewOrder(InventoryRequest inventory) {
		if (inventory.getNewQuantity() != null && inventory.getProductCode() != null) {
			log.info("Kafka Producer Message is: " + inventory);
			this.kafkaTemplate.send(ORDER_TOPIC, inventory);
			return ORDER_PLACED;
		}
		return "Please pass all necessary details";
	}

	// Returning Order and updating stock by calling kafka
	@CircuitBreaker(name = "inventoryService", fallbackMethod = "fallbackNewOrder")
	public String returnOrder(InventoryRequest inventory) {
		if (inventory.getNewQuantity() != null && inventory.getProductCode() != null) {
			log.info("Kafka Producer Message is: " + inventory);
			this.kafkaTemplate.send(ORDER_TOPIC, inventory);
			return ORDER_RETURNED;
		}
		return "Please pass all necessary details";

	}

	// Updating The Stock Details from Kafka Consumer
	@CircuitBreaker(name = "inventoryService", fallbackMethod = "fallbackUpdateStockOrder")
	public Inventory updateStock(InventoryRequest inventory, String orderType) {
		Inventory data = findOrderByProductCode(inventory.getProductCode());
		if (orderType != null && orderType.equalsIgnoreCase("newOrder")) {
			data.setQuantity(Long.valueOf(inventory.getQuantity()) - inventory.getNewQuantity());
		} else if (orderType != null && orderType.equalsIgnoreCase("returnOrder")) {
			data.setQuantity(Long.valueOf(inventory.getQuantity()) - inventory.getNewQuantity());
		}
		Inventory obj = repository.save(data);
		return obj;
	}

	// Saving New Inventory
	public Inventory save(Inventory inventory) {
		log.info("Creating New Stock Data in saveNewStock method: " + inventory);
		return repository.save(inventory);
	}

	// Fallabck Method
	public String fallbackNewOrder(InventoryRequest inventory,Throwable t) {
		log.error("Fallback triggered in order  due to Exception:" + t.getMessage());
		return "Fallback Response : Some intermittent issue has occured in the application.Please try again later."
				+ t.getMessage();

	}
	
	// Fallabck Method
		public String fallbackUpdateStockOrder(InventoryRequest inventory,String orderType ,Throwable t) {
			log.error("Fallback triggered in "+ orderType + " due to Exception:" + t.getMessage());
			return "Fallback triggered in "+ orderType + " due to Exception: " + t.getMessage();

		}

}
