package com.bss.training.kafka;

import com.bss.training.kafka.producer.ControlStreamMessageProducer;
import com.bss.training.kafka.producer.KafkaMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableMBeanExport;

@SpringBootApplication
@EnableMBeanExport
public class KafkaProducerApplication implements CommandLineRunner {
	@Autowired
	private KafkaMessageProducer kafkaMessageProducer;
	@Autowired
	private ControlStreamMessageProducer controlStreamMessageProducer;

	public static void main(String[] args) throws Exception{
		SpringApplication.run(KafkaProducerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		kafkaMessageProducer.publishMessages();
	}
}