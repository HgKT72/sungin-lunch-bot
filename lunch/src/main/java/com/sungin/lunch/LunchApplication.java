package com.sungin.lunch;

import com.sungin.lunch.bot.LunchBotListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LunchApplication {

	@Value("${discord.token}")
	private String token;

	private final LunchBotListener listener;

	public LunchApplication(LunchBotListener listener) {
		this.listener = listener;
	}

	public static void main(String[] args) {
		SpringApplication.run(LunchApplication.class, args);
	}

}