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
package com.example.android.weatherapp.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.weatherapp.R;
import com.example.android.weatherapp.model.pojo.WeatherForecast;

import java.util.List;

/**
 * @author swapnil
 * This class will load data in the list which
 * will be displayed and is also responsible
 * for showing different layouts in the list.
 */

public class WeatherForecastListAdapter extends RecyclerView.Adapter<WeatherForecastListAdapter.WeatherForecastViewHolder> {

    private List<WeatherForecast> mForecastListData;

    public WeatherForecastListAdapter(List<WeatherForecast> forecastListData){
        mForecastListData = forecastListData;
    }

    @Override
    public WeatherForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_forecast_list_item, parent, Boolean.FALSE);
        return new WeatherForecastViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WeatherForecastViewHolder holder, int position) {
        WeatherForecast forecastData = mForecastListData.get(position);
        holder.weatherDisplayData.setText(forecastData.getForecastData());
    }

    @Override
    public int getItemCount() {
        return mForecastListData.size();
    }

    public class WeatherForecastViewHolder extends RecyclerView.ViewHolder {
        private TextView weatherDisplayData;

        private WeatherForecastViewHolder(View itemView) {
            super(itemView);
            weatherDisplayData = (TextView) itemView.findViewById(R.id.tv_forecast_data);
        }
    }
}
