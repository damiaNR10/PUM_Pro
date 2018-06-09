package com.example.gradyle.projekt;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;

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
           //super.onPostExecute(bitmap);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return downloadImage(params[0]);
        }

        private Bitmap downloadImage(String code)
        {
            final DefaultHttpClient client = new DefaultHttpClient();

            final HttpGet getRequest = new HttpGet("http://www.9ori.com/store/media/images/8ab579a656.jpg");

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
        }
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

            DecimalFormat decimalFormat = new DecimalFormat("#.#");
            String tempFormat = decimalFormat.format(weather.currentCondition.getTemperature());

            cityName.setText(weather.place.getCity() + "," + weather.place.getCountry());
            temp.setText("" + tempFormat + "°C");
            humidity.setText("Humidity: " + weather.currentCondition.getHumidity() + "%");
            pressure.setText("Pressure: "+ weather.currentCondition.getPressure() + "hpa");
            wind.setText("Wind: " + weather.wind.getSpeed() + "mps");
            sunrise.setText("Sunrise: " + sunriseDate);
            sunset.setText("Sunset: " + sunsetDate);
            updated.setText("Last Updated: " + updateDate);
            description.setText("Condition: " + weather.currentCondition.getCondition() + "(" + weather.currentCondition.getDescription() + ")");


        }
    }

    private void showInputDialog()
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

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if(id == R.id.change_cityId)
        {
            showInputDialog();
        }

        return super.onOptionsItemSelected(item);
    }
*/


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        return true;
    }

}
