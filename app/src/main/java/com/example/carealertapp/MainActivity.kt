package com.example.carealertapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val apiService = ApiService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.textView)

        val btnMedications = findViewById<Button>(R.id.btnMedications)
        val btnUsers = findViewById<Button>(R.id.btnUsers)
        val btnSymptoms = findViewById<Button>(R.id.btnSymptoms)
        val btnNotifications = findViewById<Button>(R.id.btnNotifications)
        val btnDoses = findViewById<Button>(R.id.btnDoses)

        btnMedications.setOnClickListener {
            apiService.getMedications { meds: List<Medication>? ->
                runOnUiThread {
                    textView.text = meds?.joinToString("\n") { "${it.name} - ${it.dosage}" }
                        ?: "Failed to fetch medications"
                }
            }
        }

        btnUsers.setOnClickListener {
            apiService.getUsers { users: List<User>? ->
                runOnUiThread {
                    textView.text = users?.joinToString("\n") { "${it.name} (${it.email})" }
                        ?: "Failed to fetch users"
                }
            }
        }

        btnSymptoms.setOnClickListener {
            apiService.getSymptoms { symptoms: List<Symptom>? ->
                runOnUiThread {
                    textView.text = symptoms?.joinToString("\n") { it.description }
                        ?: "Failed to fetch symptoms"
                }
            }
        }

        btnNotifications.setOnClickListener {
            apiService.getNotifications { alerts: List<Alert>? ->
                runOnUiThread {
                    textView.text = alerts?.joinToString("\n") { it.message }
                        ?: "Failed to fetch notifications"
                }
            }
        }

        btnDoses.setOnClickListener {
            apiService.getDoses { doses: List<Dose>? ->
                runOnUiThread {
                    textView.text = doses?.joinToString("\n") { "${it.amount} at ${it.time}" }
                        ?: "Failed to fetch doses"
                }
            }
        }
    }
}
