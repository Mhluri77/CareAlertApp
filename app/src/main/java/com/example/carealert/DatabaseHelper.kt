package com.example.carealert

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "carealert.db", null, 6) {

    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL("""
            CREATE TABLE medications(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                medication_name TEXT,
                dosage TEXT,
                frequency TEXT,
                reminder_time TEXT,
                notes TEXT
            )
        """.trimIndent())

        db.execSQL("""
            CREATE TABLE trusted_contacts(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                contact_name TEXT,
                contact_phone TEXT
            )
        """.trimIndent())

        db.execSQL("""
            CREATE TABLE symptoms(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                symptom_name TEXT,
                description TEXT
            )
        """.trimIndent())

        db.execSQL("""
            CREATE TABLE notifications(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT,
                message TEXT
            )
        """.trimIndent())

        db.execSQL("""
            CREATE TABLE user_settings(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                language TEXT
            )
        """.trimIndent())

        db.execSQL("""
            CREATE TABLE adherence_scores(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                score INTEGER
            )
        """.trimIndent())
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS medications")
        db.execSQL("DROP TABLE IF EXISTS trusted_contacts")
        db.execSQL("DROP TABLE IF EXISTS symptoms")
        db.execSQL("DROP TABLE IF EXISTS notifications")
        db.execSQL("DROP TABLE IF EXISTS user_settings")
        db.execSQL("DROP TABLE IF EXISTS adherence_scores")
        onCreate(db)
    }

    // ==========================
    // MEDICATIONS
    // ==========================

    fun insertMedication(
        medicationName: String,
        dosage: String,
        frequency: String,
        reminderTime: String,
        notes: String
    ): Boolean {

        val db = writableDatabase

        val values = ContentValues().apply {
            put("medication_name", medicationName)
            put("dosage", dosage)
            put("frequency", frequency)
            put("reminder_time", reminderTime)
            put("notes", notes)
        }

        return db.insert(
            "medications",
            null,
            values
        ) != -1L
    }

    fun getAllMedications(): String {

        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM medications",
            null
        )

        val medications = StringBuilder()

        while (cursor.moveToNext()) {

            medications.append(
                "Name: ${
                    cursor.getString(
                        cursor.getColumnIndexOrThrow("medication_name")
                    )
                }\n"
            )

            medications.append(
                "Dosage: ${
                    cursor.getString(
                        cursor.getColumnIndexOrThrow("dosage")
                    )
                }\n\n"
            )
        }

        cursor.close()

        return medications.toString()
    }

    // ==========================
    // TRUSTED CONTACTS
    // ==========================

    fun insertTrustedContact(
        name: String,
        phone: String
    ): Boolean {

        val db = writableDatabase

        val values = ContentValues().apply {
            put("contact_name", name)
            put("contact_phone", phone)
        }

        return db.insert(
            "trusted_contacts",
            null,
            values
        ) != -1L
    }

    fun getAllContacts(): String {

        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM trusted_contacts",
            null
        )

        val contacts = StringBuilder()

        while (cursor.moveToNext()) {

            contacts.append(
                "Name: ${
                    cursor.getString(
                        cursor.getColumnIndexOrThrow("contact_name")
                    )
                }\n"
            )

            contacts.append(
                "Phone: ${
                    cursor.getString(
                        cursor.getColumnIndexOrThrow("contact_phone")
                    )
                }\n\n"
            )
        }

        cursor.close()

        return contacts.toString()
    }

    // ==========================
    // SYMPTOMS
    // ==========================

    fun insertSymptom(
        symptom: String,
        description: String
    ): Boolean {

        val values = ContentValues().apply {
            put("symptom_name", symptom)
            put("description", description)
        }

        return writableDatabase.insert(
            "symptoms",
            null,
            values
        ) != -1L
    }

    fun getAllSymptoms(): String {

        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM symptoms",
            null
        )

        val symptoms = StringBuilder()

        while (cursor.moveToNext()) {

            symptoms.append(
                "Symptom: ${
                    cursor.getString(
                        cursor.getColumnIndexOrThrow("symptom_name")
                    )
                }\n"
            )

            symptoms.append(
                "Description: ${
                    cursor.getString(
                        cursor.getColumnIndexOrThrow("description")
                    )
                }\n\n"
            )
        }

        cursor.close()

        return symptoms.toString()
    }

    // ==========================
    // NOTIFICATIONS
    // ==========================

    fun insertNotification(
        title: String,
        message: String
    ): Boolean {

        val values = ContentValues().apply {
            put("title", title)
            put("message", message)
        }

        return writableDatabase.insert(
            "notifications",
            null,
            values
        ) != -1L
    }

    fun getAllNotifications(): String {

        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM notifications",
            null
        )

        val notifications = StringBuilder()

        while (cursor.moveToNext()) {

            notifications.append(
                "Title: ${
                    cursor.getString(
                        cursor.getColumnIndexOrThrow("title")
                    )
                }\n"
            )

            notifications.append(
                "Message: ${
                    cursor.getString(
                        cursor.getColumnIndexOrThrow("message")
                    )
                }\n\n"
            )
        }

        cursor.close()

        return notifications.toString()
    }

    // ==========================
    // SETTINGS
    // ==========================

    fun saveLanguage(
        language: String
    ): Boolean {

        val values = ContentValues().apply {
            put("language", language)
        }

        return writableDatabase.insert(
            "user_settings",
            null,
            values
        ) != -1L
    }

    // ==========================
    // ADHERENCE SCORE
    // ==========================

    fun saveAdherenceScore(
        score: Int
    ): Boolean {

        val values = ContentValues().apply {
            put("score", score)
        }

        return writableDatabase.insert(
            "adherence_scores",
            null,
            values
        ) != -1L
    }

    fun getLatestAdherenceScore(): Int {

        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT score FROM adherence_scores ORDER BY id DESC LIMIT 1",
            null
        )

        var score = 0

        if (cursor.moveToFirst()) {

            score = cursor.getInt(
                cursor.getColumnIndexOrThrow("score")
            )
        }

        cursor.close()

        return score
    }

}
