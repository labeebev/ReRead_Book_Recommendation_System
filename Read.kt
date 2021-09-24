package com.example.settings

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.loader.content.AsyncTaskLoader
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.settings.adapters.FavouriteRecyclerAdapter
import com.example.settings.adapters.ReadRecyclerAdapter
import com.example.settings.database.BookDatabase
import com.example.settings.database.BookEntity
import com.example.settings.database.ReadDatabase
import com.example.settings.database.ReadEntity


class Read : Fragment() {
    lateinit var recyclerRead:RecyclerView
    lateinit var rprogressLayout:RelativeLayout
    lateinit var rprogressBar: ProgressBar
    lateinit var rlayoutManager:RecyclerView.LayoutManager
    lateinit var rrecyclerAdapter: ReadRecyclerAdapter
    var dbReadList= listOf<ReadEntity>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_read,container,false)
        recyclerRead=view.findViewById(R.id.Readrecycler)
        rprogressLayout=view.findViewById(R.id.rprogressLayout)
        rprogressBar=view.findViewById(R.id.rprogressbar)
        rlayoutManager=GridLayoutManager(activity as Context,2)
        dbReadList= Read.RetrieveRead(activity as Context).execute().get()

        if(activity!=null){
            rprogressLayout.visibility=View.GONE
            rrecyclerAdapter= ReadRecyclerAdapter(activity as Context,dbReadList)
            recyclerRead.adapter=rrecyclerAdapter
            recyclerRead.layoutManager=rlayoutManager
        }

        return view
    }
    class RetrieveRead(val context: Context): AsyncTask<Void, Void,List<ReadEntity>>(){
        override fun doInBackground(vararg params: Void?): List<ReadEntity> {
            val rdb= Room.databaseBuilder(context, ReadDatabase::class.java,"READDB").build()
            return rdb.readDao().getAllRead()
        }

    }
}