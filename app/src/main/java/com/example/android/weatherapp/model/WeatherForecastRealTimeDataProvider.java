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

import android.os.AsyncTask;

import com.example.android.weatherapp.interfaces.MVP_Main;
import com.example.android.weatherapp.model.network.NetworkConstants;
import com.example.android.weatherapp.model.pojo.ForecastDataList;
import com.example.android.weatherapp.model.pojo.WeatherApiResponse;
import com.example.android.weatherapp.model.pojo.WeatherForecast;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * background thread will use OKHttp to fetch data from the network
 * and provide the expected output.
 *
 * @author swapnil
 */
public class WeatherForecastRealTimeDataProvider extends AsyncTask<Void, Void, String> {

    private MVP_Main.modelToPresenter modelToPresenter;

    public WeatherForecastRealTimeDataProvider(MVP_Main.modelToPresenter mModelToPresenter) {
        modelToPresenter = mModelToPresenter;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String response;
        try {
            response = getResponse();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response = e.getMessage();
        }
        return response;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        parseJsonResponse(result);
    }

    /**
     * this method will parse json response
     * using GSON library
     *
     * @param result response received
     */
    private void parseJsonResponse(String result) {
        Gson gson = new Gson();
        WeatherApiResponse response = gson.fromJson(result, WeatherApiResponse.class);
        List<WeatherForecast> weatherList = new ArrayList<>();
        WeatherForecast forecastData;
        String date;
        String weatherType;
        String minMaxTemp;
        for (ForecastDataList dailyForecast : response.getForecastDataList()) {
            forecastData = new WeatherForecast();
            //The cast to (long) is very important: without it the integer overflows.
            //Multiply by 1000, since java is expecting milliseconds
            java.util.Date time = new java.util.Date((long) dailyForecast.getDt() * 1000);
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d");
            date = dateFormat.format(time);
            weatherType = dailyForecast.getWeather().get(0).getDescription();
            minMaxTemp = dailyForecast.getTemp().getMin().toString() + " " +
                    dailyForecast.getTemp().getMax().toString();
            forecastData.setForecastData(date + " " + weatherType + " " + minMaxTemp);
            weatherList.add(forecastData);
        }

        modelToPresenter.onTaskCompleted(weatherList);
    }

    /**
     * this method is responsible to build the URL and get the response
     * from the api
     *
     * @return json response
     * @author swapnil
     */
    private String getResponse() {
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(NetworkConstants.BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(NetworkConstants.KEY_Q, NetworkConstants.PARAM_Q);
        urlBuilder.addQueryParameter(NetworkConstants.KEY_MODE, NetworkConstants.PARAM_MODE);
        urlBuilder.addQueryParameter(NetworkConstants.KEY_UNITS, NetworkConstants.PARAM_UNITS);
        urlBuilder.addQueryParameter(NetworkConstants.KEY_CNT, NetworkConstants.PARAM_CNT);
        urlBuilder.addQueryParameter(NetworkConstants.KEY_APPID, NetworkConstants.PARAM_APPID);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response;
        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
