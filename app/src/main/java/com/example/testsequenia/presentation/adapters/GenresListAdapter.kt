package com.example.testsequenia.presentation.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testsequenia.R
import com.example.testsequenia.domain.GenreItem

class GenresListAdapter : ListAdapter<GenreItem, GenresListAdapter.GenresViewHolder>(
    GenreItemDiffCallback()
) {

    var itemClickListener: ((genre: GenreItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.genre_item, parent, false)
        return GenresViewHolder(view)
    }

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        holder.bind(getItem(position))

        holder.itemView.setOnClickListener {
            itemClickListener?.invoke(getItem(position))
        }
    }

    class GenresViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvGenreName: TextView = itemView.findViewById(R.id.tvGenreName)

        fun bind(item: GenreItem) {
            tvGenreName.text = item.title
            itemView.setBackgroundColor(
                getColor(
                    item.isSelected,
                    itemView.context
                )
            )
        }

        private fun getColor(isSelected: Boolean, context: Context): Int {
            return if (isSelected) {
                ContextCompat.getColor(
                    context,
                    R.color.yellow
                )
            } else {
                Color.TRANSPARENT
            }
        }
    }
}