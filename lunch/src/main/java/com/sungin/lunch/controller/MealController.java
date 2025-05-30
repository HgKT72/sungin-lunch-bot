package com.sungin.lunch.controller;


import com.sungin.lunch.model.MealInfo;
import com.sungin.lunch.service.MealService;
import com.sungin.lunch.model.MealType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/meal")
public class MealController {

    private MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }


    @GetMapping("/{date}/{type}")
    public ResponseEntity<MealInfo> getMeal(
            @PathVariable String date,
            @PathVariable MealType type){

        MealInfo meal=mealService.getMeal(date,type);
        System.out.println("요청 날짜: " + date + ", 타입: " + type);
        System.out.println("응답 데이터: " + meal);



        if(meal==null)return ResponseEntity.notFound().build();
        return ResponseEntity.ok(meal);


    }

    //수동 디버그

    @GetMapping("/update")
    public String update() throws IOException {
        mealService.updateMeals();
        return "업데이트 완료";
    }


}
