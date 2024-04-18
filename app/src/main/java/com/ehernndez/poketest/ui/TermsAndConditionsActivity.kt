package com.ehernndez.poketest.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.ehernndez.poketest.R

class TermsAndConditionsActivity : AppCompatActivity() {
    lateinit var webViewTerms: WebView
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_and_conditions)

        webViewTerms = findViewById(R.id.web_view_terms)

        val url = this.resources.getString(R.string.txt_url)
        webViewTerms.webViewClient = TermsConditionsWVClient(this)
        webViewTerms.settings.javaScriptEnabled = true
        webViewTerms.loadUrl(url)
    }
}