package com.sungin.lunch.model;

import java.util.List;

/*
 급식 정보 저장

 */
public class MealInfo {
    private String date;
    private String kcal;
    private List<String> menu;
    private MealType type;


    public MealInfo(String date, String kcal, MealType type, List<String> menu) {
        this.date = date;
        this.kcal = kcal;
        this.menu = menu;
        this.type = type;
    }

    public MealType getType() {
        return type;
    }

    public String getKcal() {
        return kcal;
    }

    public String getDate() {
        return date;
    }

    public List<String> getMenu() {
        return menu;
    }

    @Override
    public String toString() {
        return "MealInfo{" +
                "date='" + date + '\'' +
                ", kcal='" + kcal + '\'' +
                ", menu=" + menu +
                ", type=" + type +
                '}';
    }
}
