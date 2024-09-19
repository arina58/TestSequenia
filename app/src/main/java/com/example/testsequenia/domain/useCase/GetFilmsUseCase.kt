package com.example.testsequenia.domain.useCase

import com.example.testsequenia.domain.FilmItem
import com.example.testsequenia.domain.Repository

class GetFilmsUseCase(private val repository: Repository) {

    suspend operator fun invoke(): List<FilmItem>? = repository.getFilms()
}