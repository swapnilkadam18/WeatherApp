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
package com.example.android.weatherapp.view;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.android.weatherapp.R;
import com.example.android.weatherapp.interfaces.MVP_Main;
import com.example.android.weatherapp.model.pojo.WeatherForecast;
import com.example.android.weatherapp.presenter.MainViewDataPresenter;
import com.example.android.weatherapp.utilities.DataModeSelector;
import com.example.android.weatherapp.view.adapters.WeatherForecastListAdapter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity implements MVP_Main.presenterToView, SwipeRefreshLayout.OnRefreshListener {

    @InjectView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @InjectView(R.id.rv_weather_data)
    RecyclerView weatherForecastList;

    @InjectView(R.id.progress)
    ProgressBar progressBar;

    @InjectView(R.id.appbar)
    AppBarLayout appBar;

    @InjectView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    private MVP_Main.viewToPresenter presenter;

    private WeatherForecastListAdapter weatherForecastListAdapter;

    private DataModeSelector dataProviderMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        /**
         * Select data mode which you want to use,
         * the reason we are storing it in a variable
         * is to avoid redundancy of different modes
         * trying to access at the same time.
         */
        dataProviderMode = DataModeSelector.OPEN_WEATHER_API_DATA;

        presenter = new MainViewDataPresenter(this, dataProviderMode);

        initRefreshLayout();

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onRefresh() {
        weatherForecastList.setAdapter(null);
        weatherForecastListAdapter.notifyDataSetChanged();
        presenter.fetchData(dataProviderMode);

    }

    @Override
    public void showProgress() {
        lockScreenOrientation();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        unlockScreenOrientation();
        if (View.VISIBLE == progressBar.getVisibility()) {
            progressBar.setVisibility(View.GONE);
        } else if (View.VISIBLE == refreshLayout.getVisibility()) {
            refreshLayout.setRefreshing(Boolean.FALSE);
        }

    }

    @Override
    public void setItems(List<WeatherForecast> items) {
        weatherForecastListAdapter = new WeatherForecastListAdapter(items);
        weatherForecastList.setAdapter(weatherForecastListAdapter);
        initRecyclerView();
    }

    @Override
    public void showMessage(String message) {

    }

    /**
     * @author Swapnil
     * method is responsible to set the
     * color scheme and set listner for
     * view pull down gesture
     */
    private void initRefreshLayout() {
        refreshLayout.setColorSchemeColors(Color.BLUE, Color.RED, Color.GREEN);
        refreshLayout.setOnRefreshListener(this);
    }

    /**
     * @author swapnil
     * this method is responsible to show
     * data in recycler view and separating
     * items within them.
     */
    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        weatherForecastList.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(weatherForecastList.getContext(),
                linearLayoutManager.getOrientation());
        weatherForecastList.addItemDecoration(dividerItemDecoration);
    }

    /**
     * to prevent crash on screen orientation in middle of network operation
     * we will be locking orientation of the screen temporarily.
     *
     * @author swapnil
     */
    private void lockScreenOrientation() {
        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    /**
     * to prevent crash on screen orientation in middle of network operation
     * we will be unlocking orientation after locking of screen was done.
     *
     * @author swapnil
     */
    private void unlockScreenOrientation() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    }
}
