package com.example.retrofitroom

import com.example.retrofitroom.data.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface retrofitInterface {
    @GET("current.json")
  suspend fun getCurrentWeather(
        @Query("key") key: String,
        @Query("q") q: String
    ): Response<WeatherResponse>
}