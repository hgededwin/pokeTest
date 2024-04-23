package com.ehernndez.poketest.ui.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.ehernndez.poketest.R
import com.ehernndez.poketest.data.persintetData.Data
import com.ehernndez.poketest.utils.Utils
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.Locale
import java.util.regex.Matcher
import java.util.regex.Pattern

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

    lateinit var patternDigits: Pattern
    lateinit var hasADigit: Matcher
    lateinit var patternSpecialCharacter: Pattern
    lateinit var hasSpecialCharacter: Matcher

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

        edtxtPassword.setOnFocusChangeListener { v, hasFocus ->
            if (v.hasFocus()) {
                if (edtxtPassword.text!!.toString() == edtxtConfirmPassword.text!!.toString()) {
                    Utils().enableButton(
                        this@CreatePasswordActivity, btnNext,
                        isEnable = true,
                        isVariant = false
                    )
                } else {
                    Utils().enableButton(
                        this@CreatePasswordActivity, btnNext,
                        isEnable = false,
                        isVariant = false
                    )
                }
            }
        }

        edtxtConfirmPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (edtxtPassword.text.toString() == s.toString()) {
                    containerConfirmPassword.error = null
                    Utils().enableButton(
                        this@CreatePasswordActivity, btnNext,
                        isEnable = true,
                        isVariant = false
                    )
                } else {
                    containerConfirmPassword.error = "La contraseña no coincide."
                    Utils().enableButton(
                        this@CreatePasswordActivity, btnNext,
                        isEnable = false,
                        isVariant = false
                    )
                }
            }
        })

        btnNext.setOnClickListener {
            Data.shared.isRegistered = true
            Utils().createIntent(
                this@CreatePasswordActivity,
                SuccessRegisterActivity()
            )
        }
    }

    private val passwordTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(letter: Editable?) {
            hasUppercase = letter.toString() != letter.toString().lowercase(Locale.getDefault())
            hasLowercase = letter.toString() != letter.toString().uppercase(Locale.getDefault())

            patternDigits = Pattern.compile("[0-9]")
            hasADigit = patternDigits.matcher(letter.toString())

            patternSpecialCharacter = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]")
            hasSpecialCharacter = patternSpecialCharacter.matcher(letter.toString())

            if (hasUppercase) {
                imgUpperCase.setImageResource(R.drawable.ic_checked)
            } else {
                imgUpperCase.setImageResource(R.drawable.ic_wrong)
                Utils().enableButton(
                    this@CreatePasswordActivity, btnNext,
                    isEnable = false,
                    isVariant = false
                )
            }

            if (hasLowercase) {
                imgLowerCase.setImageResource(R.drawable.ic_checked)
            } else {
                imgLowerCase.setImageResource(R.drawable.ic_wrong)
                Utils().enableButton(
                    this@CreatePasswordActivity, btnNext,
                    isEnable = false,
                    isVariant = false
                )
            }

            if (letter.toString().length == hasEightCharacters || edtxtConfirmPassword.text!!.length == hasEightCharacters) {
                imgEightCharacters.setImageResource(R.drawable.ic_checked)
            } else {
                imgEightCharacters.setImageResource(R.drawable.ic_wrong)
                Utils().enableButton(
                    this@CreatePasswordActivity, btnNext,
                    isEnable = false,
                    isVariant = false
                )
            }

            if (hasADigit.find()) {
                imgANumber.setImageResource(R.drawable.ic_checked)
            } else {
                imgANumber.setImageResource(R.drawable.ic_wrong)
                Utils().enableButton(
                    this@CreatePasswordActivity, btnNext,
                    isEnable = false,
                    isVariant = false
                )
            }

            if (hasSpecialCharacter.find()) {
                imgSpecialCharacter.setImageResource(R.drawable.ic_checked)
            } else {
                imgSpecialCharacter.setImageResource(R.drawable.ic_wrong)
                Utils().enableButton(
                    this@CreatePasswordActivity, btnNext,
                    isEnable = false,
                    isVariant = false
                )
            }

            if (edtxtPassword.text!!.isEmpty()) {
                containerPassword.error = "Es necesario escribir la contraseña."
            } else {
                containerPassword.error = null
            }
        }
    }
}