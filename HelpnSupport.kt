package com.example.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class HelpnSupport : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_helpn_support)
    }

    override fun onPause() {
        super.onPause()
        onBackPressed()
    }
}