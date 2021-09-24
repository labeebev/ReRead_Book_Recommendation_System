package com.example.settings

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.settings.database.MyDBHelper


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lateinit var btnLogin : Button
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val intent = Intent(this@MainActivity,LoginActivity::class.java)
            startActivity(intent)
        }

        }
    override fun onPause() {
        super.onPause()
        finish()
    }
    }
