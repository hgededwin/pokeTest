package com.ehernndez.poketest.ui

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient

class TermsConditionsWVClient(var context: Context): WebViewClient() {
    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        Log.e("The page is starting to show", "--->")
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        Log.e("The page is finishing to show", "--->")
    }
}