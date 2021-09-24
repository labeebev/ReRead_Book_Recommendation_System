package com.example.settings

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.settings.database.MyDBHelper

class reg : AppCompatActivity() {
    lateinit var imgBtnLoginBack1: ImageButton
    lateinit var regbutton: Button
    lateinit var etextName: EditText
    lateinit var etextemail2: EditText
    lateinit var etextPhone: EditText
    lateinit var etextpassreg1: EditText
    lateinit var textlogin1: TextView
    lateinit var etextpassreg2: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg)
        val helper = MyDBHelper(applicationContext)
        val db = helper.writableDatabase
        etextName = findViewById(R.id.etextName)
        etextemail2 = findViewById(R.id.etextemail2)
        etextPhone = findViewById(R.id.etextPhone)
        etextpassreg1 = findViewById(R.id.etextpassreg1)
        regbutton = findViewById(R.id.regbutton)
        etextpassreg2 = findViewById(R.id.etextpassreg2)
        textlogin1 = findViewById(R.id.textlogin1)
        imgBtnLoginBack1 = findViewById(R.id.imgBtnLoginBack1)
        imgBtnLoginBack1.setOnClickListener {
            onBackPressed()
        }
        regbutton.setOnClickListener {
            val cv = ContentValues()
            cv.put("UNAME", etextName.text.toString())
            cv.put("EMAIL", etextemail2.text.toString())
            cv.put("PHONENO", etextPhone.text.toString())
            cv.put("PWD", etextpassreg1.text.toString())
            cv.put("LOG", 0)
            if (etextpassreg1.text.toString() == etextpassreg2.text.toString()) {
                        if (((etextName.text.toString().length > 0) && (etextPhone.text.toString().length > 0)) && (etextemail2.text.toString().length > 0)) {
                            if (etextpassreg1.text.toString().length>5) {
                                db.insert("USERS", null, cv)
                                Toast.makeText(applicationContext, "Registration Success!", Toast.LENGTH_SHORT).show()
                                etextName.setText("")
                                etextemail2.setText("")
                                etextPhone.setText("")
                                etextpassreg1.setText("")
                                etextpassreg2.setText("")
                                etextName.requestFocus()
                            } else {
                                Toast.makeText(applicationContext, "Password minimum length should be 6", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(applicationContext, "Minimum field length not reached", Toast.LENGTH_SHORT).show()
                        }
                    }
            else {
                Toast.makeText(applicationContext, "Password mismatch", Toast.LENGTH_LONG).show()
                etextpassreg2.requestFocus()
            }
        }


    textlogin1.setOnClickListener {
        val intent = Intent(this@reg, LoginActivity::class.java)
        startActivity(intent)

    }


}
}