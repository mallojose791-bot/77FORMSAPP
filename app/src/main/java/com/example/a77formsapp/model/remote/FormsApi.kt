package com.example.a77formsapp.data.remote

import com.example.a77formsapp.data.model.FormItem
import retrofit2.http.GET
import retrofit2.http.Headers

interface FormsApi {
    // Returns a plain JSON array: [{ "id": 3, "hrFormCode": "...", "description": "...", "createdAt": "..." }]
    @Headers("Accept: application/json")
    @GET("api/hr-forms")
    suspend fun getForms(): List<FormItem>
}