package com.example.settings

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.settings.database.MyDBHelper

class AccountActivity : AppCompatActivity() {
    lateinit var imgbt_back_account: ImageButton
    lateinit var imgbt_editpencil: ImageButton
    lateinit var txt_emailhint:TextView
    lateinit var txt_passwordhint:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        var helper= MyDBHelper(applicationContext)
        var db=helper.readableDatabase
        txt_emailhint=findViewById(R.id.txt_emailhint)
        txt_passwordhint=findViewById(R.id.txt_passwordhint)
        var emaill=db.rawQuery("SELECT EMAIL FROM USERS WHERE LOG=1", null)


        if (emaill != null) {
            if (emaill.moveToFirst()) {
                do {
                    val dir: String = emaill.getString(emaill.getColumnIndex("EMAIL"))
                    txt_emailhint.text=("" + dir)
                } while (emaill.moveToNext())
            }
        }
        imgbt_back_account=findViewById(R.id.imgbt_back_account)
        imgbt_editpencil=findViewById(R.id.imgbt_editpencil)
        imgbt_editpencil.setOnClickListener {
            val intent = Intent(this@AccountActivity, EditAccountActivity::class.java)
            intent.putExtra("email",txt_emailhint.text.toString())
            startActivity(intent)
        }

        imgbt_back_account.setOnClickListener {
            onBackPressed()
        }

    }
    override fun onPause() {
        super.onPause()
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}