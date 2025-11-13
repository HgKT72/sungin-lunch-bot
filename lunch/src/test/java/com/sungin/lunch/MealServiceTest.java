package com.sungin.lunch;

import com.sungin.lunch.model.MealInfo;
import com.sungin.lunch.model.MealType;
import com.sungin.lunch.service.MealService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class MealServiceTest {

    @Autowired
    private MealService mealService;

    @Test
    public void testUpdateMeals() throws IOException {
        // when
        mealService.updateMeals();

        String today = LocalDate.now().toString();
        MealInfo lunch = mealService.getMeal(today, MealType.LUNCH);

        // then
        assertNotNull(lunch, "오늘 식단 정보가 null입니다. HTML 구조나 Jsoup 파싱을 확인하세요.");
        System.out.println("오늘 식단: " + lunch);
    }
}
