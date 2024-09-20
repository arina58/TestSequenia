package com.example.testsequenia.data

import android.util.Log
import com.example.testsequenia.data.apiService.RetrofitInstance
import com.example.testsequenia.data.apiService.Root
import com.example.testsequenia.domain.FilmItem
import com.example.testsequenia.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class RepositoryImpl(
    private val mapper: Mapper
) : Repository {

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
        } catch (e: IOException) {
            Log.e(LOG_NAME, "Network error: ${e.message}")
            null
        } catch (e: Exception) {
            Log.e(LOG_NAME, "Unexpected error: ${e.message}")
            null
        }
    }

    companion object {
        private const val LOG_NAME = "RetrofitInstance"
    }
}