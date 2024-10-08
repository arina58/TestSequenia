package com.example.testsequenia.presentation.filmsFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testsequenia.domain.FilmItem
import com.example.testsequenia.domain.GenreItem
import com.example.testsequenia.domain.useCase.GetFilmsUseCase
import com.example.testsequenia.domain.useCase.GetFilteredFilmsUseCase
import com.example.testsequenia.domain.useCase.GetGenresUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FilmsViewModel(
    private val getFilmsUseCase: GetFilmsUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    private val getFilteredFilmsUseCase: GetFilteredFilmsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(State.Loading as State)
    val state = _state.asStateFlow()

    private var _genres = MutableLiveData<List<GenreItem>>()
    val genres: LiveData<List<GenreItem>> = _genres

    private lateinit var filmsData: MutableList<FilmItem>
    private lateinit var genresData: MutableList<GenreItem>

    private var currentGenrePosition = NOT_POSITION
    private var _currentPosition: Int = 0
    val currentPosition: Int
        get() = _currentPosition

    fun saveCurrentPosition(newPosition: Int) {
        _currentPosition = newPosition
    }

    init {
        viewModelScope.launch {
            loadFilms()
        }
    }

    fun refreshFilms() {
        viewModelScope.launch {
            _state.value = State.Loading
            loadFilms()
        }
    }

    fun filterFilms(genre: GenreItem) {
        val genrePosition = genresData.indexOfFirst { it.title == genre.title }
        when (currentGenrePosition) {
            NOT_POSITION -> {
                currentGenrePosition = genrePosition
                genresData[genrePosition] = genresData[genrePosition].copy(isSelected = SELECTED)
                _state.value = State.Content(getFilteredFilmsUseCase(filmsData, genre.title))
            }
            genrePosition -> {
                currentGenrePosition = NOT_POSITION
                genresData[genrePosition] = genresData[genrePosition].copy(isSelected = NOT_SELECTED)
                _state.value = State.Content(filmsData)
            }
            else -> {
                genresData[currentGenrePosition] = genresData[currentGenrePosition].copy(isSelected = NOT_SELECTED)
                currentGenrePosition = genrePosition
                genresData[genrePosition] = genresData[genrePosition].copy(isSelected = SELECTED)
                _state.value = State.Content(getFilteredFilmsUseCase(filmsData, genre.title))
            }
        }

        _genres.value = genresData.toList()
    }

    private fun loadGenres(films: List<FilmItem>) {
        viewModelScope.launch {
            genresData = getGenresUseCase(films).toMutableList()
            _genres.value = genresData.toList()
        }
    }

    private suspend fun loadFilms() {
        val filmsOrNull = getFilmsUseCase()

        if (filmsOrNull != null) {
            filmsData = filmsOrNull.sortedBy { it.localizedName }.toMutableList()
            loadGenres(filmsData)
            _state.value = State.Content(filmsData)
        } else {
            _state.value = State.Error
        }
    }

    companion object {
        private const val NOT_POSITION = -1
        private const val SELECTED = true
        private const val NOT_SELECTED = false
    }
}