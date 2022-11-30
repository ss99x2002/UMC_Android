package com.example.boxoffice

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.boxoffice.data.*
import com.example.boxoffice.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val keyValue ="f5c3388d3a87c8e653dc464a0da37caf"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = RetrofitService.retrofit
        retrofit.create(RetrofitAPI::class.java).getMovie(keyValue,0,20220101,10)
            .enqueue(object : Callback<Movie> {
                override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                    Log.e("Success",response.body().toString())
                    val dataList = response.body()?.boxofficeResult?.weeklyBoxOfficeList
                    initAdapter(dataList!!)
                }

                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    Log.e("Failure",t.message.toString())
                }
            })
    }

    fun initAdapter(dataList : List<WeeklyBoxOfficeList>)
    {
        val dataRVadapter = MovieRVAdapter(dataList)
        binding.rvMovie.layoutManager = LinearLayoutManager(this)
        binding.rvMovie.adapter = dataRVadapter
    }

}