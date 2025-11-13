package com.sungin.lunch.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumSet;

@Configuration
public class BotConfig {

    // discord.token이 "비어있지 않을 때"만 생성
    @Bean
    @ConditionalOnExpression("T(org.springframework.util.StringUtils).hasText('${discord.token:}')")
    public JDA jda(@Value("${discord.token:}") String token,
                   LunchBotListener listener) throws Exception {
        return JDABuilder.createDefault(
                        token,
                        EnumSet.of(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT)
                )
                .setActivity(Activity.playing("급식 메뉴 확인하기😋 -> !급식"))
                .addEventListeners(listener)
                .build();
    }
}