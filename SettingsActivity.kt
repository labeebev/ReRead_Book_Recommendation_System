package com.example.settings

import android.content.ContentValues
import android.content.Context
import   android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.example.settings.database.MyDBHelper

class SettingsActivity : AppCompatActivity() {
    lateinit var bt_account: Button
    lateinit var bt_profile: Button
    lateinit var bt_helpandsupport: Button
    lateinit var bt_about: Button
    lateinit var bt_signout: Button
    lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE)
        setContentView(R.layout.activity_settings)
        var helper= MyDBHelper(applicationContext)
        var db=helper.readableDatabase
        var cs= ContentValues()
        cs.put("LOG",0)
        bt_account=findViewById(R.id.bt_account)
        bt_profile=findViewById(R.id.bt_profile)
        bt_helpandsupport=findViewById(R.id.bt_helpandsupport)
        bt_about=findViewById(R.id.bt_about)
        bt_signout=findViewById(R.id.bt_signout)
        bt_signout.setOnClickListener {
            val intent=Intent(this@SettingsActivity,LoginActivity::class.java)
            db.update("USERS", cs, "LOG=1",null);
            newpreferences()
            startActivity(intent)
        }


        bt_account.setOnClickListener {
            val intent = Intent(this@SettingsActivity,AccountActivity::class.java)
            startActivity(intent)
        }
        bt_profile.setOnClickListener {
            val intent = Intent(this@SettingsActivity,ProfileActivity::class.java)
            startActivity(intent)

        }
        bt_about.setOnClickListener {
            val intent = Intent(this@SettingsActivity,About::class.java)
            startActivity(intent)
        }
        bt_helpandsupport.setOnClickListener {
            val intent = Intent(this@SettingsActivity,HelpnSupport::class.java)
            startActivity(intent)
        }

    }
    fun newpreferences() {
        sharedPreferences.edit().putBoolean("isloggedin", false).commit()
    }
}

