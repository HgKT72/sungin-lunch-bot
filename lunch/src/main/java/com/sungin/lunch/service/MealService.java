package com.sungin.lunch.service;

import com.sungin.lunch.model.MealInfo;
import com.sungin.lunch.model.MealType;
import jakarta.annotation.PostConstruct;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class MealService {

    private Map<String, MealInfo> mealMap = new HashMap<>();

    private MealType parseType(String label) {
        return switch (label.trim()) {
            case "조식" -> MealType.BREAKFAST;
            case "중식" -> MealType.LUNCH;
            case "석식" -> MealType.DINNER;
            default -> throw new IllegalArgumentException("Unknown meal type: " + label);
        };
    }

    private static final String[] MEAL_TYPES = {"조식", "중식", "석식"};


    //서버 시작시 무조건 한 번 초기화
    @PostConstruct
    public void init() throws IOException {
        updateMeals();
    }


    @Scheduled(cron= "0 0 6 ? * MON")
    public void updateMeals() throws IOException{

        Document doc = Jsoup.connect("https://hangukseonjin-s.goeas.kr/hangukseonjin-s/ad/fm/foodmenu/selectFoodMenuView.do?mi=9822").get();

        Elements headers = doc.select("table thead th");
        List<String> dates=new ArrayList<>();

        for(int i=1; i< headers.size(); i++){
            String[] parts=headers.get(i).text().split(" ");
            if(parts.length>=2){
                dates.add(parts[1].trim());//2025-05-19
            }
        }

        Elements rows = doc.select("table tbody tr");

        for(int i=0; i<rows.size()&& i<MEAL_TYPES.length; i++){//i: 조식/중식/석식 중 어떤 식사 타입인지(행 단위 row)
            Elements tds=rows.get(i).select("td");

            for(int j=0; j<tds.size()&& j< dates.size(); j++){//j: 요일에 해당하는 날의 메뉴
                Element td=tds.get(j);
                String date=dates.get(j);
                MealType type = parseType(MEAL_TYPES[i]);
                String key=date+"-"+type;//2025-05-24-중식

                String kcal = td.select(".fm_tit_p").text();
                Element lastP = td.select("p").last();//<td> 내부의 마지막 <p> 태그를 선택 (메뉴 줄이 <br>로 구분되어 있음)

                List<String> menuList=new ArrayList<>();
                if(lastP!=null){
                    String[] menuItems=lastP.html().split("<br>");//<br>태그 기준으로 분할
                    for(String item: menuItems){
                        String cleaned=Jsoup.parse(item).text().trim();
                        if(!cleaned.isEmpty()){
                            menuList.add(cleaned);
                        }
                    }
                }
                //String date, String kcal, MealType type, List<String> menu
                mealMap.put(key, new MealInfo(date,kcal,type,menuList));
            }

        }



    }

    public MealInfo getMeal(String date,MealType type){
        return mealMap.get(date+"-"+type);
    }

    public List<MealInfo> getWeekMeals(String datePrefix){
        List<MealInfo> result=new ArrayList<>();
        for(String key: mealMap.keySet()){
            if(key.startsWith(datePrefix)){
                result.add(mealMap.get(key));
            }
        }

        return result;
    }

}
