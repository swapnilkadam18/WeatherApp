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
package com.example.android.weatherapp.model.network;

/**
 * responsible to maintain all the key value pairs on the url
 *
 * @author swapnil
 */

public class NetworkConstants {

    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?";
    //City
    public static final String KEY_Q = "q";
    public static final String PARAM_Q = "Mumbai";
    //mode
    public static final String KEY_MODE = "mode";
    public static final String PARAM_MODE = "json";
    //number of days 1-16 range
    public static final String KEY_CNT = "cnt";
    public static final String PARAM_CNT = "16";
    //units
    public static final String KEY_UNITS = "units";
    public static final String PARAM_UNITS = "metric";
    //APP_ID
    public static final String KEY_APPID = "appid";
    //needs to be signed and used
    public static final String PARAM_APPID = "d41dad3d25342612bf5a0d75366809f5";
}
