package com.example.testsequenia.presentation.filmInfoFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.testsequenia.databinding.FragmentFilmInfoBinding
import com.example.testsequenia.databinding.FragmentFilmsBinding

class FilmInfoFragment : Fragment() {

    private var _binding: FragmentFilmInfoBinding? = null
    private val binding: FragmentFilmInfoBinding = _binding ?: throw RuntimeException()

    private val viewModel: FilmInfoViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmInfoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}