package com.example.newz.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.fragment.app.Fragment
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newz.R
import com.example.newz.adapter.ArticleAdapter
import com.example.newz.adapter.savednewsadapter
import com.example.newz.mvmm.Newsdatabase
import com.example.newz.mvmm.Newsrepo
import com.example.newz.mvmm.newsviewmodel
import com.example.newz.mvmm.newsviewmodelfactory


class FragmentSavedNews : Fragment(),MenuProvider{
    lateinit var viewmodel:newsviewmodel
    lateinit var rv: RecyclerView
    lateinit var newsadapter:savednewsadapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.CREATED)

        setHasOptionsMenu(true)
        super.onViewCreated(view, savedInstanceState)
        val dao= Newsdatabase.getdatabase(requireActivity()).getdao()
        val repo= Newsrepo(dao)
        val factory= newsviewmodelfactory(repo,requireActivity().application)
        viewmodel= ViewModelProvider(this,factory )[newsviewmodel::class.java]
        rv= view.findViewById(R.id.rvSavedNews)
        newsadapter=savednewsadapter()
        viewmodel.savednews.observe(viewLifecycleOwner,  {

           newsadapter.setlist(it)
            setuprecycleview()
        })
    }

    private fun setuprecycleview(){

        rv.apply{
            adapter=newsadapter
            layoutManager= LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu,menu)
        val item=menu.findItem(R.id.deletesaved)
        item.setVisible(true)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
       if(menuItem.itemId==R.id.deletesaved){
           val builder= AlertDialog.Builder(activity)
           builder.setTitle("Delete Menu")
           builder.setMessage("Are you sure you want to delete all saved news")
           builder.setPositiveButton("Delete all",object:DialogInterface.OnClickListener{
               override fun onClick(dialog: DialogInterface?, which: Int) {

                 viewmodel.Deleteall()
                   Toast.makeText(context, "Deleted All", Toast.LENGTH_SHORT).show()
                   view?.findNavController()?.navigate(R.id.action_fragmentSavedNews_to_fragmentBreakingNews)
               }
           })
           builder.setNegativeButton("Cancel") { dialog,which->
                     dialog.dismiss()
           }

           builder.create()
           builder.show()

           }

        return true


    }
}