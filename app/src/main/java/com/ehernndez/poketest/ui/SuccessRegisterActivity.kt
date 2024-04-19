package com.ehernndez.poketest.ui

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ehernndez.poketest.R
import com.ehernndez.poketest.utils.Utils

class SuccessRegisterActivity : AppCompatActivity() {
    lateinit var btnStart: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_register)

        btnStart = findViewById(R.id.btn_start)

        btnStart.setOnClickListener {
            Utils().createIntent(this@SuccessRegisterActivity, LoginActivity())
            finish()
        }
    }
}