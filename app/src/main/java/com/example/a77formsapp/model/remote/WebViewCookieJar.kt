package com.example.a77formsapp.data.remote

import android.webkit.CookieManager
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

// Stores cookies from API responses in the WebView's cookie storage and sends them back.
// Result: after logging in with OkHttp, the WebView is signed in automatically too.
class WebViewCookieJar : CookieJar {

    private val manager: CookieManager
        get() = CookieManager.getInstance()

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        if (cookies.isEmpty()) return
        cookies.forEach { manager.setCookie(url.toString(), it.toString()) }
        manager.flush()
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        val header = manager.getCookie(url.toString()) ?: return emptyList()
        return header.split(";").mapNotNull { Cookie.parse(url, it.trim()) }
    }
}