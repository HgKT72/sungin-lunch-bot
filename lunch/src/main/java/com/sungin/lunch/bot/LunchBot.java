package com.sungin.lunch.bot;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import net.dv8tion.jda.api.requests.GatewayIntent;
import javax.annotation.PostConstruct;
import java.util.EnumSet;

@Component
public class LunchBot {

    @Value("${discord.token}")
    private String token;

    private final LunchBotListener listener;

    public LunchBot(LunchBotListener listener) {
        this.listener = listener;
    }


//JDA 인스턴스 중복 실행 오류 디스코드에서 디도스 공격으로 감지
    @PostConstruct
    public void startBot() throws Exception {
        JDABuilder.createDefault(token,
                        EnumSet.of(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT)) // 여기에 추가
                .setActivity(Activity.playing("선진학교 해킹"))
                .addEventListeners(listener)
                .build();
    }
}
