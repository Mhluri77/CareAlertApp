package com.example.carealert

import android.os.Bundle
import android.content.Context
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class NotificationActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_notification)

        val titleField =
            findViewById<EditText>(R.id.notificationTitleField)

        val messageField =
            findViewById<EditText>(R.id.notificationMessageField)

        val saveButton =
            findViewById<Button>(R.id.saveNotificationButton)

        val viewButton =
            findViewById<Button>(R.id.viewNotificationsButton)

        val databaseHelper = DatabaseHelper(this)

        saveButton.setOnClickListener {

            val title =
                titleField.text.toString().trim()

            val message =
                messageField.text.toString().trim()

            if (title.isEmpty()) {

                Toast.makeText(
                    this,
                    "Enter notification title",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                val success =
                    databaseHelper.insertNotification(
                        title,
                        message
                    )

                if (success) {

                    saveNotificationOnline(
                        title,
                        message
                    )

                    Toast.makeText(
                        this,
                        "Notification saved offline and syncing online",
                        Toast.LENGTH_LONG
                    ).show()

                    titleField.text.clear()
                    messageField.text.clear()

                } else {

                    Toast.makeText(
                        this,
                        "Failed to save notification",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        viewButton.setOnClickListener {

            val notifications =
                databaseHelper.getAllNotifications()

            Toast.makeText(
                this,
                notifications,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun saveNotificationOnline(
        title: String,
        message: String
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
            "type",
            "Reminder"
        )

        json.put(
            "title",
            title
        )

        json.put(
            "message",
            message
        )

        json.put(
            "readStatus",
            false
        )

        val request =
            JsonObjectRequest(
                Request.Method.POST,
                "http://192.168.18.169:8080/api/carealert/notifications",
                json,

                { },

                { }
            )

        queue.add(request)
    }
}