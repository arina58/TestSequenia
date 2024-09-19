package com.example.testsequenia.data.apiService

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("/sequeniatesttask/films.json")
    fun getData(): Call<Root>
}