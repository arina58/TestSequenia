package com.example.testsequenia.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilmItem(
    val id: Long,
    val localizedTitle: String?,
    val title: String,
    val year: Long,
    val rating: Double?,
    val imageURL: String?,
    val description: String?,
    val genres: List<String>,
) : Parcelable
