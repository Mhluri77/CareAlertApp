package com.example.carealert

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AdherenceActivity : AppCompatActivity() {

    override fun attachBaseContext(
        newBase: Context
    ) {
        super.attachBaseContext(
            LocaleHelper.setLocale(
                newBase
            )
        )
    }

    private var adherenceScore = 85

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adherence)

        val databaseHelper = DatabaseHelper(this)

        val scoreView =
            findViewById<TextView>(R.id.adherenceScore)

        val recalcButton =
            findViewById<Button>(R.id.recalculateScoreButton)

        val savedScore =
            databaseHelper.getLatestAdherenceScore()

        if (savedScore > 0) {
            adherenceScore = savedScore
        }

        scoreView.text =
            "Your adherence score: $adherenceScore%"

        recalcButton.setOnClickListener {

            adherenceScore =
                (adherenceScore + 5)
                    .coerceAtMost(100)

            databaseHelper.saveAdherenceScore(
                adherenceScore
            )

            scoreView.text =
                "Your adherence score: $adherenceScore%"

            Toast.makeText(
                this,
                "Score saved offline",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
