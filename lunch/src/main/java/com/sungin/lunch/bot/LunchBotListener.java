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

            String today = LocalDate.now(ZoneId.of("Asia/Seoul")).toString();

            MealInfo meal = mealService.getMeal(today, MealType.LUNCH);

            if (meal.getMenu().isEmpty()) {
                event.getChannel().sendMessage("❌ 오늘의 급식 정보를 찾을 수 없습니다.").queue();
                return;
            }

            StringBuilder builder=print_meal(meal);
            event.getChannel().sendMessage(builder.toString()).queue();
        }
        else if(content.equals("!내일급식")){
            String tomorrow = LocalDate.now(ZoneId.of("Asia/Seoul")).plusDays(1).toString();
            MealInfo meal = mealService.getMeal(tomorrow, MealType.LUNCH);

            if (meal.getMenu().isEmpty()) {
                event.getChannel().sendMessage("❌ 내일의 급식 정보를 찾을 수 없습니다.").queue();
                return;
            }
            StringBuilder builder=print_meal(meal);
            event.getChannel().sendMessage(builder.toString()).queue();
        }
        else if(content.equals("!도움말")){
            StringBuilder builder=print_help();
            event.getChannel().sendMessage(builder.toString()).queue();
        }
    }




    private static StringBuilder print_meal(MealInfo meal){
        StringBuilder builder = new StringBuilder();
        builder.append("📅 ").append(meal.getDate()).append(" (").append(meal.getType()).append(")\n\n");
        builder.append("🍱 메뉴\n");

        for (String item : meal.getMenu()) {
            builder.append("- ").append(item).append("\n");
        }

        builder.append("\n🔥 칼로리: ").append(meal.getKcal());
        return builder;
    }

    private static StringBuilder print_help() {
        StringBuilder builder = new StringBuilder();

        builder.append("📖 도움말\n\n");

        builder.append("🤖 사용 가능한 명령어\n\n");

        builder.append("!급식\n");
        builder.append("- 오늘의 급식을 조회합니다.\n\n");

        builder.append("!내일급식\n");
        builder.append("- 내일의 급식을 조회합니다.\n\n");

        builder.append("!도움말\n");
        builder.append("- 사용 가능한 모든 명령어를 확인합니다.\n\n");

        return builder;
    }

}
