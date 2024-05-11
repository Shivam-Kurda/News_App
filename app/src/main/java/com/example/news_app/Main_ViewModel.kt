package com.example.news_app

import android.content.Context
import android.location.Geocoder
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.example.news.ApiFetcher
import java.util.Locale

class Main_ViewModel (): ViewModel() {
    private val apifetch=ApiFetcher()


    suspend fun getstate(lat : Float,long: Float, context: Context) : String{

        lateinit var geocoder: Geocoder
        geocoder = Geocoder(context, Locale.getDefault())
        Log.d("debugg","lat -> $lat  lon-> $long")
        val addresses = geocoder.getFromLocation(lat.toDouble(), long.toDouble(), 1)
        if(addresses!=null) {
            val address = addresses[0]
            val state = address.adminArea
            Log.d("debugg","fetched $state")
            return state
        }
        else {
            return "Matruh"
        }
        //return apifetch.getlocationinfo(lat,long).state
    }


}