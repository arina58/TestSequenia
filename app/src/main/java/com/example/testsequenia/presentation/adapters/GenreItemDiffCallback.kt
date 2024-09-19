package com.example.testsequenia.presentation.adapters

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.example.testsequenia.domain.GenreItem

class GenreItemDiffCallback : DiffUtil.ItemCallback<GenreItem>() {
    override fun areItemsTheSame(oldItem: GenreItem, newItem: GenreItem): Boolean {
        Log.e("GenreItemDiffCallback", "${oldItem.title} == ${newItem.title}")
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: GenreItem, newItem: GenreItem): Boolean {
        Log.e("GenreItemDiffCallback", "${oldItem} == ${newItem}")
        return oldItem == newItem
    }
}