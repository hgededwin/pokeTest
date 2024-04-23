package com.ehernndez.poketest.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ehernndez.poketest.R
import com.ehernndez.poketest.data.persintetData.Data
import com.ehernndez.poketest.ui.home.HomeActivity
import com.ehernndez.poketest.utils.Utils
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
    lateinit var containerPassword: TextInputLayout
    lateinit var edtxtPassword: TextInputEditText
    lateinit var txtEmailuser: TextView
    lateinit var btnLogin: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        containerPassword = findViewById(R.id.container_edtxt_login)
        edtxtPassword = findViewById(R.id.edtxt_login)
        txtEmailuser = findViewById(R.id.txt_email_user)
        btnLogin = findViewById(R.id.btn_login)

        txtEmailuser.text = Data.shared.email

        edtxtPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (edtxtPassword.text!!.length == 8) {
                    containerPassword.error = null
                    Utils().enableButton(
                        this@LoginActivity, btnLogin,
                        isEnable = true,
                        isVariant = true
                    )
                } else {
                    containerPassword.error = "La contraseña esde 8 dígitos"
                }
            }
        })

        btnLogin.setOnClickListener {
            Utils().createIntent(this@LoginActivity, HomeActivity())
            finish()
        }
    }
}