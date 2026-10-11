package com.example.a77formsapp.data.repository

import com.example.a77formsapp.data.model.FormItem
import com.example.a77formsapp.data.remote.ApiClient
import com.example.a77formsapp.data.remote.FormsApi

class FormsRepository(
    private val api: FormsApi = ApiClient.formsApi
) {
    suspend fun getForms(): Result<List<FormItem>> =
        try {
            Result.success(api.getForms())
        } catch (e: Exception) {
            Result.failure(e)
        }
}