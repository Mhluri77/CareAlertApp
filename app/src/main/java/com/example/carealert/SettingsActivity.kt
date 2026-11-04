package com.example.carealert

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import java.util.Locale

class SettingsActivity : AppCompatActivity() {

    override fun attachBaseContext(
        newBase: Context
    ) {
        super.attachBaseContext(
            LocaleHelper.setLocale(
                newBase
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val englishButton = findViewById<Button>(R.id.englishButton)
        val afrikaansButton = findViewById<Button>(R.id.afrikaansButton)
        val sepediButton = findViewById<Button>(R.id.sepediButton)
        val vendaButton = findViewById<Button>(R.id.vendaButton)
        val tsongaButton = findViewById<Button>(R.id.tsongaButton)

        val darkModeSwitch =
            findViewById<Switch>(R.id.darkModeSwitch)

        val prefs: SharedPreferences =
            getSharedPreferences(
                "CareAlertPrefs",
                MODE_PRIVATE
            )

        darkModeSwitch.isChecked =
            prefs.getBoolean(
                "dark_mode",
                false
            )

        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->

            prefs.edit()
                .putBoolean(
                    "dark_mode",
                    isChecked
                )
                .apply()

            if (isChecked) {

                AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES
                )

            } else {

                AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO
                )
            }

            recreate()
        }

        englishButton.setOnClickListener {
            saveLanguage("en")
        }

        afrikaansButton.setOnClickListener {
            saveLanguage("af")
        }

        sepediButton.setOnClickListener {
            saveLanguage("nso")
        }

        vendaButton.setOnClickListener {
            saveLanguage("ve")
        }

        tsongaButton.setOnClickListener {
            saveLanguage("ts")
        }
    }

    private fun saveLanguage(languageCode: String) {

        val prefs =
            getSharedPreferences(
                "CareAlertPrefs",
                MODE_PRIVATE
            )

        prefs.edit()
            .putString(
                "language",
                languageCode
            )
            .apply()

        changeLanguage(languageCode)
    }

    private fun changeLanguage(languageCode: String) {

        val locale = Locale(languageCode)

        Locale.setDefault(locale)

        val config =
            Configuration(resources.configuration)

        config.setLocale(locale)

        resources.updateConfiguration(
            config,
            resources.displayMetrics
        )

        Toast.makeText(
            this,
            "Language updated",
            Toast.LENGTH_SHORT
        ).show()

        recreate()
    }
}
