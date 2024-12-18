package com.inventory.service.Inventory.Management.System.service.kafka;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.inventory.service.Inventory.Management.System.service.InventoryService;

@Service
public class KafkaConsumerService {
	
	Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);

	@Autowired
	private InventoryService service;

	@KafkaListener(topics = "order-events", groupId = "inventory-group")
	public void placeNewOrder(String message) {
		log.info("Calling Kafka Consumer...."+message);
		String messg[] = message.split(",");
		messg[0]= messg[0].replace("\"", "");
		log.info(messg[0]);
		Long productId = Long.valueOf(messg[0]);
		log.info("ProductId Value is: "+messg[0]);
		messg[1]= messg[1].replace("\"", "");
		log.info(messg[1]);
		Long quantity = Long.valueOf(messg[1]);
		log.info("Quantity Value is: "+messg[1]);
		log.info("Calling Update Stock Method...");
		service.updateStock(productId, -quantity);
	}

	@KafkaListener(topics = "return-events" , groupId = "inventory-group")
	public void returnOrder(String message) {
		String messg[] = message.split(",");
		Long productId = Long.valueOf(messg[0]);
		Long quantity = Long.valueOf(messg[1]);
		service.updateStock(productId, quantity);
	}

}
