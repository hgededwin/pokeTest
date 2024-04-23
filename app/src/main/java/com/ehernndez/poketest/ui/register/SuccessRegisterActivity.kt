package com.ehernndez.poketest.ui.register

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.ehernndez.poketest.R
import com.ehernndez.poketest.ui.LoginActivity
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