package com.example.testsequenia.data

import android.util.Log
import com.example.testsequenia.data.apiService.RetrofitInstance
import com.example.testsequenia.data.apiService.Root
import com.example.testsequenia.domain.FilmItem
import com.example.testsequenia.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepositoryImpl : Repository {

    private val mapper = Mapper()


    override suspend fun getFilms(): List<FilmItem>? {
        return withContext(Dispatchers.IO) {
            loadURLData()?.let { root ->
                mapper.mapDbModelListToEntityList(root.films)
            }
        }
    }


    private fun loadURLData(): Root? {
        return try {
            val response =
                RetrofitInstance.api.getData().execute()
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e(LOG_NAME, "Loading error: ${response.code()} - ${response.message()}")
                null
            }
        } catch (t: Throwable) {
            Log.e(LOG_NAME, t.toString())
            null
        }
    }

    companion object {
        private const val LOG_NAME = "RetrofitInstance"
    }
}