package com.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

public class MyClass {

//    String json = "{\"id\": 84843049,\"name\": \"海淘君\",\"score\": 8}";
//
//    String jsonArray = "[{\"id\": 84843049,\"name\": \"海淘君\",\"score\": 8},{\"id\": 84843049,\"name\": \"海淘君\",\"score\": 9},{\"id\": 84843049,\"name\": \"海淘君\",\"score\": 10}]";


    public static void main(String[] args) {

        String jsonMap = "{'1':{'id': 84843049,'name': '海淘君','score': 8},'2':{'id': 84843049,'name': '海淘君','score': 9},'3':{'id': 84843049,'name': '海淘君','score': 10}}";
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Product>>() {
        }.getType();
        Map<String, Product> map = gson.fromJson(jsonMap, type);
        Set<String> set = map.keySet();
        for (String key : set) {
            System.out.println(">>>" + key);
            System.out.println(map.get(key).getScore() + "-----");
        }
    }
}
