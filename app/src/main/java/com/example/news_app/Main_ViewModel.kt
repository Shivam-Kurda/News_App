package com.example.news_app

import androidx.lifecycle.ViewModel
import com.example.news.ApiFetcher

class Main_ViewModel : ViewModel() {
    private val apifetch=ApiFetcher()

    suspend fun getstate(lat : Float,long: Float) : String{
        return apifetch.getlocationinfo(lat,long).state
    }


}