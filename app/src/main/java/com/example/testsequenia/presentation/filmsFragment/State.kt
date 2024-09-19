package com.example.testsequenia.presentation.filmsFragment

import com.example.testsequenia.domain.FilmItem

sealed class State {
    object Loading : State()
    object Error : State()
    data class Content(val currencyList: List<FilmItem>) : State()
}