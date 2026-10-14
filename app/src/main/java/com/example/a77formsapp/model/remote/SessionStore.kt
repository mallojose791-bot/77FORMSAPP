package com.example.a77formsapp.data.remote

import android.webkit.CookieManager

// Reads and clears the session cookies. They live in the WebView's CookieManager,
// which is shared by the API calls (WebViewCookieJar) and the form WebView.
object SessionStore {

    fun cookieValue(name: String): String? {
        val header = CookieManager.getInstance().getCookie(HrfConfig.baseUrl) ?: return null
        return header.split(";")
            .map { it.trim() }
            .firstOrNull { it.startsWith("$name=") }
            ?.substringAfter("=")
    }

    fun hasSession(): Boolean = !cookieValue("HRFSESSION").isNullOrBlank()

    fun clear() {
        val cookies = CookieManager.getInstance()
        cookies.removeAllCookies(null)
        cookies.flush()
    }
}