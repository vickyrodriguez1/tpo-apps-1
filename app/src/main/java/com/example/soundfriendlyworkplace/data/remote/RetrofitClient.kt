
package com.example.soundfriendlyworkplace.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Configuración de la conexión con el backend.
object RetrofitClient {

    // IP del servidor de Ramiro. Puede cambiar si reinicia el hotspot.
    private const val BASE_URL = "http://10.195.123.24:8080/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}


