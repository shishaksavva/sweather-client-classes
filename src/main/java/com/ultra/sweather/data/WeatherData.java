package com.ultra.sweather.data;

// Погода на день
public interface WeatherData {
    int getTemperature(); // Температура
    int getTemperatureFeeling(); // Ощущается как
    int getTemperatureMax(); // Температура максимальная
    int getTemperatureMin(); // Температура минимальная
    String getPrecipitation(); // Осадки
    int getHumidity(); // Влажность
    String getDayWeek(); // День недели
    double getWindSpeed(); // Скорость ветра м/с
}
