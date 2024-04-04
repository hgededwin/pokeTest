package com.ehernndez.poketest

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.switchmaterial.SwitchMaterial
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnNext = findViewById(R.id.btn_next)
        swTermsConditions = findViewById(R.id.sw_terms_conditions)
        containerEdtxtBornDate = findViewById(R.id.container_edtxt_born_day)
        edtxtBornDate = findViewById(R.id.edtxt_born_day)
        containerEdtxtEmail = findViewById(R.id.container_edtxt_email)
        edtxtEmail = findViewById(R.id.edtxt_email)

        btnNext.isEnabled = false
        btnNext.backgroundTintList = ContextCompat.getColorStateList(this@RegisterActivity, R.color.btn_disabled_color)


        edtxtBornDate.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Selecciona la fecha")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()


            datePicker.addOnPositiveButtonClickListener {
                val calendar = Calendar.getInstance()
                calendar.timeZone = TimeZone.getTimeZone("UTC")
                calendar.timeInMillis = it

                val customCalendar = Calendar.getInstance()
                customCalendar.set(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DATE),
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE)
                )
                customCalendar.timeZone = TimeZone.getDefault()

                val format = SimpleDateFormat("dd/MM/yyyy", Locale.US)
                val formattedDate = format.format(customCalendar.time)

                containerEdtxtBornDate.error = null
                edtxtBornDate.setText(formattedDate)
            }

            datePicker.addOnNegativeButtonClickListener {
                if (edtxtBornDate.text!!.isEmpty()) {
                    containerEdtxtBornDate.error = "Es necesario seleccionar la fecha"
                } else {
                    containerEdtxtBornDate.error = null
                }
            }

            datePicker.addOnCancelListener {
                if (edtxtBornDate.text!!.isEmpty()) {
                    containerEdtxtBornDate.error = "Es necesario seleccionar la fecha"
                } else {
                    containerEdtxtBornDate.error = null
                }
            }

            datePicker.show(supportFragmentManager, "[Born date datePicker")
        }

        edtxtEmail.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.e("[beforeTxtChanged]--->", "beforeTextchanged was called")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.e("[onTextChanged]--->", "onTextChanged was called")
            }

            override fun afterTextChanged(s: Editable?) {
                if (edtxtEmail.text.toString().isValidEmail()) {
                    containerEdtxtEmail.error = null
                } else {
                    containerEdtxtEmail.error = "El email es inválido"
                }
                Log.e("[afterTextChanged]--->", "afterTextChanged was called")
            }
        })


        swTermsConditions.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                Log.e("switch --->", "terms and conditions were checked")
                btnNext.isEnabled = true
                btnNext.backgroundTintList = ContextCompat.getColorStateList(this@RegisterActivity, R.color.primary_color)
            } else {
                Log.e("switch --->", "terms and conditions weren't checked")
                btnNext.isEnabled = false
                btnNext.backgroundTintList = ContextCompat.getColorStateList(this@RegisterActivity, R.color.btn_disabled_color)
            }
        }

        btnNext.setOnClickListener {
            // Toast.makeText(this, "El botón ha sido habilitado", Toast.LENGTH_LONG).show()
            Snackbar.make(it, "Se ha habilitado el botón", Snackbar.LENGTH_LONG)
                .setAction("Entendido") {
                    Log.e("Snackbar -->", "user has closed the snackbar")
                }
                .show()
        }
    }
}