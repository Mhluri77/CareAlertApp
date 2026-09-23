package com.example.carealertapp

import okhttp3.*
import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.IOException

class ApiService {
    private val client = OkHttpClient()
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private fun <T> getList(url: String, clazz: Class<T>, onResult: (List<T>?) -> Unit) {
        val request = Request.Builder().url(url).get().build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                onResult(null)
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let { json ->
                    val type = Types.newParameterizedType(List::class.java, clazz)
                    val adapter: JsonAdapter<List<T>> = moshi.adapter(type)
                    val result = adapter.fromJson(json)
                    onResult(result)
                }
            }
        })
    }

    fun getMedications(onResult: (List<Medication>?) -> Unit) =
        getList("http://10.0.2.2:8080/api/carealert/medications", Medication::class.java, onResult)

    fun getUsers(onResult: (List<User>?) -> Unit) =
        getList("http://10.0.2.2:8080/api/carealert/users", User::class.java, onResult)

    fun getSymptoms(onResult: (List<Symptom>?) -> Unit) =
        getList("http://10.0.2.2:8080/api/carealert/symptoms", Symptom::class.java, onResult)

    fun getNotifications(onResult: (List<Alert>?) -> Unit) =
        getList("http://10.0.2.2:8080/api/carealert/notifications", Alert::class.java, onResult)

    fun getDoses(onResult: (List<Dose>?) -> Unit) =
        getList("http://10.0.2.2:8080/api/carealert/doses", Dose::class.java, onResult)
}
