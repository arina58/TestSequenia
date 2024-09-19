package com.example.testsequenia.data

import com.example.testsequenia.data.apiService.FilmDbModel
import com.example.testsequenia.domain.FilmItem

class Mapper {

    fun mapDbModelToEntity(filmDbModel: FilmDbModel): FilmItem {
        return FilmItem(
            id = filmDbModel.id,
            localizedTitle = filmDbModel.localizedName,
            title = filmDbModel.name,
            year = filmDbModel.year,
            rating = filmDbModel.rating,
            imageURL = filmDbModel.imageUrl,
            description = filmDbModel.description,
            genres = filmDbModel.genres
        )
    }

    fun mapDbModelListToEntityList(films: List<FilmDbModel>): List<FilmItem> {
        return films.map { mapDbModelToEntity(it) }
    }
}