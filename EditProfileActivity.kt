package com.example.settings

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import com.example.settings.database.MyDBHelper

class EditProfileActivity : AppCompatActivity() {
    lateinit var bt_saveprofile: Button
    lateinit var edit_username:EditText
    lateinit var edit_birthday:EditText
    lateinit var radiobt_male:RadioButton
    lateinit var radiobt_female:RadioButton
    lateinit var radiogrp:RadioGroup
    lateinit var edit_abtme:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        edit_abtme=findViewById(R.id.edit_abtme)
        radiobt_female=findViewById(R.id.radiobt_female)
        radiobt_male=findViewById(R.id.radiobt_male)
        radiogrp=findViewById(R.id.radiogrp)
        edit_birthday=findViewById(R.id.edit_birthday)
        edit_username=findViewById(R.id.edit_username)

        val helper = MyDBHelper(applicationContext)
        val db = helper.writableDatabase
        val cv=ContentValues()
        var str=intent.getStringExtra("name")
        edit_username.setText(str)
        var str1=intent.getStringExtra("dob")
        edit_birthday.setText(str1)
        var str2=intent.getStringExtra("about")
        edit_abtme.setText(str2)
        var str3=intent.getStringExtra("gender")
        if(str3=="M"){
            radiogrp.check(R.id.radiobt_male)
        }else if(str3=="F"){
            radiogrp.check(R.id.radiobt_female)
        }else{
            radiogrp.clearCheck()
        }

        bt_saveprofile=findViewById(R.id.bt_saveprofile)
        bt_saveprofile.setOnClickListener {
            val intent = Intent(this@EditProfileActivity,ProfileActivity::class.java)
            cv.put("UNAME",edit_username.text.toString())
            cv.put("DOB",edit_birthday.text.toString())
            cv.put("ABOUT",edit_abtme.text.toString())
            db.update("USERS", cv, "LOG=1",null);
            startActivity(intent)

        }
    }

    fun onGenderButtonClicked(view: View) {
        if (view is RadioButton){
            val helper = MyDBHelper(applicationContext)
            val db = helper.writableDatabase
            val cs=ContentValues()
            val checked =view.isChecked

            when (view.getId()) {
                R.id.radiobt_male ->
                    if (checked){
                        cs.put("GENDER","M")
                        db.update("USERS",cs,"LOG=1",null)
                    }


                R.id.radiobt_female ->
                    if (checked) {
                        cs.put("GENDER","F")
                        db.update("USERS",cs,"LOG=1",null)

                    }

            }
        }


    }
    override fun onPause() {
        super.onPause()
        finish()
    }
}