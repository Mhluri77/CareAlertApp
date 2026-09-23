package com.example.carealertapp

import com.squareup.moshi.Json

data class User(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "email") val email: String
)

data class Alert(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "message") val message: String,
    @Json(name = "timestamp") val timestamp: String
)

data class Medication(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "dosage") val dosage: String
)

data class Symptom(
    @Json(name = "id") val id: Int,
    @Json(name = "description") val description: String
)

data class Dose(
    @Json(name = "id") val id: Int,
    @Json(name = "amount") val amount: String,
    @Json(name = "time") val time: String
)
