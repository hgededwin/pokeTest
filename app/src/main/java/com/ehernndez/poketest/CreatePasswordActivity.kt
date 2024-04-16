package com.ehernndez.poketest

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.ehernndez.poketest.utils.Utils
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.Locale

class CreatePasswordActivity : AppCompatActivity() {
    lateinit var imgEightCharacters: ImageView
    lateinit var imgUpperCase: ImageView
    lateinit var imgLowerCase: ImageView
    lateinit var imgSpecialCharacter: ImageView
    lateinit var imgANumber: ImageView
    lateinit var containerPassword: TextInputLayout
    lateinit var edtxtPassword: TextInputEditText
    lateinit var containerConfirmPassword: TextInputLayout
    lateinit var edtxtConfirmPassword: TextInputEditText
    lateinit var btnNext: Button

    val hasEightCharacters = 8
    var hasUppercase = false
    var hasLowercase = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_password)

        imgEightCharacters = findViewById(R.id.img_eight_characters)
        imgUpperCase = findViewById(R.id.img_uppercase)
        imgLowerCase = findViewById(R.id.img_lowercase)
        imgSpecialCharacter = findViewById(R.id.img_special_character)
        imgANumber = findViewById(R.id.img_number)
        containerPassword = findViewById(R.id.container_edtxt_password)
        edtxtPassword = findViewById(R.id.edtxt_password)
        containerConfirmPassword = findViewById(R.id.container_edtxt_confirm_password)
        edtxtConfirmPassword = findViewById(R.id.edtxt_confirm_password)
        btnNext = findViewById(R.id.btn_next)

        edtxtPassword.addTextChangedListener(passwordTextWatcher)

    }

    private val passwordTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            hasUppercase = s.toString() != s.toString().lowercase(Locale.getDefault())
            hasLowercase = s.toString() != s.toString().uppercase(Locale.getDefault())

            if (hasUppercase) {
                imgUpperCase.setImageResource(R.drawable.ic_checked)
            } else {
                imgUpperCase.setImageResource(R.drawable.ic_wrong)
                Utils().enableButton(this@CreatePasswordActivity, btnNext, false)
            }

            if (hasLowercase) {
                imgLowerCase.setImageResource(R.drawable.ic_checked)
            } else {
                imgLowerCase.setImageResource(R.drawable.ic_wrong)
                Utils().enableButton(this@CreatePasswordActivity, btnNext, false)
            }

            if (s.toString().length == hasEightCharacters) {
                imgEightCharacters.setImageResource(R.drawable.ic_checked)
            } else {
                imgEightCharacters.setImageResource(R.drawable.ic_wrong)
                Utils().enableButton(this@CreatePasswordActivity, btnNext, false)
            }
        }
    }
}