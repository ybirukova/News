package com.example.homework28.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homework28.R
import com.example.homework28.domain.models.NewsData

class NewsAdapter(
    private val newsList: List<NewsData>,
    private val itemClick: (String) -> Unit
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_news_item, parent, false)
        return NewsViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.onBind(newsList[position])
    }

    override fun getItemCount(): Int = newsList.size

    inner class NewsViewHolder(
        itemView: View,
        private val itemClick: (String) -> Unit
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
                itemClick.invoke(news.url)
            }
        }
    }
}