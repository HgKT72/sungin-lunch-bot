package com.sungin.lunch.bot;

import jakarta.annotation.PostConstruct;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DiscordBot {

    @Value("${discord.token}")
    private String token;

    private final LunchBotListener listener;

    public DiscordBot(LunchBotListener listener) {
        this.listener = listener;
    }

    @PostConstruct
    public void startBot() {
        JDABuilder.createDefault(token)
                .addEventListeners(listener)
                .setActivity(Activity.playing("급식 메뉴 확인중 😋"))
                .build();
    }
}
