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

class SymptomActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_symptom)

        val symptomNameField =
            findViewById<EditText>(R.id.symptomNameField)

        val symptomDescriptionField =
            findViewById<EditText>(R.id.symptomDescriptionField)

        val saveButton =
            findViewById<Button>(R.id.saveSymptomButton)

        val viewButton =
            findViewById<Button>(R.id.viewSymptomsButton)

        val databaseHelper = DatabaseHelper(this)

        saveButton.setOnClickListener {

            val symptom =
                symptomNameField.text.toString().trim()

            val description =
                symptomDescriptionField.text.toString().trim()

            if (symptom.isEmpty()) {

                Toast.makeText(
                    this,
                    "Enter symptom name",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                val success =
                    databaseHelper.insertSymptom(
                        symptom,
                        description
                    )

                if (success) {

                    saveSymptomOnline(
                        symptom,
                        description
                    )

                    Toast.makeText(
                        this,
                        "Symptom saved offline and syncing online",
                        Toast.LENGTH_LONG
                    ).show()

                    symptomNameField.text.clear()
                    symptomDescriptionField.text.clear()

                } else {

                    Toast.makeText(
                        this,
                        "Failed to save symptom",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        viewButton.setOnClickListener {

            val symptoms =
                databaseHelper.getAllSymptoms()

            Toast.makeText(
                this,
                symptoms,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun saveSymptomOnline(
        symptom: String,
        description: String
    ) {

        val queue =
            Volley.newRequestQueue(this)

        val json = JSONObject()

        json.put(
            "user",
            JSONObject().put(
                "userId",
                1
            )
        )

        json.put(
            "symptomName",
            symptom
        )

        json.put(
            "description",
            description
        )

        val request =
            JsonObjectRequest(
                Request.Method.POST,
                "http://192.168.18.169:8080/api/carealert/symptoms",
                json,

                { },

                { }
            )

        queue.add(request)
    }
}