package com.example.newz.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.newz.R
import com.example.newz.db.savedarticle
import com.example.newz.util
import kotlinx.android.synthetic.main.fragment_article.view.*
import kotlinx.android.synthetic.main.newlist.view.*
import kotlinx.android.synthetic.main.newlist.view.tvDescription
import kotlinx.android.synthetic.main.newlist.view.tvPublishedAt
import kotlinx.android.synthetic.main.newlist.view.tvSource
import kotlinx.android.synthetic.main.newlist.view.tvTitle

class savednewsadapter:RecyclerView.Adapter<savednewsadapter.savednewsholder>() {
    private var a= listOf<savedarticle>()

    inner class savednewsholder(itemview: View):RecyclerView.ViewHolder(itemview)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): savednewsholder {
        var view=LayoutInflater.from(parent.context).inflate(R.layout.newlist,parent,false)
        val holder=savednewsholder(view)
        return holder
    }

    override fun getItemCount(): Int {
       return a.size
    }

    override fun onBindViewHolder(holder: savednewsholder, position: Int) {
       var assert=a[position]

        holder.itemView.apply {
            Glide.with(context).load(assert.urlToImage).into(ivArticleImage)
            tvSource.text= assert.source.name
            tvDescription.text=assert.description
            tvTitle.text=assert.title
            tvPublishedAt.text= util.DateFormat(assert.publishedAt)

        }
    }
    fun setlist(article:List<savedarticle>){
        this.a=article
        notifyDataSetChanged()
    }

}