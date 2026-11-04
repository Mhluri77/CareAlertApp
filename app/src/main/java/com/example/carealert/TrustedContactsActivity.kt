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

class TrustedContactsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trusted_contacts)

        val nameField =
            findViewById<EditText>(R.id.contactNameField)

        val phoneField =
            findViewById<EditText>(R.id.contactPhoneField)

        val saveButton =
            findViewById<Button>(R.id.saveContactButton)

        val viewButton =
            findViewById<Button>(R.id.viewContactsButton)

        val databaseHelper = DatabaseHelper(this)

        saveButton.setOnClickListener {

            val name =
                nameField.text.toString().trim()

            val phone =
                phoneField.text.toString().trim()

            if (
                name.isEmpty() ||
                phone.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Please enter both name and phone",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                val success =
                    databaseHelper.insertTrustedContact(
                        name,
                        phone
                    )

                if (success) {

                    saveContactOnline(
                        name,
                        phone
                    )

                    Toast.makeText(
                        this,
                        "Contact saved offline and syncing online",
                        Toast.LENGTH_LONG
                    ).show()

                    nameField.text.clear()
                    phoneField.text.clear()

                } else {

                    Toast.makeText(
                        this,
                        "Failed to save contact",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        viewButton.setOnClickListener {

            val contacts =
                databaseHelper.getAllContacts()

            Toast.makeText(
                this,
                contacts,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun saveContactOnline(
        name: String,
        phone: String
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
            "contactName",
            name
        )

        json.put(
            "contactPhone",
            phone
        )

        json.put(
            "contactEmail",
            ""
        )

        json.put(
            "alertPermission",
            true
        )

        val request =
            JsonObjectRequest(
                Request.Method.POST,
                "http://192.168.18.169:8080/api/carealert/contacts",
                json,

                { },

                { }
            )

        queue.add(request)
    }
}