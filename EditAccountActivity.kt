package com.example.settings

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.settings.database.MyDBHelper

class EditAccountActivity : AppCompatActivity() {

    lateinit var bt_saveaccount:Button
    lateinit var edit_password:EditText
    lateinit var reedit_password:EditText
    lateinit var edit_email:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_account)
        edit_email=findViewById(R.id.edit_email)
        edit_password=findViewById(R.id.edit_password)
        bt_saveaccount=findViewById(R.id.bt_saveaccount)
        reedit_password=findViewById(R.id.reedit_password)
        val helper = MyDBHelper(applicationContext)
        val db = helper.writableDatabase
        val cv= ContentValues()
        var str=intent.getStringExtra("email")
        edit_email.setText(str)
        bt_saveaccount.setOnClickListener {

            if((edit_password.text.toString() == reedit_password.text.toString())&& (edit_password.text.toString()!=""&& edit_password.text.toString().length > 5)){
                Toast.makeText(applicationContext,"Password match",Toast.LENGTH_SHORT).show()
            val intent = Intent(this@EditAccountActivity,AccountActivity::class.java)
                cv.put("EMAIL",edit_email.text.toString())
                cv.put("PWD",edit_password.text.toString())
                db.update("USERS",cv,"LOG=1",null)
            startActivity(intent)}
            else{
                Toast.makeText(applicationContext,"Password mismatch or invalid password length!",Toast.LENGTH_SHORT).show()
            }


        }
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}