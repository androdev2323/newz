package com.example.newz.ui

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newz.R
import com.example.newz.adapter.ArticleAdapter
import com.example.newz.db.Article
import com.example.newz.mvmm.Newsdatabase
import com.example.newz.mvmm.Newsrepo
import com.example.newz.mvmm.newsviewmodel
import com.example.newz.mvmm.newsviewmodelfactory
import com.example.newz.wrzpper.Resource
import kotlinx.android.synthetic.main.fragment_breaking_news.*


class FragmentBreakingNews : Fragment(), ArticleAdapter.itemclicklistener,MenuProvider {

lateinit var viewmodel:newsviewmodel
lateinit var rv:RecyclerView
lateinit var newsadapter:ArticleAdapter
lateinit var pb:ProgressBar
var newsfiltered= arrayListOf<Article>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_breaking_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.CREATED)

        setHasOptionsMenu(true)

        val dao=Newsdatabase.getdatabase(requireActivity()).getdao()
        val repo=Newsrepo(dao)
        val factory=newsviewmodelfactory(repo,requireActivity().application)
         viewmodel=ViewModelProvider(this,factory )[newsviewmodel::class.java]
        rv= view.findViewById(R.id.rvBreakingNews)
        pb=paginationProgressBar
        val cm = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nInfo = cm.activeNetworkInfo
        if (nInfo !=null && nInfo.isConnected){

            setuprecycleview()

            loadbreakingnews()



        } else {

            // IF THERE IS NO INTERNET THEN DISPLAY THIS
            noWifi.visibility = View.VISIBLE
            noWifiText.visibility = View.VISIBLE
        }
        val catlis=View.OnClickListener(){
            when(it.id){
               R.id.sportsImage-> {
                   viewmodel.getcategorynews("sports")
                   loadcategorynews()
                   setuprecycleview()
               }
                R.id.businessImage->{
                    viewmodel.getcategorynews("business")
                    loadcategorynews()
                    setuprecycleview()
                }
                R.id.techImage->{
                    viewmodel.getcategorynews("Tech")
                    loadcategorynews()
                    setuprecycleview()
                }
                R.id.breakingImage->{
              viewmodel.getbreakingnews("us")
                    loadbreakingnews()
                    setuprecycleview()

                }
            }
        }
          sportsImage.setOnClickListener(catlis)
        businessImage.setOnClickListener(catlis)
        techImage.setOnClickListener(catlis)
        breakingImage.setOnClickListener(catlis)
        newsadapter.setitemclicklistener(this)



    }


    private fun loadcategorynews(){
        viewmodel.categorynews.observe(viewLifecycleOwner,{response->
            when(response){
                is Resource.Error -> {
                    hidepbbar()
                    Log.d("", response.message!!)
                }
                is Resource.Loading -> showpb()
                is Resource.success -> {
                    hidepbbar()
                    response.data?.let {
                        catresponse->
                        newsfiltered=catresponse.articles as ArrayList<Article>
                        newsadapter.setlist(catresponse.articles)
                    }
                }
            }

        })

    }

    private fun loadbreakingnews() {
        viewmodel.breakingnews.observe(viewLifecycleOwner,{response->
            when(response){
                is Resource.Error -> {
                    hidepbbar()
                    Log.d("breakfrag",response.message!!)
                }
                is Resource.Loading -> {showpb()}
                is Resource.success -> {
                    hidepbbar()
                    response.data?.let {
                            newsresponse->

                        newsfiltered=newsresponse.articles as ArrayList<Article>
                        newsadapter.setlist(newsresponse.articles)

                    }
                }
            }
        })
    }



    private fun setuprecycleview(){
        newsadapter= ArticleAdapter()
       newsadapter.setitemclicklistener(this)
        rv.apply{
            adapter=newsadapter
            layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
        }
    }

    override fun onitemclick(position: Int, Article: Article) {
        val action = FragmentBreakingNewsDirections.actionFragmentBreakingNewsToFragmentArticle(Article)
        view?.findNavController()?.navigate(action)


    }

    private fun showpb() {
       pb.visibility=View.VISIBLE
    }

    private fun hidepbbar() {
        pb.visibility=View.INVISIBLE
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu,menu)
        val savedNews= menu.findItem(R.id.savednewsfrag)
      savedNews.setVisible(true)

        val menuitem=menu.findItem(R.id.search)
        val searchview=menuitem.actionView as androidx.appcompat.widget.SearchView

        searchview.setOnSearchClickListener(){
         savedNews.setVisible(false)
        }

        searchview.queryHint="Search news"
        searchview.setOnQueryTextListener(object:androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                 filternews(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filternews(newText)
                return true
            }

        })
      return  super.onCreateOptionsMenu(menu, menuInflater)


    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.savednewsfrag){

            view?.findNavController()?.navigate(R.id.action_fragmentBreakingNews_to_fragmentSavedNews)
        }


        return true

    }
    private fun filternews(news:String?){
        var newlist= arrayListOf<Article>()
        for(i in newsfiltered){
            if(i.title!!.contains(news!!)){
                newlist.add(i)

            }

        }
          Log.d("query"," ${newlist.size} ")
        setuprecycleview()
        newsadapter.setlist(newlist)


    }
}