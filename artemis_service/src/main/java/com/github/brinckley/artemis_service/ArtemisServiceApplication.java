package com.github.brinckley.artemis_service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@Slf4j
@SpringBootApplication
public class ArtemisServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArtemisServiceApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void onStartup() {
		log.info("Service started successfully");
	}
}
