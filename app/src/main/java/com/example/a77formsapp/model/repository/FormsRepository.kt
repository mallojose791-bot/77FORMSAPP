package com.example.a77formsapp.data.repository

import com.example.a77formsapp.data.model.FormItem
import com.example.a77formsapp.data.remote.ApiClient
import com.example.a77formsapp.data.remote.FormsApi
import com.example.a77formsapp.data.remote.SessionEvents
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import java.io.IOException

class FormsRepository(
    private val api: FormsApi = ApiClient.formsApi
) {
    suspend fun getForms(): Result<List<FormItem>> {
        return try {
            Result.success(api.getForms())
        } catch (e: CancellationException) {
            throw e
        } catch (e: HttpException) {
            val message = when (e.code()) {
                401 -> {
                    // Session is gone: the app will show the sign-in screen.
                    SessionEvents.notifyExpired()
                    "Session expired. Please sign in again."
                }
                403 -> "This account must change its password on the HRF website first."
                else -> "Server error (${e.code()})."
            }
            Result.failure(Exception(message))
        } catch (e: IOException) {
            Result.failure(Exception("Can't reach the server. Check your internet connection."))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}