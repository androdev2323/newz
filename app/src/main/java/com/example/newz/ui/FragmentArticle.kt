package com.example.newz.ui

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.newz.R
import com.example.newz.db.Source
import com.example.newz.db.savedarticle
import com.example.newz.mvmm.Newsdatabase
import com.example.newz.mvmm.Newsrepo
import com.example.newz.mvmm.newsviewmodel
import com.example.newz.mvmm.newsviewmodelfactory
import com.example.newz.util
import kotlinx.android.synthetic.main.fragment_article.*


class FragmentArticle : Fragment() {
lateinit var arg:FragmentArticleArgs
lateinit var viewmodel:newsviewmodel
var ch=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_article, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         var dao=Newsdatabase.getdatabase(requireActivity()).getdao()
        var newsrepo=Newsrepo(dao)
        val factory=newsviewmodelfactory(newsrepo,requireActivity().application)
        viewmodel=ViewModelProvider(this,factory )[newsviewmodel::class.java]
        arg= FragmentArticleArgs.fromBundle(requireArguments())
        var a=arg.article
        tvSource.text=a.source?.name
        tvDescription.text=a.description
        tvTitle.text=a.title
        tvPublishedAt.text= util.DateFormat(a.publishedAt)
        var date=util.DateFormat(a.publishedAt)
        Glide.with(requireActivity()).load(a.urlToImage).into(articleImage)
        val source=Source(a.source?.id,a.source?.name)
        viewmodel.savednews.observe(viewLifecycleOwner,{
            for(i in it){
                if(i.title==a.title){
                    ch=i.title
                }

            }

        })
        fab.setOnClickListener(){

            Log.d("art","${a.title} ${ch}")
            if(a.title==ch){

                Toast.makeText(activity,"already added",Toast.LENGTH_SHORT).show()
                Log.d("art","exists")
            }
            else{

               viewmodel.insertnews(savedarticle(0,"","",a.description!!,date!!,source,a.title!!,a.url!!,a.urlToImage!!))
                view.findNavController().navigate(R.id.action_fragmentArticle_to_fragmentSavedNews)
                Log.d("art","saved")
            }

        }

    }

}