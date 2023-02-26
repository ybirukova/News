package com.example.homework28.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homework28.R
import com.example.homework28.domain.models.NewsData

class NewsViewHolder(
    itemView: View,
    private val itemClick: (NewsData) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    fun onBind(news: NewsData) {
        val image = itemView.findViewById<ImageView>(R.id.iv_image_of_news)
        val title = itemView.findViewById<TextView>(R.id.tv_title)
        val date = itemView.findViewById<TextView>(R.id.tv_date)

        title.text = news.title
        date.text = news.date

        if (news.urlToImage.isBlank()) {
            image.setImageResource(R.drawable.ic_defaut_news_pic)
        } else
            Glide
                .with(itemView.context)
                .load(news.urlToImage)
                .into(image)

        itemView.setOnClickListener {
            itemClick.invoke(news)
        }
    }
}