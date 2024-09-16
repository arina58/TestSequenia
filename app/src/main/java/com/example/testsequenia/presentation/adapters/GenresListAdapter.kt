package com.example.testsequenia.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testsequenia.R

class GenresListAdapter(private val dataList: List<String>) :
    RecyclerView.Adapter<GenresListAdapter.GenresViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.genre_item, parent, false)
        return GenresViewHolder(view)
    }

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size

    class GenresViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvGenreName: TextView = itemView.findViewById(R.id.tvGenreName)

        fun bind(text: String) {
            tvGenreName.text = text
        }
    }
}