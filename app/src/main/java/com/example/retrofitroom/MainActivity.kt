package com.example.retrofitroom

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.example.retrofitroom.databinding.ActivityMainBinding
import com.example.retrofitroom.databinding.ActivityMainBindingImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val API_KEY = "acb4a4de25aa41b784651422200510"

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var service: retrofitInterface
    lateinit var viewModel: CurrentViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.swipeLayout.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                if (hasNetworkAvailable(this@MainActivity)) {
                    refresh()
                    binding.swipeLayout.isRefreshing = false
                } else {
                    Toast.makeText(this@MainActivity, "Network not availabel", Toast.LENGTH_SHORT)
                        .show()
                    binding.swipeLayout.isRefreshing=true
                }
            }
        })
        setSupportActionBar(binding.toolbar)
        service = ApiService.retrofit.create(retrofitInterface::class.java)
        viewModel = ViewModelProvider(this).get(CurrentViewModel::class.java)
        viewModel.currentWeather?.observe(this, Observer {
            Glide.with(this).load("https:" + it.condition.icon)
                .into(binding.image)
            binding.txttext.text = it.condition.text
            binding.lastUpdated.text = it.lastUpdated
            binding.huminity.text = it.humidity.toString()
            binding.tempC.text = it.tempC.toString()
            binding.windkph.text = it.windKph.toString()
        })
        binding.btnSearch.setOnClickListener {
            if (hasNetworkAvailable(this)) {
                refresh()
            } else {
                Toast.makeText(this, "Network not available", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun hasNetworkAvailable(context: Context): Boolean {
        val service = Context.CONNECTIVITY_SERVICE
        val manager = context.getSystemService(service) as ConnectivityManager
        val network = manager.activeNetworkInfo
        return (network != null)
    }

    fun refresh() {
        CoroutineScope(Dispatchers.IO).launch {
            val response =
                service.getCurrentWeather(API_KEY, binding.editSearch.text.toString())
                    .body()?.current
            response?.let {
                viewModel.insert(it)
            }
        }
    }
}