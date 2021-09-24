package com.example.settings

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.settings.database.MyDBHelper
import com.google.android.material.textfield.TextInputEditText

lateinit var savebtn:Button
lateinit var review1:RelativeLayout
lateinit var bookname:EditText
lateinit var bookauthor:EditText
lateinit var reviewtext:TextInputEditText
lateinit var username:EditText
lateinit var ratingBar: RatingBar
lateinit var textusername:String
class WriteReview : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_review)
        savebtn=findViewById(R.id.savereviewbt)
        review1=findViewById(R.id.relativereview)
        bookname=findViewById(R.id.et_reviewbookname)
        bookauthor=findViewById(R.id.et_reviewbookauthor)
        reviewtext=findViewById(R.id.reviewtext)
        val helper = MyDBHelper(applicationContext)
        val db = helper.writableDatabase
        var cursor = db.rawQuery("SELECT * FROM USERS WHERE LOG=1", null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val name: String = cursor.getString(cursor.getColumnIndex("UNAME"))
                    textusername = ("" + name)}while (cursor.moveToNext())
            }}
        val cv=ContentValues()
        savebtn.setOnClickListener {

            cv.put("BOOKNAME", bookname.text.toString())
            cv.put("BOOKAUTHOR", bookauthor.text.toString())
            cv.put("BOOKREVIEW", reviewtext.text.toString())
            cv.put("UNAME",textusername)
            db.insert("REVIEWS", null,cv)
            Toast.makeText(applicationContext,"Your review submitted",Toast.LENGTH_SHORT).show()
            onBackPressed()
        }


    }
}