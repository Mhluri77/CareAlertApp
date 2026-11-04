package com.example.carealert

import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        applySavedLanguage()

        super.onCreate(savedInstanceState)

        val preferences =
            getSharedPreferences(
                "CareAlertPrefs",
                MODE_PRIVATE
            )

        val isLoggedIn =
            preferences.getBoolean(
                "isLoggedIn",
                false
            )

        if (isLoggedIn) {

            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                )
            )

            finish()
            return
        }

        setContentView(R.layout.activity_welcome)

        val continueButton =
            findViewById<Button>(R.id.continueButton)

        continueButton.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    LoginActivity::class.java
                )
            )

            finish()
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun applySavedLanguage() {

        val prefs =
            getSharedPreferences(
                "CareAlertPrefs",
                MODE_PRIVATE
            )

        val language =
            prefs.getString(
                "language",
                "en"
            ) ?: "en"

        val locale = Locale(language)

        Locale.setDefault(locale)

        val config =
            Configuration(resources.configuration)

        config.setLocale(locale)

        resources.updateConfiguration(
            config,
            resources.displayMetrics
        )
    }
}
