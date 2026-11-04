package com.example.carealert

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class HelpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        Toast.makeText(this, "Help screen opened", Toast.LENGTH_SHORT).show()
    }
}
