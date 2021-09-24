package com.example.settings

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.cardview.widget.CardView
import androidx.loader.content.AsyncTaskLoader
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.settings.adapters.FavouriteRecyclerAdapter
import com.example.settings.database.BookDatabase
import com.example.settings.database.BookEntity


class Favourites : Fragment() {
    lateinit var recyclerFavourite:RecyclerView
    lateinit var progressLayout:RelativeLayout
    lateinit var layoutManager:RecyclerView.LayoutManager
    lateinit var recyclerAdapter:FavouriteRecyclerAdapter
    var dbBookList= listOf<BookEntity>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_favourites,container,false)
        recyclerFavourite=view.findViewById(R.id.Favrecycler)
        progressLayout=view.findViewById(R.id.progressLayout)
        layoutManager=GridLayoutManager(activity as Context,2)
        dbBookList=RetrieveFavourites(activity as Context).execute().get()

        if(activity!=null){
            progressLayout.visibility=View.GONE
            recyclerAdapter= FavouriteRecyclerAdapter(activity as Context,dbBookList)
            recyclerFavourite.adapter=recyclerAdapter
            recyclerFavourite.layoutManager=layoutManager
        }

        return view
    }
    class RetrieveFavourites(val context: Context): AsyncTask<Void, Void,List<BookEntity>>(){
        override fun doInBackground(vararg params: Void?): List<BookEntity> {
            val db= Room.databaseBuilder(context,BookDatabase::class.java,"BOOKSDB").build()
            return db.bookDao().getAllBooks()
        }

    }
}