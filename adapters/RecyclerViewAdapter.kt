package com.example.settings.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.settings.BookActivity
import com.example.settings.R
import com.example.settings.model.Book

class RecyclerViewAdapter(private val mContext: Context, mData: List<Book>) :
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder?>() {
    private val mData: List<Book>
    private val options: RequestOptions
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View
        val inflater = LayoutInflater.from(mContext)
        view = inflater.inflate(R.layout.book_raw_item, parent, false)
        val viewHolder = MyViewHolder(view)
        viewHolder.lcontainer.setOnClickListener {
            val i = Intent(mContext, BookActivity::class.java)
            val pos: Int = viewHolder.adapterPosition
            i.putExtra("book_id",mData[pos].id)
            i.putExtra("book_title", mData[pos].title)
            i.putExtra("book_author", mData[pos].authors)
            i.putExtra("book_desc", mData[pos].description)
            i.putExtra("book_categories", mData[pos].categories)
            i.putExtra("book_publish_date", mData[pos].publishedDate)
            i.putExtra("book_price",mData[pos].price)
            i.putExtra("book_info", mData[pos].getmUrl())
            i.putExtra("book_buy", mData[pos].buy)
            i.putExtra("book_preview", mData[pos].preview)
            i.putExtra("book_thumbnail", mData[pos].thumbnail)
            mContext.startActivity(i)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, i: Int) {
        val book: Book = mData[i]
        holder.tvTitle.setText(book.title)
        holder.tvAuthor.setText(book.authors)
        holder.tvPrice.setText(book.price)
        holder.tvCategory.setText(book.categories)

        //load image from internet and set it into imageView using Glide
        Glide.with(mContext).load(book.thumbnail).apply(options).into(holder.ivThumbnail)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class MyViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivThumbnail: ImageView
        var tvTitle: TextView
        var tvCategory: TextView
        var tvPrice: TextView
        var tvAuthor: TextView
        var lcontainer: LinearLayout

        init {
            ivThumbnail = itemView.findViewById(R.id.thumbnail)
            tvTitle = itemView.findViewById(R.id.title)
            tvAuthor = itemView.findViewById(R.id.author)
            tvCategory = itemView.findViewById(R.id.category)
            tvPrice = itemView.findViewById(R.id.price)
            lcontainer = itemView.findViewById(R.id.lcontainer)
        }
    }

    init {
        this.mData = mData

        //Request option for Glide
        options = RequestOptions().centerCrop().placeholder(R.drawable.loading_shape)
            .error(R.drawable.loading_shape)
    }


}