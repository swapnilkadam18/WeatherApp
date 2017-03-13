/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.weatherapp.model;

import android.os.Handler;

import com.example.android.weatherapp.interfaces.MVP_Main;
import com.example.android.weatherapp.model.pojo.WeatherForecast;

import java.util.ArrayList;
import java.util.List;

/**
 * @author swapnil
 *         this class is responsible to provide static
 *         data for weather.
 */

public class WeatherForecastStaticDataProvider implements MVP_Main.presenterToModel {

    private MVP_Main.modelToPresenter modelToPresenter;

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

    public WeatherForecastStaticDataProvider() {

    }

    public WeatherForecastStaticDataProvider(MVP_Main.modelToPresenter mModelToPresenter) {
        this.modelToPresenter = mModelToPresenter;
    }

    @Override
    public void provideStaticData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                modelToPresenter.onTaskCompleted(getData());
            }
        }, 2000);
    }

    private List<WeatherForecast> getData() {
        List<WeatherForecast> weatherList = new ArrayList<>();
        WeatherForecast forecastData;
        for (String weatherData : dummyWeatherData) {
            forecastData = new WeatherForecast();
            forecastData.setForecastData(weatherData);
            weatherList.add(forecastData);
        }
        return weatherList;
    }
}
