package com.example.news_app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.news.ApiFetcher

class HeadlineViewModel : ViewModel() {

    val news_titles =MutableLiveData<List<Article>>(listOf<Article>())
    val apifetch=ApiFetcher();
    suspend fun updatenews(location : String, country : String, category: String) {
        val newsList = apifetch.getnews(location, country, category)
        news_titles.postValue(newsList)
//        news_titles.value=apifetch.getnews(location,country,category);

    }

    suspend fun updatenews_location(location: String, pagesize: Int) {
        val newsList=apifetch.getnews_location(location,pagesize)
        news_titles.postValue(newsList)
    }
}