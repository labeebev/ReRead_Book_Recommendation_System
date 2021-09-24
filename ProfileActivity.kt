package com.example.settings

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.database.Cursor.FIELD_TYPE_STRING
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView


import androidx.appcompat.app.AppCompatActivity
import com.example.settings.database.MyDBHelper
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class ProfileActivity : AppCompatActivity() {

    lateinit var imgbt_back_profile: ImageButton
    lateinit var imgbt_editpencil: ImageButton
    lateinit var textusername: TextView
    lateinit var txt_birthday: TextView
    lateinit var txt_gender: TextView
    lateinit var txt_aboutme: TextView
    private var binding: ProfileActivity? = null
    private var bottomSheetDialog: BottomSheetDialog? = null
    lateinit var fabcamera: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val helper = MyDBHelper(applicationContext)
        val db = helper.readableDatabase
        imgbt_back_profile = findViewById(R.id.imgbt_back_profile)
        imgbt_editpencil = findViewById(R.id.imgbt_editpencil)
        fabcamera = findViewById(R.id.fabcamera)
        txt_gender = findViewById(R.id.txt_gen)
        txt_aboutme = findViewById(R.id.txt_abtme)
        txt_birthday = findViewById(R.id.txt_bday)
        textusername = findViewById(R.id.textusername)
        var cursor = db.rawQuery("SELECT * FROM USERS WHERE LOG=1", null)

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val name: String = cursor.getString(cursor.getColumnIndex("UNAME"))
                    textusername.text = ("" + name)
                   /* val dob: String = cursor.getString(cursor.getColumnIndex("DOB"))*/
                    if (cursor.getType(cursor.getColumnIndex("DOB")) == FIELD_TYPE_STRING) {
                        val dob = cursor.getString(cursor.getColumnIndex("DOB"));
                        txt_birthday.text = ("" + dob)
                    }
                    if (cursor.getType(cursor.getColumnIndex("GENDER")) == FIELD_TYPE_STRING) {
                    val gen: String = cursor.getString(cursor.getColumnIndex("GENDER"))
                    txt_gender.text = ("" + gen)}
                    if (cursor.getType(cursor.getColumnIndex("ABOUT")) == FIELD_TYPE_STRING) {val abt: String = cursor?.getString(cursor.getColumnIndex("ABOUT"))
                    txt_aboutme.text = ("" + abt)}
                } while (cursor.moveToNext())
            }
        }

        imgbt_editpencil.setOnClickListener {
            val intent = Intent(this@ProfileActivity, EditProfileActivity::class.java)
            intent.putExtra("name", textusername.text.toString())
            intent.putExtra("dob", txt_birthday.text.toString())
            intent.putExtra("about", txt_aboutme.text.toString())
            intent.putExtra("gender", txt_gender.text.toString())
            startActivity(intent)

        }

        imgbt_back_profile.setOnClickListener {

            onBackPressed()
        }
        initActionClick()

    }

    private fun initActionClick() {
        fabcamera.setOnClickListener(View.OnClickListener { showBottomSheetPickPhoto() })
    }

    private fun showBottomSheetPickPhoto() {
        @SuppressLint("InflateParams")
        val view = layoutInflater.inflate(R.layout.bottom_sheet_pick, null)
        bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog!!.setContentView(view)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Objects.requireNonNull(bottomSheetDialog!!.window)
                    ?.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        bottomSheetDialog!!.setOnDismissListener { bottomSheetDialog = null }
        bottomSheetDialog!!.show()
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}