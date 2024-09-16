package com.example.testsequenia.presentation.filmsFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testsequenia.databinding.FragmentFilmsBinding
import com.example.testsequenia.domain.FilmItem
import com.example.testsequenia.presentation.adapters.FilmsListAdapter
import com.example.testsequenia.presentation.adapters.GenresListAdapter

class FilmsFragment : Fragment() {

//    private var _binding: FragmentFilmsBinding? = null
//    private val binding: FragmentFilmsBinding = _binding ?: throw RuntimeException()

    private val binding by lazy {
        FragmentFilmsBinding.inflate(layoutInflater)
    }

    private val viewModel: FilmsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        _binding = FragmentFilmsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val genres = listOf("фэнтези", "драма", "криминал", "детектив")
        val films = listOf(
            FilmItem(
                "Побег из Шоушенка",
                "https://st.kp.yandex.net/images/film_iphone/iphone360_326.jpg"
            ),
            FilmItem(
                "Зеленая миля", "https://st.kp.yandex.net/images/film_iphone/iphone360_435.jpg"
            ),
            FilmItem(
                "Форрест Гамп",
                "https://st.kp.yandex.net/images/film_iphone/iphone360_448.jpg"
            ),
            FilmItem(
                "Список Шиндлера",
                "https://st.kp.yandex.net/images/film_iphone/iphone360_329.jpg"
            )
        )

        binding.rvGenres.adapter = GenresListAdapter(genres)
//        binding.rvFilms.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvFilms.adapter = FilmsListAdapter(films)
    }

}