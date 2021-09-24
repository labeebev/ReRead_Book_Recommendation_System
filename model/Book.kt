package com.example.settings.model

data class Book(
    val id:String,
    val title: String,
    val authors: String,
    val publishedDate: String,
    val description: String,
    val categories: String,
    val thumbnail: String,
    val buy: String,
    val preview: String,
    val price: String,
    val pageCount: Int,
   private val mUrl: String
) {
    private val mrRetailPrice: String? = null
    fun getmUrl(): String {
        return mUrl
    }
}