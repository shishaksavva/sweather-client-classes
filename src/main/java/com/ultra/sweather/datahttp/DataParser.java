package com.ultra.sweather.datahttp;

import com.ultra.sweather.data.WeatherData;
import com.ultra.sweather.data.WeekData;

import java.util.*;

public class DataParser {
    public static Set<City> getCities(String text) {
        HashSet<City> cities = new HashSet<>();

        for (String line : text.split("===")) {
            String[] words = line.split("---");

            if (words.length > 3) {
                City city = new City();
                city.setName(words[1]);
                city.setLat(Double.valueOf(words[5]));
                city.setLng(Double.valueOf(words[6]));
                cities.add(city);
            }
        }

        return cities;
    }

    public static WeatherData getWeatherData(String text) {
        String[] lines = text.split("---");
        return new WeatherData() {
            @Override
            public int getTemperature() {
                return Integer.valueOf(lines[0]);
            }

            @Override
            public int getTemperatureFeeling() {
                return Integer.valueOf(lines[1]);
            }

            @Override
            public int getTemperatureMax() {
                return Integer.valueOf(lines[2]);
            }

            @Override
            public int getTemperatureMin() {
                return Integer.valueOf(lines[3]);
            }

            @Override
            public String getPrecipitation() {
                return lines[4];
            }

            @Override
            public int getHumidity() {
                return Integer.valueOf(lines[5]);
            }

            @Override
            public double getWindSpeed() {
                return Double.valueOf(lines[6]);
            }

            @Override
            public String getDayWeek() {
                return lines[7];
            }


        };
    }

    public static WeekData getWeekData(String text, String cityName) {
        List<WeatherData> list = Arrays.stream(text.split("===")).map(line -> getWeatherData(line)).toList();

        return new WeekData() {
            @Override
            public String getCityName() {
                return cityName;
            }

            @Override
            public WeatherData getToday() {
                return list.get(0);
            }

            @Override
            public WeatherData getTomorrow() {
                return list.get(1);
            }

            @Override
            public List<WeatherData> getWeek() {
                return list;
            }
        };
    }
}
