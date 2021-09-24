package com.example.settings.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.settings.R
import com.example.settings.database.ReadEntity
import com.squareup.picasso.Picasso

class ReadRecyclerAdapter(val context: Context, val readList: List<ReadEntity>):RecyclerView.Adapter<ReadRecyclerAdapter.ReadViewHolder>() {
    class ReadViewHolder(view:View):RecyclerView.ViewHolder(view){
        val txttitle:TextView=view.findViewById(R.id.rtitle)
        val txtcategory:TextView=view.findViewById(R.id.rcategory)
        val txtprice:TextView=view.findViewById(R.id.rprice)
        val txtauthor:TextView=view.findViewById(R.id.rauthor)
        val imgBookimage:ImageView=view.findViewById(R.id.rbookthumbnail)
        val favcontent:RelativeLayout=view.findViewById(R.id.rlayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReadViewHolder {
      val view=LayoutInflater.from(parent.context)
          .inflate(R.layout.rcardview,parent,false)
        return ReadViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReadViewHolder, position: Int) {
       val read=readList[position]
        holder.txttitle.text=read.read_title
        holder.txtcategory.text=read.read_category
        holder.txtprice.text=read.read_price
        holder.txtauthor.text=read.read_author
        Picasso.get().load(read.read_thumbnail).error(R.drawable.loading_shape).into(holder.imgBookimage)
    }

    override fun getItemCount(): Int {
        return readList.size
    }
}