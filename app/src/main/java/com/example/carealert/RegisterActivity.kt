package com.example.carealert

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RegisterActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(
            LocaleHelper.setLocale(newBase)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)

        val fullNameField =
            findViewById<EditText>(R.id.fullNameField)

        val emailField =
            findViewById<EditText>(R.id.emailField)

        val passwordField =
            findViewById<EditText>(R.id.passwordField)

        val registerButton =
            findViewById<Button>(R.id.registerButton)

        registerButton.setOnClickListener {

            val fullName =
                fullNameField.text.toString().trim()

            val email =
                emailField.text.toString().trim()

            val password =
                passwordField.text.toString().trim()

            if (
                fullName.isEmpty()
                || email.isEmpty()
                || password.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Fill all fields",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            registerUser(
                fullName,
                email,
                password
            )
        }
    }

    private fun registerUser(
        fullName: String,
        email: String,
        password: String
    ) {

        val date =
            SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.getDefault()
            ).format(Date())

        val json = JSONObject()

        json.put(
            "fullName",
            fullName
        )

        json.put(
            "email",
            email
        )

        json.put(
            "passwordHash",
            password
        )

        json.put(
            "profilePhoto",
            ""
        )

        json.put(
            "registrationDate",
            date
        )

        val request =
            JsonObjectRequest(
                Request.Method.POST,
                "http://192.168.18.169:8080/api/carealert/users",
                json,

                {
                    Toast.makeText(
                        this,
                        "Account created successfully",
                        Toast.LENGTH_LONG
                    ).show()

                    finish()
                },

                {
                    Toast.makeText(
                        this,
                        "Registration failed",
                        Toast.LENGTH_LONG
                    ).show()
                }
            )

        Volley.newRequestQueue(this)
            .add(request)
    }
}