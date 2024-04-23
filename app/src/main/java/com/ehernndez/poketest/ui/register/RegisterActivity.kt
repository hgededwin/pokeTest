package com.ehernndez.poketest.ui.register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ehernndez.poketest.R
import com.ehernndez.poketest.data.persintetData.Data
import com.ehernndez.poketest.utils.DateValidatorRange
import com.ehernndez.poketest.utils.isValidEmail
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class RegisterActivity : AppCompatActivity() {
    lateinit var btnNext: Button
    lateinit var swTermsConditions: MaterialSwitch
    lateinit var containerEdtxtBornDate: TextInputLayout
    lateinit var edtxtBornDate: TextInputEditText
    lateinit var containerEdtxtEmail: TextInputLayout
    lateinit var edtxtEmail: TextInputEditText
    lateinit var containerEdtxtName: TextInputLayout
    lateinit var edtxtName: TextInputEditText
    lateinit var containerEdtxtLastName: TextInputLayout
    lateinit var edtxtLastName: TextInputEditText
    lateinit var txtTermsAndConditions: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnNext = findViewById(R.id.btn_next)
        swTermsConditions = findViewById(R.id.sw_terms_conditions)
        containerEdtxtBornDate = findViewById(R.id.container_edtxt_born_day)
        edtxtBornDate = findViewById(R.id.edtxt_born_day)
        containerEdtxtEmail = findViewById(R.id.container_edtxt_email)
        edtxtEmail = findViewById(R.id.edtxt_email)
        containerEdtxtName = findViewById(R.id.container_edtxt_name)
        edtxtName = findViewById(R.id.edtxt_name)
        containerEdtxtLastName = findViewById(R.id.container_edtxt_lastname)
        edtxtLastName = findViewById(R.id.edtxt_lastname)
        txtTermsAndConditions = findViewById(R.id.txt_terms_conditions)

        edtxtBornDate.setOnClickListener {
            validateBornDate()
        }

        edtxtName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.e("[beforeTxtChanged edtxtName]--->", "beforeTextchanged was called")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.e("[onTextChanged edtxtName]--->", "onTextChanged was called")
            }

            override fun afterTextChanged(s: Editable?) {
                Log.e("[afterText edtxtName]--->", "afterTextChanged was called")
                if (edtxtName.text!!.isEmpty()) {
                    containerEdtxtName.error = "Escribe tu nombre"
                } else {
                    containerEdtxtName.error = null
                }

                validateInputs()
            }
        })

        edtxtLastName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.e("[beforeTxtChanged edtxtLastname]--->", "beforeTextchanged was called")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.e("[onTextChanged edtxtLastname]--->", "onTextChanged was called")

            }

            override fun afterTextChanged(s: Editable?) {
                Log.e("[afterText edtxtLastname]--->", "afterTextChanged was called")
                if (edtxtLastName.text!!.isEmpty()) {
                    containerEdtxtLastName.error = "Escribe tus apellidos"
                } else {
                    containerEdtxtLastName.error = null
                }

                validateInputs()
            }
        })


        edtxtEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.e("[beforeTxtChanged]--->", "beforeTextchanged was called")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.e("[onTextChanged]--->", "onTextChanged was called")
            }

            override fun afterTextChanged(s: Editable?) {
                Log.e("[afterTextChanged]--->", "afterTextChanged was called")

                if (edtxtEmail.text.toString().isValidEmail()) {
                    containerEdtxtEmail.error = null
                } else {
                    containerEdtxtEmail.error = "El email es inválido"
                }

                validateInputs()
            }
        })

        txtTermsAndConditions.setOnClickListener {
            val intent = Intent(this, TermsAndConditionsActivity::class.java)
            startActivity(intent)
        }

        swTermsConditions.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                Log.e("switch --->", "terms and conditions were checked")
            } else {
                Log.e("switch --->", "terms and conditions weren't checked")
            }
            validateInputs()
        }

        btnNext.setOnClickListener {
            saveDataUser()
            val intent = Intent(this@RegisterActivity, VerificationCodeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveDataUser() {
        Data.shared.userName = edtxtName.text.toString()
        Data.shared.lastName = edtxtLastName.text.toString()
        Data.shared.bornDate = edtxtBornDate.text.toString()
        Data.shared.email = edtxtEmail.text.toString()
    }

    private fun validateBornDate() {
        // creating an instance of calendar
        val calendar = Calendar.getInstance()
        calendar.timeZone = TimeZone.getTimeZone("UTC")

        // selecting today
        val today = MaterialDatePicker.todayInUtcMilliseconds()
        calendar.timeInMillis = today

        // configurating calendar to take 100 years backward
        calendar.set(
            calendar[Calendar.YEAR] - 100,
            calendar[Calendar.MONTH],
            calendar[Calendar.DATE]
        )
        val oneHundredYearsBack = calendar.timeInMillis
        calendar.timeInMillis = today

        // configurating calendar to take -19 yo. This means that if you had 18 yo, you can use the app.
        calendar.set(
            calendar[Calendar.YEAR] - 19,
            calendar[Calendar.MONTH],
            calendar[Calendar.DATE]
        )
        val eighteenYearsBack = calendar.timeInMillis

        // setting constraints into the datePicker
        val constraintBuilder = CalendarConstraints.Builder()
            .setStart(oneHundredYearsBack)
            .setEnd(eighteenYearsBack)
            .setValidator(DateValidatorRange(oneHundredYearsBack, eighteenYearsBack))

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Selecciona la fecha")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setCalendarConstraints(constraintBuilder.build())
            .build()


        // onPositiveButtonClick method for datePicker
        datePicker.addOnPositiveButtonClickListener {
            validateInputs()

            // creating another instance of calendar to correct the current date.
            val customCalendar = Calendar.getInstance()
            customCalendar.set(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE),
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE)
            )
            customCalendar.timeZone = TimeZone.getDefault()
            customCalendar.timeInMillis = it

            val format = SimpleDateFormat("dd/MM/yyyy", Locale.US)
            val formattedDate = format.format(customCalendar.time)

            containerEdtxtBornDate.error = null
            edtxtBornDate.setText(formattedDate)
        }

        // onNegativeButtonClick method for datePicker
        datePicker.addOnNegativeButtonClickListener {
            if (edtxtBornDate.text!!.isEmpty()) {
                containerEdtxtBornDate.error = "Es necesario seleccionar la fecha"
            } else {
                containerEdtxtBornDate.error = null
            }
        }

        // onCancelButtonClick method for datePicker
        datePicker.addOnCancelListener {
            if (edtxtBornDate.text!!.isEmpty()) {
                containerEdtxtBornDate.error = "Es necesario seleccionar la fecha"
            } else {
                containerEdtxtBornDate.error = null
            }
        }

        datePicker.show(supportFragmentManager, "[Born date datePicker")
    }

    private fun validateInputs() {
        if (edtxtName.text!!.isEmpty() ||
            edtxtLastName.text!!.isEmpty() ||
            edtxtBornDate.text!!.isEmpty() ||
            edtxtEmail.text!!.isEmpty() ||
            !swTermsConditions.isChecked
        ) {
            btnNext(false)
        } else {
            btnNext(true)
        }
    }

    private fun btnNext(isEnable: Boolean) {
        if (isEnable) {
            btnNext.isEnabled = true
            btnNext.backgroundTintList =
                ContextCompat.getColorStateList(this@RegisterActivity, R.color.primary_color)
        } else {
            btnNext.isEnabled = false
            btnNext.backgroundTintList =
                ContextCompat.getColorStateList(this@RegisterActivity, R.color.btn_disabled_color)
        }
    }
}