package com.example.a77formsapp.data.remote

import com.example.a77formsapp.data.model.LoginRequest
import com.example.a77formsapp.data.model.LoginResponse
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApi {

    // Step 1: loading /login makes the server set the XSRF-TOKEN cookie (the response is HTML, we ignore it).
    @GET("login")
    suspend fun openLoginPage(): ResponseBody

    // Step 2: log in. The session comes back in the HRFSESSION cookie.
    @Headers("Accept: application/json")
    @POST("api/auth/login")
    suspend fun login(
        @Header("X-XSRF-TOKEN") xsrfToken: String,
        @Body body: LoginRequest
    ): LoginResponse
}