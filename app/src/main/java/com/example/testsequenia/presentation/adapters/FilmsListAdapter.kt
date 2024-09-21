package com.example.testsequenia.presentation.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.testsequenia.R
import com.example.testsequenia.domain.FilmItem

class FilmsListAdapter : ListAdapter<FilmItem, FilmsListAdapter.FilmsViewHolder>(
    FilmItemDiffCallback()
) {

    var itemClickListener: ((item: FilmItem, position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.film_item, parent, false)
        return FilmsViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilmsViewHolder, position: Int) {
        val item = getItem(position)

        holder.tvTitle.text = item.localizedName

        if (item.imageURL != null) {
            Glide.with(holder.itemView.context)
                .load(Uri.parse(item.imageURL))
                .override(600, 900)
                .apply(RequestOptions().transform(RoundedCorners(20)))
                .error(
                    Glide.with(holder.itemView.context)
                        .load(R.drawable.default_poster)
                        .override(1000, 1500)
                        .apply(RequestOptions().transform(RoundedCorners(20)))
                )
                .into(holder.ivImage)
        } else {
            Glide.with(holder.itemView.context)
                .load(R.drawable.default_poster)
                .override(600, 900)
                .apply(RequestOptions().transform(RoundedCorners(20)))
                .into(holder.ivImage)
        }

        holder.itemView.setOnClickListener {
            itemClickListener?.invoke(item, position)
        }
    }

    class FilmsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val ivImage: ImageView = itemView.findViewById(R.id.ivImage)
    }
}