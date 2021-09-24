package com.example.settings

import android.content.Context
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.settings.RoundedBottomSheetDialogFragment
import com.example.settings.adapters.RecyclerViewAdapter
import com.example.settings.model.Book
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONException
import org.json.JSONObject


lateinit var coordinatorLayout: CoordinatorLayout
lateinit var bottomAppBar: BottomAppBar
lateinit var bottomNavigationView: BottomNavigationView
lateinit var frameLayout: FrameLayout
lateinit var floatingActionButton: FloatingActionButton
lateinit var search_edit_text:EditText
lateinit var loading_indicator: ProgressBar
lateinit var search_button:Button
lateinit var error_message:TextView
lateinit var mBooks: ArrayList<Book>
lateinit var mRecyclerView: RecyclerView
lateinit var mAdapter: RecyclerViewAdapter
lateinit var mRequestQueue: RequestQueue

private const val url = "https://www.googleapis.com/books/v1/volumes?q="


 class Dashboardmain : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_dashboard)

         bottomAppBar = findViewById(R.id.bottomappbar)
         bottomNavigationView = findViewById(R.id.bottomNavigationView)
         coordinatorLayout = findViewById(R.id.bottomNavigation)
         frameLayout = findViewById(R.id.frame)
         floatingActionButton = findViewById(R.id.fab)
         search_edit_text = findViewById(R.id.search_box)
         loading_indicator=findViewById(R.id.loading_indicator)
         search_button=findViewById(R.id.search_buttton)
         error_message=findViewById(R.id.message_display)
         mRequestQueue=Volley.newRequestQueue(this)
         mBooks= ArrayList()
         mRecyclerView=findViewById(R.id.recycler_view)
         mRecyclerView.setHasFixedSize(true)


         supportActionBar
         if (savedInstanceState == null) {
             supportFragmentManager.beginTransaction()
                 .replace(R.id.frame, DashboardFragment())
                 .commit()
         }
         search_button.setOnClickListener {
             mBooks.clear()
             search()
         }
         bottomNavigationView.setOnNavigationItemSelectedListener {
             when (it.itemId) {
                 R.id.dashboard -> {
                     supportFragmentManager.beginTransaction()
                         .replace(R.id.frame, DashboardFragment())
                         .commit()
                 }
                 R.id.favourite -> {
                     supportFragmentManager.beginTransaction()
                         .replace(R.id.frame, Favourites())
                         .commit()
                 }
                 R.id.read -> {
                     supportFragmentManager.beginTransaction()
                         .replace(R.id.frame, Read())
                         .commit()
                 }
                 R.id.recommendation -> {
                     supportFragmentManager.beginTransaction()
                         .replace(R.id.frame, Reviews())
                         .commit()
                 }

             }
             return@setOnNavigationItemSelectedListener true
         }
         floatingActionButton.setOnClickListener {
             val addPhotoBottomDialogFragment: RoundedBottomSheetDialogFragment = RoundedBottomSheetDialogFragment.newInstance()
             addPhotoBottomDialogFragment.show(supportFragmentManager,
                 "fragment_backdrop")
         }
     }

     override fun onNavigationItemSelected(item: MenuItem): Boolean {
         TODO("Not yet implemented")
     }

     private fun parseJson(key: String) {
         val request = JsonObjectRequest(
             Request.Method.GET, key, null,
             object : Response.Listener<JSONObject?> {
                 override fun onResponse(response: JSONObject?) {
                     var id=""
                     var title = ""
                     var author = ""
                     var publishedDate = "NoT Available"
                     var description = "No Description"
                     var pageCount = 1000
                     var categories = "No categories Available "
                     var buy = ""
                     var price = "NOT_FOR_SALE"
                     try {
                         val items = response!!.getJSONArray("items")
                         for (i in 0 until items.length()) {
                             val item = items.getJSONObject(i)
                             val volumeInfo = item.getJSONObject("volumeInfo")
                             try {
                                 title = volumeInfo.getString("title")
                                 val authors = volumeInfo.getJSONArray("authors")
                                 author = if (authors.length() == 1) {
                                     authors.getString(0)
                                 } else {
                                     authors.getString(0) + "|" + authors.getString(1)
                                 }
                                 publishedDate = volumeInfo.getString("publishedDate")
                                 pageCount = volumeInfo.getInt("pageCount")
                                 val saleInfo = item.getJSONObject("saleInfo")
                                 val listPrice = saleInfo.getJSONObject("listPrice")
                                 price =
                                     listPrice.getString("amount") + " " + listPrice.getString("currencyCode")
                                 description = volumeInfo.getString("description")
                                 buy = saleInfo.getString("buyLink")
                                 categories = volumeInfo.getJSONArray("categories").getString(0)
                             } catch (e: Exception) {
                             }
                             val thumbnail =
                                 volumeInfo.getJSONObject("imageLinks").getString("thumbnail")
                             val previewLink = volumeInfo.getString("previewLink")
                             val url = volumeInfo.getString("infoLink")
                             mBooks.add(
                                 Book(
                                     id,
                                     title,
                                     author,
                                     publishedDate,
                                     description,
                                     categories,
                                     thumbnail,
                                     buy,
                                     previewLink,
                                     price,
                                     pageCount,
                                     url
                                 )
                             )
                             mAdapter = mBooks.let { RecyclerViewAdapter(this@Dashboardmain, it) }
                             mRecyclerView.setAdapter(mAdapter)

                         }
                     } catch (e: JSONException) {
                         e.printStackTrace()
                         Log.e("TAG", e.toString())
                     }
                 }
             }, object : Response.ErrorListener {
                 override fun onErrorResponse(error: VolleyError) {
                     error.printStackTrace()
                 }
             })
         mRequestQueue.add(request)
     }


     private fun Read_network_state(context: Context): Boolean {
         val is_connected: Boolean
         val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
         val info = cm.activeNetworkInfo
         is_connected = info != null && info.isConnectedOrConnecting
         return is_connected
     }

     private fun search() {
         val search_query: String = search_edit_text.getText().toString()
         val is_connected: Boolean = Read_network_state(this)
         if (!is_connected) {
             error_message.setText(R.string.Failed_to_Load_data)
             mRecyclerView.setVisibility(View.INVISIBLE)
             error_message.setVisibility(View.VISIBLE)
             return
         }

         //  Log.d("QUERY",search_query);
         if (search_query == "") {
             Toast.makeText(this, "Please enter your query", Toast.LENGTH_SHORT).show()
             return
         }
         val final_query = search_query.replace(" ", "+")
         val uri: Uri = Uri.parse(url.toString() + final_query)
         val builder: Uri.Builder = uri.buildUpon()
         parseJson(builder.toString())
     }
 }

/*  override fun onOptionsItemSelected(item: MenuItem): Boolean {
          val id = item.itemId

          if (id == R.drawable.ic_baseline_favorite_border_24){
                 Toast.makeText(this@MainActivity, "clicked on favourites",Toast.LENGTH_SHORT).show()
          }
          return super.onOptionsItemSelected(item)
      } */



