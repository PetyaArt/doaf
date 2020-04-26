package com.doaf.presentation.streams_screen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doaf.R
import com.doaf.entities.Streams
import com.doaf.presentation.OnItemClickListener

class StreamsAdapter(
    var streams: Streams = Streams(arrayListOf(), Streams.Pagination("")),
    val onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<StreamsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_streams, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindIdViewHolder(streams.data[position])
    }

    override fun getItemCount() = streams.data.size

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        fun onBindIdViewHolder(stream: Streams.Data) {
            val title: TextView = view.findViewById(R.id.stream_title)
            val name: TextView = view.findViewById(R.id.stream_name)
            val count: TextView = view.findViewById(R.id.stream_count)
            val image: ImageView = view.findViewById(R.id.stream_image)

            title.text = stream.title
            name.text = stream.userName
            count.text = stream.viewerCount.toString()

            var url = stream.thumbnailUrl
            url = url.replace("{width}", "440")
            url = url.replace("{height}", "248")
            Glide.with(view).load(url).into(image)

            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onItemClickListener.itemClick(adapterPosition)
        }
    }
}