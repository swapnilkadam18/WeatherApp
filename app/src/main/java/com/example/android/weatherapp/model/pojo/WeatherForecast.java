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
package com.example.android.weatherapp.model.pojo;

/**
 * @author swapnil
 *         This class is a bean class responsible to
 *         hold forecast data obtained from any source.
 */

public class WeatherForecast {

    private String forecastData;

    /**
     * get forecast data
     *
     * @return forecastData
     */
    public String getForecastData() {
        return forecastData;
    }

    /**
     * to set forecast data
     *
     * @param forecastData data which needs to be set
     */
    public void setForecastData(String forecastData) {
        this.forecastData = forecastData;
    }
}
