package com.example.a77formsapp.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private val okHttp = OkHttpClient.Builder()
        // Keeps cookies (XSRF-TOKEN, HRFSESSION, AWSALB) in the WebView's cookie storage.
        .cookieJar(WebViewCookieJar())
        // BASIC logs only the URL and status code, never headers or bodies.
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("${HrfConfig.baseUrl}/")
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val formsApi: FormsApi by lazy { retrofit.create(FormsApi::class.java) }
    val authApi: AuthApi by lazy { retrofit.create(AuthApi::class.java) }
}