package com.example.settings

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.settings.adapters.ReviewAdapter
import com.example.settings.database.MyDBHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
lateinit var fabreview: FloatingActionButton
lateinit var review: LinearLayout

/**
 * A simple [Fragment] subclass.
 * Use the [Reviews.newInstance] factory method to
 * create an instance of this fragment.
 */
class Reviews : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    companion object{
        lateinit var dbHelper: MyDBHelper
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)



        }

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_reviews, container, false)
        fabreview = v.findViewById(R.id.fabreview)
        review = v.findViewById(R.id.review)
        dbHelper= MyDBHelper(v.context)
        fabreview.setOnClickListener {
            val intent = Intent (getActivity(), WriteReview::class.java)
            getActivity()?.startActivity(intent)
        }
        viewReviews(v)

        return v

    }

    @SuppressLint("WrongConstant")
    private fun viewReviews(view: View){
        val reviewslist= dbHelper.getReviews(view.context)
        val adapter= ReviewAdapter(view.context,reviewslist)
        val rv : RecyclerView =view.findViewById(R.id.rv)
        rv.layoutManager=LinearLayoutManager(view.context,LinearLayout.VERTICAL,false)
        rv.adapter=adapter
    }

    override fun onResume() {
        view?.let { viewReviews(it) }
        super.onResume()

    }
    /*companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Reviews.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                Reviews().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }*/


}