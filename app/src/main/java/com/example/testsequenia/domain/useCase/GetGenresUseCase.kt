package com.example.testsequenia.domain.useCase

import com.example.testsequenia.domain.FilmItem
import com.example.testsequenia.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetGenresUseCase {

    suspend operator fun invoke(films: List<FilmItem>): List<String> {
        return withContext(Dispatchers.Default) {
            films.flatMap { film ->
                film.genres.map { genre ->
                    genre.replaceFirstChar { it.uppercase() }
                }
            }.toSet().toList()
        }
    }
}