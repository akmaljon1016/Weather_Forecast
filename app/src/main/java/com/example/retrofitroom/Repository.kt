package com.example.retrofitroom

import androidx.lifecycle.LiveData
import com.example.retrofitroom.data.Current

class Repository(var currentWeatherDao: CurrentWeatherDao) {

    val  current:LiveData<Current> =currentWeatherDao.getWeather()

    suspend fun insert(current: Current) {
        currentWeatherDao.upsert(current)
    }
}