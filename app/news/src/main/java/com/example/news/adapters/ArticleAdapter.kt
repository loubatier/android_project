package com.example.news.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news.R
import com.example.news.models.Article

class ArticleAdapter (
    private val dataset: List<Article>)
    : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    class ViewHolder(val root: View) : RecyclerView.ViewHolder(root) {
        fun bind(item: Article) {

            val artTitle = root.findViewById<TextView>(R.id.article_title)
            val artDesc = root.findViewById<TextView>(R.id.article_description)
            val artImage = root.findViewById<ImageView>(R.id.article_image)

            artTitle.text = item.title
            artDesc.text = item.description
            Glide.with(root).load(item.urlToImage).into(artImage)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val rootView = LayoutInflater.from(parent.context)
            .inflate(R.layout.articles_recycler_item, parent, false)
        return ViewHolder(rootView)

    }

    override fun onBindViewHolder(holder: ArticleAdapter.ViewHolder, position: Int) {
        holder.bind(dataset[position])
    }

    override fun getItemCount() : Int = dataset.size
}