package com.mint.challenge.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.mint.challenge.Dtos.OrderReports;

@Configuration
public class KafkaConfig {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootStrapServer;

	public static final String KAFKATOPIC = "order-topic-new";

	@Bean
	public NewTopic newTopic() {
		return TopicBuilder.name(KAFKATOPIC).build();
	}

	public Map<String, Object> producerConfig() {
		Map<String, Object> confiqMap = new HashMap<>();
		confiqMap.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
		confiqMap.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		confiqMap.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, OrderReports.class);
		return confiqMap;
	}

	@Bean
	public ProducerFactory<String, OrderReports> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfig());
	}

	@Bean
	public KafkaTemplate<String, OrderReports> kafkaTemplate(ProducerFactory<String, OrderReports> producerFactory) {
		return new KafkaTemplate<>(producerFactory);
	}

}
