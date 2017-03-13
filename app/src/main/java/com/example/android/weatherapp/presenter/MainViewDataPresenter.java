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
package com.example.android.weatherapp.presenter;

import com.example.android.weatherapp.interfaces.MVP_Main;
import com.example.android.weatherapp.model.WeatherForecastRealTimeDataProvider;
import com.example.android.weatherapp.model.pojo.WeatherForecast;
import com.example.android.weatherapp.model.WeatherForecastStaticDataProvider;
import com.example.android.weatherapp.utilities.DataModeSelector;

import java.util.List;

/**
 * @author swapnil
 *         Presenter class will be moderator between
 *         view and model packages
 *         request from view to model will go through presenter,
 *         presenter will be responsible for fetching data from
 *         model and presenting it as a response to view.
 */

public class MainViewDataPresenter implements MVP_Main.viewToPresenter, MVP_Main.modelToPresenter {

    private MVP_Main.presenterToView presenterToView;
    private MVP_Main.presenterToModel presenterToModel;
    private DataModeSelector mDataModeSelector;


    /**
     * @param mPresenterToView context to communicate back to view
     * @param dataProviderMode enum which will fetch static or real time data, we are passing this as
     *                         param so we don't have to change mode manually in this class if we change the mode
     *                         in the view.
     * @author Swapnil
     */
    public MainViewDataPresenter(MVP_Main.presenterToView mPresenterToView, DataModeSelector dataProviderMode) {
        presenterToView = mPresenterToView;
        mDataModeSelector = dataProviderMode;
    }

    @Override
    public void onResume() {
        if (presenterToView != null) {
            presenterToView.showProgress();
        }
        fetchData(mDataModeSelector);
    }

    @Override
    public void onDestroy() {
        presenterToView = null;
    }

    @Override
    public void fetchData(DataModeSelector dataProvider) {
        if (DataModeSelector.LOCAL_STATIC_DATA == dataProvider) {
            presenterToModel = new WeatherForecastStaticDataProvider(this);
            presenterToModel.provideStaticData();
        } else if (DataModeSelector.OPEN_WEATHER_API_DATA == dataProvider) {
            WeatherForecastRealTimeDataProvider realtimeDataProvider = new WeatherForecastRealTimeDataProvider(this);
            realtimeDataProvider.execute();
        }
    }

    @Override
    public void onTaskCompleted(List<WeatherForecast> items) {
        presenterToView.setItems(items);
        presenterToView.hideProgress();
    }
}
