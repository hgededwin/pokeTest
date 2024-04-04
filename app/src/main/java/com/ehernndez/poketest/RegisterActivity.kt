package com.ehernndez.poketest

import android.graphics.Color
import android.os.Bundle
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnNext = findViewById(R.id.btn_next)
        swTermsConditions = findViewById(R.id.sw_terms_conditions)
        containerEdtxtBornDate = findViewById(R.id.container_edtxt_born_day)
        edtxtBornDate = findViewById(R.id.edtxt_born_day)

        btnNext.isEnabled = false
        btnNext.backgroundTintList = ContextCompat.getColorStateList(this@RegisterActivity, R.color.btn_disabled_color)


        edtxtBornDate.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Selecciona la fecha")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

            datePicker.show(supportFragmentManager, "[Born date datePicker")
        }

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