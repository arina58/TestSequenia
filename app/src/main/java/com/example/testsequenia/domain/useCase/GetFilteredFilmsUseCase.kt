package com.example.testsequenia.domain.useCase

import com.example.testsequenia.domain.FilmItem

class GetFilteredFilmsUseCase {

    operator fun invoke(films: List<FilmItem>, genre: String): List<FilmItem> {
        val filterList = emptyList<FilmItem>().toMutableList()

        films.forEach { film ->
            if (film.genres.contains(genre.lowercase())) filterList.add(film)
        }

        return filterList.toList()
    }
}