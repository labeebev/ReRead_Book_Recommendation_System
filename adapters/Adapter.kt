package com.example.settings.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.settings.R
import com.example.settings.Reviews
import kotlinx.android.synthetic.main.review_row.view.*
lateinit var txttitle:TextView
lateinit var txtauthor:TextView
lateinit var txtrev:TextView
lateinit var txtuser:TextView

class ReviewAdapter(mCtx:Context,val reviews: ArrayList<ReviewEntity> ): RecyclerView.Adapter<ReviewAdapter.ViewHolder>(){
    val mCtx=mCtx

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val txttitle=itemView.txttitle
        val txtauthor=itemView.txtauthor
        val txtrev=itemView.txtrev
        val txtuser=itemView.txtuser
    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ReviewAdapter.ViewHolder {
        val v=LayoutInflater.from(p0.context).inflate(R.layout.review_row,p0,false)


        return ViewHolder(v)

    }

    override fun onBindViewHolder(p0: ReviewAdapter.ViewHolder, p1: Int) {
        val review:ReviewEntity= reviews[p1]
        p0.txttitle.text=review.BOOKNAME
        p0.txtauthor.text=review.BOOKAUTHOR
        p0.txtrev.text=review.BOOKREVIEW
        p0.txtuser.text=review.UNAME

    }

    override fun getItemCount(): Int {
        return reviews.size
    }

}