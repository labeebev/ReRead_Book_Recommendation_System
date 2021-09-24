package com.example.settings

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.settings.R
import com.example.settings.database.BookDatabase
import com.example.settings.database.BookEntity
import com.example.settings.database.ReadDatabase
import com.example.settings.database.ReadEntity
import com.example.settings.model.Book
import com.google.android.material.appbar.CollapsingToolbarLayout
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
lateinit var favbtn:Button
lateinit var readbtn:Button
class BookActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)
        favbtn=findViewById(R.id.favbtn)
        readbtn=findViewById(R.id.readbtn)

        //hide the default actionBar
        getSupportActionBar()?.hide()

        //Receive
        val extras: Bundle? = getIntent().getExtras()
        var id: String? = ""
        var title: String? = ""
        var authors: String? = ""
        var description: String? = ""
        var categories: String? = ""
        var publishDate: String? = ""
        var info: String? = ""
        var price: String? = ""
        var preview: String? = ""
        var thumbnail: String? = ""
        if (extras != null) {
            id = extras.getString("book_id")
            title = extras.getString("book_title")
            authors = extras.getString("book_author")
            description = extras.getString("book_desc")
            categories = extras.getString("book_categories")
            publishDate = extras.getString("book_publish_date")
            info = extras.getString("book_info")
            price = extras.getString("book_price")
            preview = extras.getString("book_preview")
            thumbnail = extras.getString("book_thumbnail")
        }
        val collapsingToolbarLayout: CollapsingToolbarLayout = findViewById(R.id.collapsing_id)
        collapsingToolbarLayout.setTitleEnabled(true)
        val tvTitle: TextView = findViewById(R.id.aa_book_name)
        val tvAuthors: TextView = findViewById(R.id.aa_author)
        val tvDesc: TextView = findViewById(R.id.aa_description)
        val tvCatag: TextView = findViewById(R.id.aa_categorie)
        val tvPublishDate: TextView = findViewById(R.id.aa_publish_date)
        val tvInfo: TextView = findViewById(R.id.aa_info)
        val tvPrice:TextView=findViewById(R.id.aa_price)
        val tvPreview: TextView = findViewById(R.id.aa_preview)
        val ivThumbnail: ImageView = findViewById(R.id.aa_thumbnail)
        tvTitle.text = title
        tvAuthors.text = authors
        tvDesc.text = description
        tvCatag.text = categories
        tvPublishDate.text = publishDate
        tvPrice.text=price
        val finalInfo = info
        val bookEntity = BookEntity(
            id.toString(),
            title.toString(),
            authors.toString(),
            publishDate.toString(),
            categories.toString(),
            thumbnail.toString(),
            price.toString()
        )
        val readEntity = ReadEntity(
            id.toString(),
            title.toString(),
            authors.toString(),
            publishDate.toString(),
            categories.toString(),
            thumbnail.toString(),
            price.toString()
        )

        val checkFav=DBAsyncTask(applicationContext,bookEntity,1).execute()
        val isFav=checkFav.get()
        if(isFav){
            favbtn.text="UNFAV"
            val favcolor=ContextCompat.getColor(applicationContext,R.color.colorPrimary)
            favbtn.setBackgroundColor(favcolor)

        }else{
            favbtn.text="FAV"
            val nofavcolor=ContextCompat.getColor(applicationContext,R.color.colorOnSecondary)
            favbtn.setBackgroundColor(nofavcolor)
        }
        favbtn.setOnClickListener {
            if(!DBAsyncTask(applicationContext,bookEntity,1).execute().get()){
                val async =DBAsyncTask(applicationContext,bookEntity,2).execute()
                val result=async.get()
                if(result){
                    Toast.makeText(this@BookActivity,"Added to Favourites",Toast.LENGTH_SHORT).show()
                    favbtn.text="UNFAV"
                    val favcolor=ContextCompat.getColor(applicationContext,R.color.colorPrimary)
                    favbtn.setBackgroundColor(favcolor)
                }
                else{
                    Toast.makeText(this@BookActivity,"Some error occured",Toast.LENGTH_SHORT).show()
                }
            }
            else{
                val async=DBAsyncTask(applicationContext,bookEntity,3).execute()
                val result=async.get()
                if(result){
                    Toast.makeText(this@BookActivity,"Removed from Favourites",Toast.LENGTH_SHORT).show()
                    favbtn.text="FAV"
                    val nofavcolor=ContextCompat.getColor(applicationContext,R.color.colorOnSecondary)
                    favbtn.setBackgroundColor(nofavcolor)

                }else{
                    Toast.makeText(this@BookActivity,"Some error occured",Toast.LENGTH_SHORT).show()
                }
            }
        }

        val checkRead=RDBAsyncTask(applicationContext,readEntity,1).execute()
        val isRead=checkRead.get()
        if(isRead){
            readbtn.text="UNREAD"
            val readcolor=ContextCompat.getColor(applicationContext,R.color.colorPrimary)
            readbtn.setBackgroundColor(readcolor)

        }else{
            readbtn.text="READ"
            val unreadcolor=ContextCompat.getColor(applicationContext,R.color.colorOnSecondary)
            readbtn.setBackgroundColor(unreadcolor)
        }
        readbtn.setOnClickListener {
            if(!RDBAsyncTask(applicationContext,readEntity,1).execute().get()){
                val async =RDBAsyncTask(applicationContext,readEntity,2).execute()
                val result=async.get()
                if(result){
                    Toast.makeText(this@BookActivity,"Added to TO-READ List",Toast.LENGTH_SHORT).show()
                    readbtn.text="UNREAD"
                    val readcolor=ContextCompat.getColor(applicationContext,R.color.colorPrimary)
                    readbtn.setBackgroundColor(readcolor)
                }
                else{
                    Toast.makeText(this@BookActivity,"Some error occured",Toast.LENGTH_SHORT).show()
                }
            }
            else{
                val async=RDBAsyncTask(applicationContext,readEntity,3).execute()
                val result=async.get()
                if(result){
                    Toast.makeText(this@BookActivity,"Removed from TO-READ List",Toast.LENGTH_SHORT).show()
                    readbtn.text="READ"
                    val unreadcolor=ContextCompat.getColor(applicationContext,R.color.colorOnSecondary)
                    readbtn.setBackgroundColor(unreadcolor)

                }else{
                    Toast.makeText(this@BookActivity,"Some error occured",Toast.LENGTH_SHORT).show()
                }
            }
        }


        tvInfo.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse(finalInfo))
            startActivity(i)
        }
        val finalPreview = preview
        tvPreview.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse(finalPreview))
            startActivity(i)
        }
        collapsingToolbarLayout.setTitle(title)
        val requestOptions: RequestOptions =
            RequestOptions().centerCrop().placeholder(R.drawable.loading_shape)
                .error(R.drawable.loading_shape)
        Glide.with(this).load(thumbnail).apply(requestOptions).into(ivThumbnail)
    }

    class DBAsyncTask(val context: Context, val bookEntity: BookEntity, val mode: Int) :
        AsyncTask<Void, Void, Boolean>() {
        val db = Room.databaseBuilder(context, BookDatabase::class.java, "BOOKSDB").build()
        override fun doInBackground(vararg params: Void?): Boolean {
            when (mode) {
                1 -> {
                    val book: BookEntity? = db.bookDao().getBookById(bookEntity.book_id)
                    db.close()
                    return book != null


                }
                2 -> {
                    db.bookDao().insertBook(bookEntity)
                    db.close()
                    return true
                }
                3 -> {
                    db.bookDao().DeleteBook(bookEntity)
                    db.close()
                    return true

                }
            }
            return false
        }
    }

    class RDBAsyncTask(val context: Context, val readEntity: ReadEntity, val mode: Int) :
        AsyncTask<Void, Void, Boolean>() {
        val rdb = Room.databaseBuilder(context, ReadDatabase::class.java, "READDB").build()
        override fun doInBackground(vararg params: Void?): Boolean {
            when (mode) {
                1 -> {
                    val read: ReadEntity? = rdb.readDao().getReadById(readEntity.read_id)
                    rdb.close()
                    return read != null


                }
                2 -> {
                    rdb.readDao().insertRead(readEntity)
                    rdb.close()
                    return true
                }
                3 -> {
                    rdb.readDao().DeleteRead(readEntity)
                    rdb.close()
                    return true

                }
            }
            return false
        }
    }
}