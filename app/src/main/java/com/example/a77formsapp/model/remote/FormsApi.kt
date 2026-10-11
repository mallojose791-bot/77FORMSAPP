package com.example.a77formsapp.data.remote

import com.example.a77formsapp.data.model.FormItem
import retrofit2.http.GET

interface FormsApi {
    // Expected response: [{ "id": "1", "title": "...", "description": "...", "url": "https://..." }]
    @GET("forms")
    suspend fun getForms(): List<FormItem>
}