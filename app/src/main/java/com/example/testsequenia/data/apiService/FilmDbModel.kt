package com.example.testsequenia.data.apiService

import com.google.gson.annotations.SerializedName

data class Root(
    val films: List<FilmDbModel>,
)

data class FilmDbModel(
    val id: Long,
    @SerializedName("localized_name") val localizedName: String,
    val name: String,
    val year: Long,
    val rating: Double?,
    @SerializedName("image_url") val imageUrl: String?,
    val description: String?,
    val genres: List<String>
)
