package com.ultra.sweather.data;

import java.util.List;

// Получает данные
public interface WeatherManager {

    // Города по началу названия города
    List<String> getCities(String filter);

    // Погода на неделю по названию города
    WeekData getWeather(String city) throws Exception;
}
