package com.ultra.sweather;

import com.ultra.sweather.data.WeekData;
import com.ultra.sweather.datahttp.HttpWeatherManager;

import java.util.List;

public class Program {

    public static void main(String[] args) throws Exception {
        HttpWeatherManager manager = new HttpWeatherManager();

        manager.getCitiesList("Т");

        System.out.println("Getting forecast");
        WeekData data = manager.getWeather("Тобольск");

        System.out.println(data);
    }
}
