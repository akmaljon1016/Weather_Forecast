package com.example.retrofitroom

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.retrofitroom.data.Current


@Dao
interface CurrentWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(current: Current)

    @Query("SELECT * FROM current_weather")
    fun getWeather(): LiveData<Current>
}