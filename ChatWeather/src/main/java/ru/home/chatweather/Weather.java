package ru.home.chatweather;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

public class Weather
{
    public static String getWeather(String message, Model model) throws IOException
    {
                URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + message + "&appid=c64fd0aece79ab6829c20a429af3670c&units=metric&lang=ru");
                Scanner in = new Scanner((InputStream) url.getContent());
                StringBuilder res = new StringBuilder();
                while (in.hasNext()) {
                    res.append(in.nextLine());
                }

                JSONObject object = new JSONObject(res.toString());
                JSONObject main = object.getJSONObject("main");
                JSONObject wind = object.getJSONObject("wind");

                model.setName(object.getString("name"));
                model.setTemp(main.getDouble("temp"));
                model.setTemp_max(main.getDouble("temp_max"));
                model.setTemp_min(main.getDouble("temp_min"));
                model.setHumidity(main.getDouble("humidity"));
                model.setSpeed(wind.getDouble("speed"));
                model.setVisibility(object.getDouble(("visibility")));

                JSONArray getArray = object.getJSONArray("weather");

                for (int i = 0; i < getArray.length(); i++) {
                    JSONObject obj = getArray.getJSONObject(i);
                    model.setIcon((String) obj.get("icon"));
                    model.setDescription((String) obj.get("description"));
                    model.setMain((String) obj.get("main"));
                }

                return "Место: " + model.getName() + "\n" +
                        "Главное: " + model.getMain() + "\n" +
                        "Описание: " + model.getDescription() + "\n" +
                        "Температура: " + model.getTemp() + "%\n" +
                        "Температура max: " + model.getTemp_max() + "%\n" +
                        "Температура min: " + model.getTemp_min() + "%\n" +
                        "Видимость: " + model.getVisibility() + "km\n" +
                        "Скорость: " + model.getSpeed() + "м\n" +
                        "Влажность: " + model.getHumidity() + "%\n" +
                        "https://openweathermap.org/weathermap?basemap=map&cities=true&layer=radar&lat=51.5085&lon=-0.1257&zoom=6";

    }
}
