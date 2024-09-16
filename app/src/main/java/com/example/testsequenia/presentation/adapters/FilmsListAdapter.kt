package com.example.testsequenia.presentation.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testsequenia.R
import com.example.testsequenia.domain.FilmItem

class FilmsListAdapter (private val dataList: List<FilmItem>) :
    RecyclerView.Adapter<FilmsListAdapter.FilmsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.film_item, parent, false)
        return FilmsViewHolder(view)
    }
    override fun onBindViewHolder(holder: FilmsViewHolder, position: Int) {
        val item = dataList[position]

        holder.tvTitle.text = item.title

        Glide.with(holder.itemView.context)
            .load(Uri.parse(item.imageURL))
            .into(holder.ivImage)
    }

    override fun getItemCount(): Int = dataList.size

    class FilmsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val ivImage: ImageView = itemView.findViewById(R.id.ivImage)
    }
}