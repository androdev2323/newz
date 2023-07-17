package com.example.newz.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.newz.R
import com.example.newz.db.Article
import com.example.newz.util
import kotlinx.android.synthetic.main.fragment_article.view.*
import kotlinx.android.synthetic.main.fragment_article.view.tvDescription
import kotlinx.android.synthetic.main.fragment_article.view.tvPublishedAt
import kotlinx.android.synthetic.main.fragment_article.view.tvSource
import kotlinx.android.synthetic.main.fragment_article.view.tvTitle
import kotlinx.android.synthetic.main.newlist.view.*
import java.text.DateFormat

class ArticleAdapter:RecyclerView.Adapter<ArticleAdapter.Articleholder>(){

    var newslist=listOf<Article>()
    private var listener:itemclicklistener?=null

    inner class Articleholder(itemview: View):RecyclerView.ViewHolder(itemview){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Articleholder {
   val view=LayoutInflater.from(parent.context).inflate(R.layout.newlist,parent,false)
       val viewholder=Articleholder(view)
        return viewholder
    }

    override fun getItemCount(): Int {
      return newslist.size
    }

    override fun onBindViewHolder(holder: Articleholder, position: Int) {
        val article = newslist[position]


        val requestoptions=RequestOptions()
      holder.itemView.apply {
          Glide.with(context).load(article.urlToImage).into(ivArticleImage)
          tvSource.text= article.source!!.name
          tvDescription.text=article.description
          tvTitle.text=article.title
          tvPublishedAt.text=util.DateFormat(article.publishedAt)

      }
        holder.itemView.setOnClickListener(){
            listener?.onitemclick(position,article)
        }



    }
    interface itemclicklistener{
        fun onitemclick(position:Int,Article:Article)
    }
    fun setitemclicklistener(listener:itemclicklistener){
        this.listener=listener
    }
    fun setlist(newslist:List<Article>){
        this.newslist=newslist
        notifyDataSetChanged()
    }
}