package data;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import model.Place;
import model.Weather;
import okhttp3.internal.Util;
import utill.Utils;

public class JSONWeatherParser {
    public static Weather getWeather(String data)
    {
        Weather weather = new Weather();

        //stworzenie obiektu json z danych

        try {
            JSONObject jsonObject = new JSONObject(data);

            Place place = new Place();
            JSONObject coordObj = Utils.getObject("coord", jsonObject);
            place.setLat(Utils.getFloat("lat", coordObj));
            place.setLon(Utils.getFloat("lon", coordObj));

            JSONObject sysObj = Utils.getObject("sys", jsonObject);
            place.setCountry(Utils.getString("country", sysObj));
            place.setLastupdate(Utils.getInt("dt", jsonObject));
            place.setSunrise(Utils.getInt("sunrise", sysObj));
            place.setSunset(Utils.getInt("sunset", sysObj));
            place.setCity(Utils.getString("city", jsonObject));
            weather.place = place;

            JSONArray jsonArray = jsonObject.getJSONArray("weather");
            JSONObject jsonWeather = jsonArray.getJSONObject(0);
            weather.currentCondition.setWeatherId(Utils.getInt("id", jsonWeather));
            weather.currentCondition.setDescription(Utils.getString("description", jsonWeather));
            weather.currentCondition.setCondition(Utils.getString("main", jsonWeather));
            weather.currentCondition.setIcon(Utils.getString("icon", jsonWeather));


            JSONObject windObj = Utils.getObject("wind", jsonObject);
            weather.wind.setSpeed(Utils.getFloat("speed", windObj));
            weather.wind.setDeg(Utils.getFloat("def", windObj));

            JSONObject cloudsObj = Utils.getObject("clouds", jsonObject);
            weather.clouds.setPrecipitation(Utils.getInt("all", cloudsObj));

            return weather;

        } catch (JSONException e) {
            e.printStackTrace();

            return null;
        }
    }
}
