package com.example.a77formsapp.data.repository

import com.example.a77formsapp.data.model.LoginRequest
import com.example.a77formsapp.data.model.LoginResponse
import com.example.a77formsapp.data.remote.ApiClient
import com.example.a77formsapp.data.remote.AuthApi
import com.example.a77formsapp.data.remote.SessionStore
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import java.io.IOException

class AuthRepository(
    private val api: AuthApi = ApiClient.authApi
) {
    suspend fun login(email: String, password: String): Result<LoginResponse> {
        return try {
            SessionStore.clear()                  // start from a clean state
            api.openLoginPage().close()           // gets the XSRF-TOKEN cookie

            val xsrf = SessionStore.cookieValue("XSRF-TOKEN")
                ?: return Result.failure(Exception("Couldn't get a security token from the server. Try again."))

            val response = api.login(xsrf, LoginRequest(email, password))
            if (response.mustChangePassword) {
                SessionStore.clear()
                Result.failure(Exception("This account must change its password on the HRF website first."))
            } else {
                Result.success(response)
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: HttpException) {
            Result.failure(Exception(loginErrorMessage(e)))
        } catch (e: IOException) {
            Result.failure(Exception("Can't reach the server. Check your internet connection."))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun loginErrorMessage(e: HttpException): String {
        // Error bodies look like {"error": "Invalid email or password."}
        val serverMessage = runCatching {
            val body = e.response()?.errorBody()?.string().orEmpty()
            Regex("\"error\"\\s*:\\s*\"([^\"]*)\"").find(body)?.groupValues?.get(1)
        }.getOrNull()

        return when (e.code()) {
            401 -> serverMessage ?: "Invalid email or password."
            403 -> "Security check failed. Please try again."
            429 -> serverMessage ?: "Too many login attempts. Try again in 5 minutes."
            else -> serverMessage ?: "Server error (${e.code()})."
        }
    }
}