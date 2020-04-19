package com.doaf.presentation.discover_screen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doaf.R
import com.doaf.entities.Games
import com.doaf.presentation.OnItemClickListener

class DiscoverAdapter(
    var games: Games = Games(arrayListOf(), Games.Pagination("")),
    val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<DiscoverAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_discover, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindIdViewHolder(games.data[position])
    }

    override fun getItemCount() = games.data.size

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        fun onBindIdViewHolder(game: Games.Data) {
            val text: TextView = view.findViewById(R.id.game_name)
            val image: ImageView = view.findViewById(R.id.game_image)

            text.text = game.name
            var url = game.boxArtUrl
            url = url.replace("{width}", "285")
            url = url.replace("{height}", "380")
            Glide.with(view).load(url).into(image)
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
           onItemClickListener.itemClick(adapterPosition)
        }

    }
}