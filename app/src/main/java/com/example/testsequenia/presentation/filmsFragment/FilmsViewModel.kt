package com.example.testsequenia.presentation.filmsFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testsequenia.data.RepositoryImpl
import com.example.testsequenia.domain.FilmItem
import com.example.testsequenia.domain.useCase.GetFilmsUseCase
import com.example.testsequenia.domain.useCase.GetGenresUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FilmsViewModel : ViewModel() {


    private val repository = RepositoryImpl()
    private val getFilmsUseCase = GetFilmsUseCase(repository)
    private val getGenresUseCase = GetGenresUseCase()

    private val _state = MutableStateFlow(State.Loading as State)
    val state = _state.asStateFlow()

    private var _genres = MutableLiveData<List<String>>()
    val genres: LiveData<List<String>> = _genres

    init {
        viewModelScope.launch {
            loadFilms()
        }
    }

    fun loadGenres(films: List<FilmItem>) {
        viewModelScope.launch {
            _genres.value = getGenresUseCase(films) ?: emptyList()
        }
    }

    fun refreshFilms() {
        viewModelScope.launch {
            _state.value = State.Loading
            loadFilms()
        }
    }

    private suspend fun loadFilms() {
        val films = getFilmsUseCase()

        if (films != null) {
            _state.value = State.Content(films)
        } else {
            _state.value = State.Error
        }
    }
}