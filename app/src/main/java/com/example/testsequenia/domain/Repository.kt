package com.example.testsequenia.domain

interface Repository {
    suspend fun getFilms(): List<FilmItem>?
}