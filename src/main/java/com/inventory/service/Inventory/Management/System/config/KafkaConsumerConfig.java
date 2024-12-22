package com.inventory.service.Inventory.Management.System.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.inventory.service.Inventory.Management.System.dto.InventoryRequest;
import com.inventory.service.Inventory.Management.System.entity.Inventory;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

	@Value("${kafka.server.url}")
	private String kafkaServerURL;

	@Bean
	public ConsumerFactory<String, InventoryRequest> consumerFactory() {

		// Creating a map of string-object type
		Map<String, Object> config = new HashMap<>();

		// Adding the Configuration
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServerURL);
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "inventory-group");
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

		// Returning message in JSON format
		return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
				new JsonDeserializer<>(InventoryRequest.class));
	}

	// Creating a Listener
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, InventoryRequest> inventoryListener() {
		ConcurrentKafkaListenerContainerFactory<String, InventoryRequest> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());

		return factory;
	}

}
