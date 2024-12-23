package com.inventory.service.Inventory.Management.System.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.inventory.service.Inventory.Management.System.entity.Inventory;

@Configuration
public class KafkaProducerConfig { // Kafka Producer Configuartion Defined

	@Value("${kafka.server.url}")
	private String kafkaServerURL;

	@Bean
	public ProducerFactory<String, Inventory> producerFactory() {
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServerURL);
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		configProps.put(ProducerConfig.RETRIES_CONFIG, 2); // Retry up to 2 times
		configProps.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 1000); // Wait 1 second between retries
		configProps.put(ProducerConfig.ACKS_CONFIG, "1"); // Wait for leader acknowledgment

		// Other configurations
		configProps.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1);
		return new DefaultKafkaProducerFactory<>(configProps);
	}

	@Bean
	public KafkaTemplate<String, Inventory> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}

}
