package com.example.kafkaproducer;

import com.example.kafkaproducer.producer.TestProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KafkaproducerApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private TestProducer testProducer;

	@Test
	void test() {
		testProducer.create();
	}
}
