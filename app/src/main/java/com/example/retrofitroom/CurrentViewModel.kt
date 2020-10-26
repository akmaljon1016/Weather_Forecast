package com.example.retrofitroom

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.retrofitroom.data.Current
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrentViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: Repository? = null
     var currentWeather: LiveData<Current>? = null

    init {
        val currentDao = WeatherDatabase.getDatabase(application).currentWeatherDao()
        repository = Repository(currentDao)
        currentWeather= repository!!.current
    }

    fun insert(current: Current) = viewModelScope.launch(Dispatchers.IO) {
        repository?.insert(current)
    }
}