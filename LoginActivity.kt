package com.example.settings

import android.R.id
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.settings.database.MyDBHelper


class LoginActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var textRegister2: TextView
    lateinit var btnLogin2: Button
    lateinit var etextemail: EditText
    lateinit var etextpass: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE)
        setContentView(R.layout.activity_login)
        val isloggedin = sharedPreferences.getBoolean("isloggedin", false)
        if (isloggedin) {
            val intent = Intent(this@LoginActivity, Dashboardmain::class.java)
            startActivity(intent)
            finish()
        }else{

        }
        textRegister2 = findViewById(R.id.textRegister2)
        btnLogin2 = findViewById(R.id.btnLogin2)
        etextemail = findViewById(R.id.etextemail)
        etextpass = findViewById(R.id.etextpass)
        var helper = MyDBHelper(applicationContext)
        var db = helper.readableDatabase


        textRegister2.setOnClickListener {
            val intent = Intent(this@LoginActivity, reg::class.java)
            startActivity(intent)
        }
        btnLogin2.setOnClickListener {
            var args = listOf<String>(etextemail.text.toString(), etextpass.text.toString()).toTypedArray()
            var rs = db.rawQuery("SELECT * FROM USERS WHERE EMAIL=? AND PWD=?", args)
            var cs = ContentValues()

            cs.put("LOG", 1)
            val intent = Intent(this@LoginActivity, Dashboardmain::class.java)
            if (rs.moveToNext()) {
                savepreferences()
                startActivity(intent)
                db.update("USERS", cs, "EMAIL=? AND PWD=?", args);
                Toast.makeText(applicationContext, "Welcome!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Invalid credentials!", Toast.LENGTH_SHORT).show()
                etextemail.requestFocus()
            }


    } }

    override fun onPause() {
        super.onPause()
        finish()
    }

    fun savepreferences() {
        sharedPreferences.edit().putBoolean("isloggedin", true).commit()
    }
}