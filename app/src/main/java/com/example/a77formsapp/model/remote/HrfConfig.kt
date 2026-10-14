package com.example.a77formsapp.data.remote

import android.net.Uri
import android.webkit.CookieManager

// Settings for the HRF dev server.
object HrfConfig {

    const val baseUrl = "https://d13i3lzlpfwhol.cloudfront.net"

    fun formUrl(hrFormCode: String): String =
        "$baseUrl/questions/answer/${Uri.encode(hrFormCode)}"

    // The session cookie is already in the WebView's cookie storage (WebViewCookieJar puts it
    // there at login), so the form opens signed in. This just makes sure cookies are accepted.
    fun applySessionCookies() {
        CookieManager.getInstance().setAcceptCookie(true)
    }
}