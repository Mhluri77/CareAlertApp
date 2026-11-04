package com.example.carealert

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        val preferences =
            getSharedPreferences(
                "CareAlertPrefs",
                MODE_PRIVATE
            )

        if (preferences.getBoolean("dark_mode", false)) {

            AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_YES
            )

        } else {

            AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val notificationButton =
            findViewById<Button>(R.id.notificationButton)

        val symptomButton =
            findViewById<Button>(R.id.symptomButton)

        val medicationButton =
            findViewById<Button>(R.id.medicationButton)

        val settingsButton =
            findViewById<Button>(R.id.settingsButton)

        val helpButton =
            findViewById<Button>(R.id.helpButton)

        val logoutButton =
            findViewById<Button>(R.id.logoutButton)

        val contactsButton =
            findViewById<Button>(R.id.contactsButton)

        val adherenceButton =
            findViewById<Button>(R.id.adherenceButton)

        val themeButton =
            findViewById<ImageButton>(R.id.themeButton)

        themeButton.setOnClickListener {

            val darkMode =
                preferences.getBoolean(
                    "dark_mode",
                    false
                )

            if (darkMode) {

                AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO
                )

                preferences.edit()
                    .putBoolean(
                        "dark_mode",
                        false
                    )
                    .apply()

            } else {

                AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES
                )

                preferences.edit()
                    .putBoolean(
                        "dark_mode",
                        true
                    )
                    .apply()
            }

            recreate()
        }

        notificationButton.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    NotificationActivity::class.java
                )
            )
        }

        symptomButton.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    SymptomActivity::class.java
                )
            )
        }

        medicationButton.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    MedicationActivity::class.java
                )
            )
        }

        settingsButton.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    SettingsActivity::class.java
                )
            )
        }

        helpButton.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    HelpActivity::class.java
                )
            )
        }

        contactsButton.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    TrustedContactsActivity::class.java
                )
            )
        }

        adherenceButton.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    AdherenceActivity::class.java
                )
            )
        }

        logoutButton.setOnClickListener {

            preferences.edit()
                .clear()
                .apply()

            startActivity(
                Intent(
                    this,
                    LoginActivity::class.java
                )
            )

            finish()
        }
    }
}
