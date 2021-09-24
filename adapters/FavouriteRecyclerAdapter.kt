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
import com.example.settings.database.BookEntity
import com.squareup.picasso.Picasso

class FavouriteRecyclerAdapter(val context: Context, val bookList: List<BookEntity>):RecyclerView.Adapter<FavouriteRecyclerAdapter.FavouriteViewHolder>() {
    class FavouriteViewHolder(view:View):RecyclerView.ViewHolder(view){
        val txttitle:TextView=view.findViewById(R.id.title)
        val txtcategory:TextView=view.findViewById(R.id.category)
        val txtprice:TextView=view.findViewById(R.id.price)
        val txtauthor:TextView=view.findViewById(R.id.author)
        val imgBookimage:ImageView=view.findViewById(R.id.bookthumbnail)
        val favcontent:RelativeLayout=view.findViewById(R.id.favlayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
      val view=LayoutInflater.from(parent.context)
          .inflate(R.layout.cardview,parent,false)
        return FavouriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
       val book=bookList[position]
        holder.txttitle.text=book.book_title
        holder.txtcategory.text=book.book_category
        holder.txtprice.text=book.book_price
        holder.txtauthor.text=book.book_author
        Picasso.get().load(book.book_thumbnail).error(R.drawable.loading_shape).into(holder.imgBookimage)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }
}