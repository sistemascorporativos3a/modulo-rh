package br.edu.up.jpa;

import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class Runner {

	private final RabbitTemplate rabbitTemplate;
	private final Receiver receiver;

	public Runner(Receiver receiver, RabbitTemplate rabbitTemplate) {
		this.receiver = receiver;
		this.rabbitTemplate = rabbitTemplate;
	}

	public void run(String... args) throws Exception {
		System.out.println("Enviando mensagem...");
		rabbitTemplate.convertAndSend(Programa.topicExchangeName, "foo.bar.baz", "Hello from RabbitMQ!");
		receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
	}

}
