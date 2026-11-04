package com.example.carealert

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    override fun attachBaseContext(
        newBase: Context
    ) {
        super.attachBaseContext(
            LocaleHelper.setLocale(
                newBase
            )
        )
    }

    private val loginUrl =
        "http://192.168.18.169:8080/api/login"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val usernameField =
            findViewById<EditText>(R.id.usernameField)

        val passwordField =
            findViewById<EditText>(R.id.passwordField)

        val loginButton =
            findViewById<Button>(R.id.loginButton)


        val registerButton =
            findViewById<Button>(
                R.id.registerScreenButton
            )

        registerButton.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    RegisterActivity::class.java
                )
            )
        }

        loginButton.setOnClickListener {

            val email =
                usernameField.text.toString().trim()

            val password =
                passwordField.text.toString().trim()

            if (
                email.isEmpty() ||
                password.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Enter email and password",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                loginUser(email, password)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.GINGERBREAD)
    private fun loginUser(
        email: String,
        password: String
    ) {

        val queue =
            Volley.newRequestQueue(this)

        val jsonBody = JSONObject()

        jsonBody.put("email", email)
        jsonBody.put("password", password)

        val request = JsonObjectRequest(
            Request.Method.POST,
            loginUrl,
            jsonBody,

            { response ->

                val preferences =
                    getSharedPreferences(
                        "CareAlertPrefs",
                        MODE_PRIVATE
                    )

                preferences.edit()
                    .putBoolean("isLoggedIn", true)
                    .putString("username", email)
                    .putLong("userId", response.getLong("userId"))
                    .apply()

                Toast.makeText(
                    this,
                    "Login successful",
                    Toast.LENGTH_SHORT
                ).show()

                startActivity(
                    Intent(
                        this,
                        MainActivity::class.java
                    )
                )

                finish()
            },

            { error ->

                Toast.makeText(
                    this,
                    error.message ?: error.toString(),
                    Toast.LENGTH_LONG
                ).show()
            }

        )

        queue.add(request)
    }
}
