package com.example.android.weatherapp;

import com.example.android.weatherapp.model.WeatherForecastStaticDataProvider;
import com.example.android.weatherapp.model.pojo.WeatherForecast;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.internal.WhiteboxImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by swapnil on 13/03/2017.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(WeatherForecastStaticDataProvider.class)
public class WeatherForecastStaticDataTest {

    @Mock
    WeatherForecast weatherForecast;

    @Mock
    WeatherForecastStaticDataProvider staticDataProvider;

    private String[] dummyWeatherData = {
            "Today, May 17 - Clear - 17°C / 15°C",
            "Tomorrow - Cloudy - 19°C / 15°C",
            "Thursday - Rainy- 30°C / 11°C",
            "Friday - Thunderstorms - 21°C / 9°C",
            "Saturday - Thunderstorms - 16°C / 7°C",
            "Sunday - Rainy - 16°C / 8°C",
            "Monday - Partly Cloudy - 15°C / 10°C",
            "Tue, May 24 - Meatballs - 16°C / 18°C",
            "Wed, May 25 - Cloudy - 19°C / 15°C",
            "Thu, May 26 - Stormy - 30°C / 11°C",
            "Fri, May 27 - Hurricane - 21°C / 9°C",
            "Sat, May 28 - Meteors - 16°C / 7°C",
            "Sun, May 29 - Apocalypse - 16°C / 8°C",
            "Mon, May 30 - Post Apocalypse - 15°C / 10°C",};
    List<String> weatherList = new ArrayList(Arrays.asList(dummyWeatherData));

    @Test
    public void testGetData(){
        WeatherForecastStaticDataProvider staticDataProvider = new WeatherForecastStaticDataProvider();
        try {
            List<WeatherForecast> forecastList = WhiteboxImpl.invokeMethod(staticDataProvider,"getData");
            Assert.assertEquals(weatherList.size(),forecastList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
