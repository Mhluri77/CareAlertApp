package com.example.carealert

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MedicationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medication)

        val medicationNameField =
            findViewById<EditText>(R.id.medicationNameField)

        val dosageField =
            findViewById<EditText>(R.id.dosageField)

        val frequencyField =
            findViewById<EditText>(R.id.frequencyField)

        val reminderTimeField =
            findViewById<EditText>(R.id.reminderTimeField)

        val notesField =
            findViewById<EditText>(R.id.notesField)

        val saveButton =
            findViewById<Button>(R.id.saveMedicationButton)

        val viewButton =
            findViewById<Button>(R.id.viewMedicationsButton)

        val databaseHelper = DatabaseHelper(this)

        saveButton.setOnClickListener {

            val name =
                medicationNameField.text.toString().trim()

            val dosage =
                dosageField.text.toString().trim()

            val frequency =
                frequencyField.text.toString().trim()

            val reminderTime =
                reminderTimeField.text.toString().trim()

            val notes =
                notesField.text.toString().trim()

            val success =
                databaseHelper.insertMedication(
                    name,
                    dosage,
                    frequency,
                    reminderTime,
                    notes
                )

            if (success) {

                saveMedicationOnline(
                    name,
                    dosage,
                    frequency,
                    notes
                )

                Toast.makeText(
                    this,
                    "Medication saved offline and syncing online",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                Toast.makeText(
                    this,
                    "Failed to save medication",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        viewButton.setOnClickListener {

            val medications =
                databaseHelper.getAllMedications()

            Toast.makeText(
                this,
                medications,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun saveMedicationOnline(
        name: String,
        dosage: String,
        frequency: String,
        notes: String
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
            "medicationName",
            name
        )

        json.put(
            "dosage",
            dosage
        )

        json.put(
            "frequency",
            frequency
        )

        json.put(
            "notes",
            notes
        )

        val request =
            JsonObjectRequest(
                Request.Method.POST,
                "http://192.168.18.169:8080/api/carealert/medications",
                json,

                { },

                { }
            )

        queue.add(request)
    }
}