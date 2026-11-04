
package com.example.carealert

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Locale

class MedicationActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MEDICATION_SYNC"
        private const val MEDICATION_URL =
            "http://192.168.18.169:8080/api/carealert/medications"
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocaleHelper.setLocale(newBase))
    }

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

            val name = medicationNameField.text.toString().trim()
            val dosage = dosageField.text.toString().trim()
            val frequency = frequencyField.text.toString().trim()
            val reminderTime = reminderTimeField.text.toString().trim()
            val notes = notesField.text.toString().trim()

            if (name.isEmpty() || dosage.isEmpty() ||
                frequency.isEmpty() || reminderTime.isEmpty()
            ) {
                Toast.makeText(
                    this,
                    "Please complete all required fields",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val formattedTime = formatReminderTime(reminderTime)

            if (formattedTime == null) {
                Toast.makeText(
                    this,
                    "Please enter a valid time, e.g. 08:30 or 08:30 AM",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            val success = databaseHelper.insertMedication(
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
                    formattedTime,
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

            val medications = databaseHelper.getAllMedications()

            Toast.makeText(
                this,
                medications,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun formatReminderTime(time: String): String? {

        val inputFormats = listOf(
            "HH:mm",
            "H:mm",
            "hh:mm a",
            "h:mm a",
            "HH:mm:ss"
        )

        for (format in inputFormats) {
            try {
                val parser = SimpleDateFormat(format, Locale.US)
                parser.isLenient = false

                val parsedTime = parser.parse(time)

                if (parsedTime != null) {
                    val outputFormat =
                        SimpleDateFormat("HH:mm:ss", Locale.US)

                    return outputFormat.format(parsedTime)
                }

            } catch (e: Exception) {
                // Try the next supported time format.
            }
        }

        return null
    }

    private fun saveMedicationOnline(
        name: String,
        dosage: String,
        frequency: String,
        reminderTime: String,
        notes: String
    ) {

        val preferences =
            getSharedPreferences("CareAlertPrefs", MODE_PRIVATE)

        val userId = preferences.getLong("userId", -1L)

        if (userId == -1L) {
            Toast.makeText(
                this,
                "Please log in again before syncing medication",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        val json = JSONObject()

        json.put(
            "user",
            JSONObject().put("userId", userId)
        )

        json.put("medicationName", name)
        json.put("dosage", dosage)
        json.put("frequency", frequency)
        json.put("reminderTime", reminderTime)
        json.put("notes", notes)

// Add the medication start date
        val today = java.text.SimpleDateFormat(
            "yyyy-MM-dd",
            java.util.Locale.US
        ).format(java.util.Date())

        json.put("startDate", today)

        val request = JsonObjectRequest(
            Request.Method.POST,
            MEDICATION_URL,
            json,

            { response ->
                Log.d(TAG, "Medication synced: $response")

                Toast.makeText(
                    this,
                    "Medication successfully synced with server!",
                    Toast.LENGTH_SHORT
                ).show()
            },

            { error ->
                Log.e(TAG, "Medication sync failed", error)

                Toast.makeText(
                    this,
                    "Saved offline, but online sync failed",
                    Toast.LENGTH_LONG
                ).show()
            }
        )

        Volley.newRequestQueue(applicationContext).add(request)
    }
}
