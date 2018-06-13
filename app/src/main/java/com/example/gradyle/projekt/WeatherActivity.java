package com.example.gradyle.projekt;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import data.CityPreference;
import data.JSONWeatherParser;
import data.WeatherHttpClient;
import model.Weather;
import utill.Utils;

public class WeatherActivity extends AppCompatActivity {

    private TextView cityName;
    private TextView temp;
    private ImageView iconView;
    private TextView description;
    private TextView humidity;
    private TextView pressure;
    private TextView wind;
    private TextView sunrise;
    private TextView sunset;
    private TextView updated;
    private TextView proba;
    public Button changeCityB;




    Weather weather = new Weather();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        cityName = (TextView) findViewById(R.id.cityText);
        iconView = (ImageView) findViewById(R.id.thumbnailIcon);
        temp = (TextView) findViewById(R.id.tempText);
        description = (TextView) findViewById(R.id.cloudText);
        humidity = (TextView) findViewById(R.id.humidText);
        pressure = (TextView) findViewById(R.id.pressureText);
        wind = (TextView) findViewById(R.id.windText);
        sunrise = (TextView) findViewById(R.id.riseText);
        sunset = (TextView) findViewById(R.id.setText);
        updated = (TextView) findViewById(R.id.updateText);
        changeCityB = (Button) findViewById(R.id.changeCityB);
        //changeCityET = (EditText) findViewById(R.id.changeCityET);
        //proba = (TextView) findViewById(R.id.proba);

        final Button button = findViewById(R.id.changeCityB);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showInputDialog();
            }
        });

        CityPreference cityPreference = new CityPreference(WeatherActivity.this);

        renderWeatherData(cityPreference.getCity());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }



    public void renderWeatherData(String city)
    {
        WeatherTask weatherTask = new WeatherTask();
        weatherTask.execute(new String[]{city + "&units=metric"});
    }

    private class DownloadImageAsyncTask extends AsyncTask<String, Void, Bitmap>
    {
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            iconView.setImageBitmap(bitmap);

        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return downloadImage(params[0]);
        }


         private Bitmap downloadImage(String code) {
            try {
                URL url = new URL(Utils.ICON_URL + code + ".png");
                Log.d("Data : ", url.toString());
                HttpURLConnection connection = (HttpURLConnection) url .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap currentBitmap = BitmapFactory.decodeStream(input);
                return currentBitmap; }
                catch (IOException e)
                {
                    e.printStackTrace();
                 return null; } }



       /* private Bitmap downloadImage(String code)
        {
            final DefaultHttpClient client = new DefaultHttpClient();

            final HttpGet getRequest = new HttpGet(Utils.ICON_URL + code + ".png");

            try {
                HttpResponse response = client.execute(getRequest);

                final int statusCode  = response.getStatusLine().getStatusCode();

                if(statusCode != HttpStatus.SC_OK)
                {
                    Log.e("Download image", "Error: " + statusCode);
                    return null;
                }
                final HttpEntity entity = response.getEntity();

                if(entity != null)
                {
                    InputStream inputStream = null;
                    inputStream = entity.getContent();

                    final  Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    return bitmap;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }*/
    }


    private class WeatherTask extends AsyncTask<String, Void, Weather>
    {
        @Override
        protected Weather doInBackground(String... params) {

            String data = ((new WeatherHttpClient()).getWeatherData(params[0]));

            weather.iconData = weather.currentCondition.getIcon();
            weather = JSONWeatherParser.getWeather(data);


            Log.v("Data: ", weather.currentCondition.getDescription());

            new DownloadImageAsyncTask().execute(weather.iconData);

            return weather;
        }

        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);

            DateFormat df = DateFormat.getDateInstance();

            String sunriseDate = df.format(new Date(weather.place.getSunrise()));
            String sunsetDate = df.format(new Date(weather.place.getSunset()));
            String updateDate = df.format(new Date(weather.place.getLastupdate()));


            long dvsunrise = Long.valueOf(weather.place.getSunrise())*1000;
            java.util.Date dfsunrise = new java.util.Date(dvsunrise);
            String sunriseValue = new SimpleDateFormat("HH:mm").format(dfsunrise);
            long dvsunset = Long.valueOf(weather.place.getSunset())*1000;
            java.util.Date dfsunset = new java.util.Date(dvsunset);
            String sunsetValue = new SimpleDateFormat("HH:mm").format(dfsunset);
            long dvupd = Long.valueOf(weather.place.getLastupdate())*1000;
            java.util.Date dfupd = new java.util.Date(dvupd);
            String upd = new SimpleDateFormat("HH:mm").format(dfupd);


            DecimalFormat decimalFormat = new DecimalFormat("#.#");
            String tempFormat = decimalFormat.format(weather.currentCondition.getTemperature());

            cityName.setText(weather.place.getCity() + "," + weather.place.getCountry());
            temp.setText("" + tempFormat + "°C");
            humidity.setText("Humidity: " + weather.currentCondition.getHumidity() + "%");
            pressure.setText("Pressure: "+ weather.currentCondition.getPressure() + "hpa");
            wind.setText("Wind: " + weather.wind.getSpeed() + "mps");
            sunrise.setText("Sunrise: " + sunriseValue);
            sunset.setText("Sunset: " + sunsetValue);
            updated.setText("Last Updated: " + upd);
            description.setText("Condition: " + weather.currentCondition.getCondition() + "(" + weather.currentCondition.getDescription() + ")");


        }
    }

    public void showInputDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(WeatherActivity.this);
        builder.setTitle("Change City");
        final EditText cityInput = new EditText(WeatherActivity.this);
        cityInput.setInputType(InputType.TYPE_CLASS_TEXT);
        cityInput.setHint("Moscow,RU");
        builder.setView(cityInput);
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CityPreference cityPreference = new CityPreference(WeatherActivity.this);
                cityPreference.setCity(cityInput.getText().toString());

                String newCity = cityPreference.getCity();

                renderWeatherData(newCity);
            }
        });
        builder.show();
    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        return true;
    }

}