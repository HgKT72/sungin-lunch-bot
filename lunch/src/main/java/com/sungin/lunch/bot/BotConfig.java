package com.sungin.lunch.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumSet;

@Configuration
public class BotConfig {

    @Bean
    public JDA jda(@Value("${discord.token}") String token,
                   LunchBotListener listener) throws Exception {
        return JDABuilder.createDefault(
                        token,
                        EnumSet.of(
                                GatewayIntent.GUILD_MESSAGES,
                                GatewayIntent.MESSAGE_CONTENT
                        )
                )
                .setActivity(Activity.playing("급식 메뉴 확인중 😋"))
                .addEventListeners(listener)
                .build();
    }
}