package com.example.testsequenia.presentation.filmsFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.testsequenia.R
import com.example.testsequenia.databinding.FragmentFilmsBinding
import com.example.testsequenia.presentation.adapters.FilmsListAdapter
import com.example.testsequenia.presentation.adapters.GenresListAdapter
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilmsFragment : Fragment() {

    private var _binding: FragmentFilmsBinding? = null
    private val binding: FragmentFilmsBinding
        get() = _binding ?: throw RuntimeException("FragmentFilmInfoBinding == null")

    private val viewModel: FilmsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appBar = requireActivity().findViewById<MaterialToolbar>(R.id.topAppBar)
        appBar.title = resources.getString(R.string.films_title)
        appBar.navigationIcon = null

        val filmAdapter = FilmsListAdapter()
        setFilmRv(filmAdapter)

        val genresListAdapter = GenresListAdapter()
        setGenreRv(genresListAdapter)

        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is State.Error -> {
                        binding.tvFilmsTitle.visibility = GONE
                        binding.tvGenresTitle.visibility = GONE
                        binding.progressBar.visibility = GONE

                        showError()
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

                        filmAdapter.submitList(it.currencyList)
                    }
                }
            }
        }
    }

    private fun showError() {
        val snackbar = Snackbar.make(
            requireView(),
            resources.getString(R.string.loading_error_description),
            Snackbar.LENGTH_LONG
        ).setAction(resources.getString(R.string.loading_error_action_text)) {
            viewModel.refreshFilms()
        }

        val snackbarView = snackbar.view

        val textView =
            snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        val actionView =
            snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_action)

        textView.typeface = ResourcesCompat.getFont(requireContext(), R.font.roboto_regular)
        actionView.typeface = ResourcesCompat.getFont(requireContext(), R.font.roboto_medium)

        snackbar.setActionTextColor(ContextCompat.getColor(requireContext(), R.color.yellow))
        snackbar.show()
    }

    private fun setGenreRv(adapter: GenresListAdapter) {
        binding.rvGenres.adapter = adapter
        adapter.itemClickListener = { genreItem ->
            viewModel.filterFilms(genreItem)
        }

        viewModel.genres.observe(viewLifecycleOwner) {
            adapter.submitList(it.toList())
        }
    }

    private fun setFilmRv(adapter: FilmsListAdapter) {
        binding.rvFilms.adapter = adapter
        binding.rvFilms.nestedScrollTo(viewModel.currentPosition, binding.nestedScrollingView)

        adapter.itemClickListener = { film, position ->
            viewModel.saveCurrentPosition(position)
            findNavController().navigate(
                FilmsFragmentDirections.actionFilmsFragmentToFilmInfoFragment(film)
            )
        }
    }

    private fun RecyclerView.nestedScrollTo(position:Int, nestedScrollView: NestedScrollView) {
        getChildAt(position)?.y?.let {
            nestedScrollView.fling(0)
            nestedScrollView.smoothScrollTo(0, it.toInt())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}