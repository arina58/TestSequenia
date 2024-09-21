package com.example.testsequenia.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.testsequenia.domain.GenreItem

class GenreItemDiffCallback : DiffUtil.ItemCallback<GenreItem>() {
    override fun areItemsTheSame(oldItem: GenreItem, newItem: GenreItem): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: GenreItem, newItem: GenreItem): Boolean {
        return oldItem == newItem
    }
}