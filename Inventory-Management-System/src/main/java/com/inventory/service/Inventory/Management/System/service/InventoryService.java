package com.inventory.service.Inventory.Management.System.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.inventory.service.Inventory.Management.System.entity.Inventory;
import com.inventory.service.Inventory.Management.System.repository.InventoryRepository;

@Service
public class InventoryService {

	@Autowired
	private InventoryRepository repository;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	Logger log = LoggerFactory.getLogger(InventoryService.class);

	private final String ORDER_TOPIC = "order-events";
	private final String ORDER_PLACED = "Order is placed successfully.It will be delivered in 10-15 working days.";
	private final String RETURN_TOPIC = "return-events";
	private final String ORDER_RETURNED = "Order is returned successfully.Payment amount will be credited to the official ordered account.";

	public Inventory findOrderByProductCode(Long productCode) {
		return repository.findById(productCode).get();
	}

	public String placeNewOrder(Inventory inventory) {
		String message = inventory.getProductCode() + "," + inventory.getQuantity();
		log.info("Kafka Producer Message is: " + message);
		this.kafkaTemplate.send(ORDER_TOPIC, message);
		return ORDER_PLACED;
	}

	public String returnOrder(Inventory inventory) {
		String message = inventory.getProductCode() + "," + inventory.getQuantity();
		this.kafkaTemplate.send(RETURN_TOPIC, message);
		return ORDER_RETURNED;
	}

	public void updateStock(Long productId, long quantity) {

		Inventory inventory = repository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Stock is not available. We will be back soon!"));
		log.info("Kafka Producer Message is: " + inventory.getQuantity());
		inventory.setQuantity(inventory.getQuantity() + quantity);
		repository.save(inventory);
	}

	public Inventory saveNewStock(Inventory inventory) {
		log.info("Creating New Stock Data in saveNewStock method: " + inventory);
		return repository.save(inventory);
	}

}
