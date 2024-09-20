package com.example.testsequenia.di

import com.example.testsequenia.data.Mapper
import com.example.testsequenia.data.RepositoryImpl
import com.example.testsequenia.domain.Repository
import com.example.testsequenia.domain.useCase.GetFilmsUseCase
import com.example.testsequenia.domain.useCase.GetFilteredFilmsUseCase
import com.example.testsequenia.domain.useCase.GetGenresUseCase
import com.example.testsequenia.presentation.filmsFragment.FilmsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { Mapper() }
    single<Repository> { RepositoryImpl(get()) }
    single { GetFilmsUseCase(get()) }
    single { GetGenresUseCase() }
    single { GetFilteredFilmsUseCase() }
    viewModel { FilmsViewModel(get(), get(), get()) }
}