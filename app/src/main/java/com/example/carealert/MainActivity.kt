
package com.example.carealert

import android.content.Intent
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import android.widget.Button
import android.widget.ImageButton
import android.util.Log

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

import me.pushy.sdk.Pushy
import java.util.concurrent.Executors

import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "PUSHY_TOKEN"

        private const val PUSHY_URL =
            "http://192.168.18.169:8080/api/carealert/users"
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(
            LocaleHelper.setLocale(newBase)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load saved preferences
        val preferences = getSharedPreferences(
            "CareAlertPrefs",
            MODE_PRIVATE
        )

        // Apply saved theme
        if (preferences.getBoolean("dark_mode", false)) {
            AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_YES
            )
        } else {
            AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        setContentView(R.layout.activity_main)

        // Register Pushy device and save its token
        registerPushyDevice()

        // BUTTON INITIALIZATION

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

        // DARK MODE BUTTON

        themeButton.setOnClickListener {
            val darkMode = preferences.getBoolean(
                "dark_mode",
                false
            )

            if (darkMode) {
                AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO
                )

                preferences.edit()
                    .putBoolean("dark_mode", false)
                    .apply()

            } else {
                AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES
                )

                preferences.edit()
                    .putBoolean("dark_mode", true)
                    .apply()
            }

            recreate()
        }

        // NAVIGATION BUTTONS

        notificationButton.setOnClickListener {
            startActivity(
                Intent(this, NotificationActivity::class.java)
            )
        }

        symptomButton.setOnClickListener {
            startActivity(
                Intent(this, SymptomActivity::class.java)
            )
        }

        medicationButton.setOnClickListener {
            startActivity(
                Intent(this, MedicationActivity::class.java)
            )
        }

        settingsButton.setOnClickListener {
            startActivity(
                Intent(this, SettingsActivity::class.java)
            )
        }

        helpButton.setOnClickListener {
            startActivity(
                Intent(this, HelpActivity::class.java)
            )
        }

        contactsButton.setOnClickListener {
            startActivity(
                Intent(this, TrustedContactsActivity::class.java)
            )
        }

        adherenceButton.setOnClickListener {
            startActivity(
                Intent(this, AdherenceActivity::class.java)
            )
        }

        // LOGOUT BUTTON

        logoutButton.setOnClickListener {
            preferences.edit()
                .clear()
                .apply()

            startActivity(
                Intent(this, LoginActivity::class.java)
            )

            finish()
        }
    }

    // REGISTER PUSHY DEVICE

    private fun registerPushyDevice() {

        val preferences = getSharedPreferences(
            "CareAlertPrefs",
            MODE_PRIVATE
        )

        // Get the logged-in user's database ID
        val userId = preferences.getLong("userId", -1L)

        if (userId == -1L) {
            Log.e(TAG, "User ID not found in SharedPreferences")

            Toast.makeText(
                this,
                "Please log in again.",
                Toast.LENGTH_LONG
            ).show()

            return
        }

        val appContext = applicationContext

        val executor = Executors.newSingleThreadExecutor()

        executor.execute {
            try {
                // Retrieve existing token or register device
                val token = if (Pushy.isRegistered(appContext)) {
                    Pushy.getDeviceCredentials(appContext).token
                } else {
                    Pushy.register(appContext)
                }

                Log.d(
                    TAG,
                    "Pushy registration successful. Token length: ${token.length}"
                )

                runOnUiThread {
                    savePushyTokenToBackend(userId, token)
                }

            } catch (e: Exception) {
                Log.e(
                    "PUSHY_ERROR",
                    "Pushy registration failed",
                    e
                )

                runOnUiThread {
                    Toast.makeText(
                        this,
                        "Push registration failed: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } finally {
                executor.shutdown()
            }
        }
    }

    // SAVE PUSHY TOKEN IN SPRING BOOT BACKEND

    private fun savePushyTokenToBackend(
        userId: Long,
        token: String
    ) {

        val url = "$PUSHY_URL/$userId/pushy-token"

        val json = JSONObject()
        json.put("token", token)

        val request = JsonObjectRequest(
            Request.Method.POST,
            url,
            json,

            { response ->
                Log.d(
                    TAG,
                    "Pushy token saved: $response"
                )

                Toast.makeText(
                    this,
                    "Push notifications connected successfully!",
                    Toast.LENGTH_LONG
                ).show()
            },

            { error ->
                Log.e(
                    "PUSHY_BACKEND_ERROR",
                    "Could not save Pushy token. HTTP error: ${error.networkResponse?.statusCode}",
                    error
                )

                Toast.makeText(
                    this,
                    "Could not save notification token to server.",
                    Toast.LENGTH_LONG
                ).show()
            }
        )

        Volley.newRequestQueue(applicationContext).add(request)
    }
}
