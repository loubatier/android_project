package com.example.news.adapters;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news.R
import com.example.news.models.Submode

class SubmodeAdapter (
    private val dataset: List<Submode>,
    private val callback: (submode: Submode) -> Unit
)
    :
    RecyclerView.Adapter<SubmodeAdapter.ViewHolder>() {

    inner class ViewHolder(val root: View) : RecyclerView.ViewHolder(root) {

        fun bind(item: Submode) {
            root.setOnClickListener {
                callback(item)
            }

            val submodeName = root.findViewById<TextView>(R.id.submode_name)
            val submodeImage = root.findViewById<ImageView>(R.id.submode_image)

            submodeName.text = item.name
            Glide.with(root).load(item.imageUrl).into(submodeImage)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val rootView = LayoutInflater.from(parent.context)
            .inflate(R.layout.submode_recycler_item, parent, false)
        return ViewHolder(rootView)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataset[position])
    }

    override fun getItemCount() : Int = dataset.size

}
