package com.example.testsequenia.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.testsequenia.domain.FilmItem

class FilmItemDiffCallback : DiffUtil.ItemCallback<FilmItem>() {
    override fun areItemsTheSame(oldItem: FilmItem, newItem: FilmItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FilmItem, newItem: FilmItem): Boolean {
        return oldItem == newItem
    }
}