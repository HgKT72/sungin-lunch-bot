package com.sungin.lunch.bot;

import com.sungin.lunch.model.MealInfo;
import com.sungin.lunch.model.MealType;
import com.sungin.lunch.service.MealService;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class LunchBotListener extends ListenerAdapter {

    @Autowired
    private MealService mealService;

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw();

        if (content.equals("!급식")) {
            //String today = LocalDate.now().toString();
            String today = LocalDate.now(ZoneId.of("Asia/Seoul")).toString();

            MealInfo meal = mealService.getMeal(today, MealType.LUNCH);

            if (meal.getMenu().isEmpty()) {
                event.getChannel().sendMessage("❌ 오늘의 급식 정보를 찾을 수 없습니다.").queue();
                return;
            }

            StringBuilder builder = new StringBuilder();
            builder.append("📅 ").append(meal.getDate()).append(" (").append(meal.getType()).append(")\n\n");
            builder.append("🍱 메뉴\n");

            for (String item : meal.getMenu()) {
                builder.append("- ").append(item).append("\n");
            }

            builder.append("\n🔥 칼로리: ").append(meal.getKcal());

            event.getChannel().sendMessage(builder.toString()).queue();
        }
    }
}
