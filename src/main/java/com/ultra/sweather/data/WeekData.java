package com.ultra.sweather.data;

import java.util.List;

// Данные о погоде на неделю
public interface WeekData {
    String getCityName(); // Город, тупо, но похуй
    // Температура на сегодня
    WeatherData getToday();
    // Температура на сегодня
    WeatherData getTomorrow();
    // Температура на 5 дней начиная с послезавтрашнего дня
    List<WeatherData> getWeek();
}
