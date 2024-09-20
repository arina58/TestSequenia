package com.example.testsequenia.presentation.filmInfoFragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.testsequenia.R
import com.example.testsequenia.databinding.FragmentFilmInfoBinding
import com.google.android.material.appbar.MaterialToolbar

class FilmInfoFragment : Fragment() {

    private var _binding: FragmentFilmInfoBinding? = null
    private val binding: FragmentFilmInfoBinding
        get() = _binding ?: throw RuntimeException("FragmentFilmInfoBinding == null")


    private val filmItem by lazy {
        FilmInfoFragmentArgs.fromBundle(requireArguments()).filmItem
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmInfoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFilmInfo()

        val appBar = requireActivity().findViewById<MaterialToolbar>(R.id.topAppBar)

        appBar.title = filmItem.localizedName
        appBar.setNavigationIcon(R.drawable.ic_back)
        appBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setFilmInfo() {
        setPoster()

        binding.tvTitle.text = filmItem.localizedName

        binding.tvGenresAndYear.text = buildString {
            if (filmItem.genres.isNotEmpty()) {
                append(filmItem.genres.joinToString())
                append(resources.getStringArray(R.array.genres_and_year)[0])
                append(" ")
            }
            append(filmItem.year)
            append(" ")
            append(resources.getStringArray(R.array.genres_and_year)[1])
        }

        if (filmItem.rating == null)
            binding.tvRating.visibility = GONE
        else
            binding.tvRating.text = filmItem.rating.toString()

        if (filmItem.description == null)
            binding.tvDescription.visibility = GONE
        else
            binding.tvDescription.text = filmItem.description
    }

    private fun setPoster() {
        if (filmItem.imageURL != null) {
            Glide.with(requireContext())
                .load(Uri.parse(filmItem.imageURL))
                .override(600, 900)
                .apply(RequestOptions().transform(RoundedCorners(20)))
                .error(
                    Glide.with(requireContext())
                        .load(R.drawable.default_poster)
                        .override(600, 900)
                        .apply(RequestOptions().transform(RoundedCorners(20)))
                )
                .into(binding.ivPoster)
        } else {
            Glide.with(requireContext())
                .load(R.drawable.default_poster)
                .override(600, 900)
                .apply(RequestOptions().transform(RoundedCorners(20)))
                .into(binding.ivPoster)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}