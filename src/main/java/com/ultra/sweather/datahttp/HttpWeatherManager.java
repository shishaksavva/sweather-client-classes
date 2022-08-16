package com.ultra.sweather.datahttp;

import com.ultra.sweather.data.WeatherManager;
import com.ultra.sweather.data.WeekData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HttpWeatherManager implements WeatherManager {

    private Set<City> hash = new HashSet<>();

    @Override
    public List<String> getCities(String filter) {
        return getCitiesList(filter).stream().map(city -> city.getName()).toList();
    }

    public List<City> getCitiesList(String filter) {
        String url = "https://savva-game.herokuapp.com/cities?q=" + URLEncoder.encode(filter, StandardCharsets.UTF_8) + "&language=ru&key=6bbed54f1fab4d12ba8652c80c52ee2c";
        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection) new URL(url).openConnection();

            connection.setRequestMethod("GET");
            connection.setUseCaches(false);

            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);

            connection.connect();

            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line;

                do {
                    line = in.readLine();

                    sb.append(line);
                } while (line != null);

                hash = DataParser.getCities(sb.toString());

                return hash.stream().toList();
            }
            System.out.println("Fail request: " + connection.getResponseCode());
            return null;

        } catch (Throwable tr) {
            tr.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public WeekData getWeather(double lat, double lon, String cityName) {
        String url = "https://savva-game.herokuapp.com/forecast?lat=" + lat + "&lon=" + lon;
        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection) new URL(url).openConnection();

            connection.setRequestMethod("GET");

            connection.setConnectTimeout(100000);
            connection.setReadTimeout(100000);

            connection.connect();

            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line;

                do {
                    line = in.readLine();

                    sb.append(line);
                    sb.append("\n");
                } while (line != null);

                return DataParser.getWeekData(sb.toString(), cityName);
            } else {
                System.out.println("Fail request: " + connection.getResponseCode());
                return null;
            }
        } catch (Throwable tr) {
            tr.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    @Override
    public WeekData getWeather(String cityName) throws Exception {
        City city = hash.stream().filter((City c) -> c.getName().equals(cityName)).findFirst().orElse(null);

        if (city == null) {
            city = getCitiesList(cityName).get(0);
        }

        if (city == null) {
            throw new Exception("City not Found");
        }

        return getWeather(city.getLat(), city.getLng(), city.getName());
    }
}
