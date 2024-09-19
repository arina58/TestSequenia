package com.example.testsequenia.presentation.filmsFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testsequenia.R
import com.example.testsequenia.databinding.FragmentFilmsBinding
import com.example.testsequenia.domain.FilmItem
import com.example.testsequenia.presentation.adapters.FilmsListAdapter
import com.example.testsequenia.presentation.adapters.GenresListAdapter
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class FilmsFragment : Fragment() {

    private var _binding: FragmentFilmsBinding? = null
    private val binding: FragmentFilmsBinding
        get() = _binding ?: throw RuntimeException("FragmentFilmInfoBinding == null")

    private val viewModel: FilmsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFilms.layoutManager = GridLayoutManager(requireContext(), 2)

        val appBar = requireActivity().findViewById<MaterialToolbar>(R.id.topAppBar)
        appBar.title = resources.getString(R.string.films_title)
        appBar.navigationIcon = null

        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is State.Error -> {
                        binding.tvFilmsTitle.visibility = GONE
                        binding.tvGenresTitle.visibility = GONE
                        binding.progressBar.visibility = GONE

                        Snackbar.make(view, resources.getString(R.string.loading_error_description), Snackbar.LENGTH_LONG)
                            .setAction(resources.getString(R.string.loading_error_action_text)) {
                                viewModel.refreshFilms()
                            }
                            .show()
                    }

                    is State.Loading -> {
                        binding.tvFilmsTitle.visibility = GONE
                        binding.tvGenresTitle.visibility = GONE
                        binding.progressBar.visibility = VISIBLE
                    }

                    is State.Content -> {
                        binding.tvFilmsTitle.visibility = VISIBLE
                        binding.tvGenresTitle.visibility = VISIBLE
                        binding.progressBar.visibility = GONE

                        viewModel.loadGenres(it.currencyList)

                        setFilmRv(it.currencyList)
                    }
                }
            }
        }

        viewModel.genres.observe(viewLifecycleOwner) {
            binding.rvGenres.adapter = GenresListAdapter(it)
        }

    }

    private fun setFilmRv(it: List<FilmItem>) {
        val adapter = FilmsListAdapter(it)
        binding.rvFilms.adapter = adapter
        adapter.itemClickListener = { film ->
            findNavController().navigate(
                FilmsFragmentDirections.actionFilmsFragmentToFilmInfoFragment(film)
            )
        }

        binding.rvFilms.isNestedScrollingEnabled = false
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}