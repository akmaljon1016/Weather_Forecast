package com.example.retrofitroom.data


import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val current: Current,
    val location: Location
)