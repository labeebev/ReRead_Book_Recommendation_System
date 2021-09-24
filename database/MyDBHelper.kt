package com.example.settings.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.settings.adapters.ReviewEntity

class MyDBHelper(context:Context) : SQLiteOpenHelper(context,"USERDB",null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT,UNAME TEXT,EMAIL TEXT ,PWD TEXT,PHONENO INTEGER,GENDER TEXT,DOB DATE, ABOUT TEXT,LOG INTEGER, IMG STRING )")
        db?.execSQL("CREATE TABLE REVIEWS(REVIEWID INTEGER PRIMARY KEY AUTOINCREMENT,BOOKNAME TEXT,BOOKAUTHOR TEXT,BOOKREVIEW TEXT,UNAME TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE USERDB")
        db?.execSQL("DROP TABLE REVIEWS")
    }
    fun insertImage(
            image:String?
    ):Long{
        val db=this.writableDatabase
        val values=ContentValues()
        values.put("IMG",image)
        val id=db.insert("USERS",null,values)
        return id
    }

    fun getReviews(mCtx:Context):ArrayList<ReviewEntity>{
        val db=this.readableDatabase
        val cursor=db.rawQuery("SELECT * FROM REVIEWS",null)
        val reviews=ArrayList<ReviewEntity>()
        if(cursor.count==0)
            Toast.makeText(mCtx,"No reviews found",Toast.LENGTH_SHORT).show()
        else{
            while (cursor.moveToNext()){
                val review=ReviewEntity()
                if (cursor.getType(cursor.getColumnIndex("REVIEWID")) == Cursor.FIELD_TYPE_STRING){
                review.REVIEWID=cursor.getInt(cursor.getColumnIndex("REVIEWID"))}
                if (cursor.getType(cursor.getColumnIndex("BOOKNAME")) == Cursor.FIELD_TYPE_STRING){
                review.BOOKNAME=cursor.getString(cursor.getColumnIndex("BOOKNAME"))}
                if (cursor.getType(cursor.getColumnIndex("BOOKAUTHOR")) == Cursor.FIELD_TYPE_STRING){
                review.BOOKAUTHOR=cursor.getString(cursor.getColumnIndex("BOOKAUTHOR"))}
                if (cursor.getType(cursor.getColumnIndex("BOOKREVIEW")) == Cursor.FIELD_TYPE_STRING){
                review.BOOKREVIEW=cursor.getString(cursor.getColumnIndex("BOOKREVIEW"))}
                if (cursor.getType(cursor.getColumnIndex("UNAME")) == Cursor.FIELD_TYPE_STRING){
                review.UNAME=cursor.getString(cursor.getColumnIndex("UNAME"))}
                reviews.add(review)
            }
        }
        cursor.close()
        db.close()
        return reviews

    }

}
