package com.example.news.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ShareCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.network.models.Article
import com.example.news.R

class ArticleAdapter(
    private val dataset: List<com.example.network.models.Article>,
    private val callback: (article: Article) -> Unit
)
    : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    inner class ViewHolder(val root: View) : RecyclerView.ViewHolder(root) {

        fun bind(item: com.example.network.models.Article) {
            root.setOnClickListener {
                callback(item)
            }

            val artTitle = root.findViewById<TextView>(R.id.article_title)
            val artDesc = root.findViewById<TextView>(R.id.article_description)
            val artImage = root.findViewById<ImageView>(R.id.article_image)
            val artButton = root.findViewById<Button>(R.id.article_button)

            /*artButton.setOnClickListener {
                ShareCompat.IntentBuilder.from(root.)
                    .setType("text/plain")
                    .setChooserTitle("Share URL")
                    .setText(item.url)
                    .startChooser();
            }*/

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