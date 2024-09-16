package com.example.testsequenia.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.testsequenia.R
import com.example.testsequenia.databinding.ActivityMainBinding
import com.example.testsequenia.presentation.filmsFragment.FilmsFragment

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().replace(R.id.navHostFragment, FilmsFragment())
            .commit()
    }
}