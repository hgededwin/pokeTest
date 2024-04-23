package com.ehernndez.poketest.ui.register

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ehernndez.poketest.R
import com.ehernndez.poketest.data.persintetData.Data
import com.ehernndez.poketest.utils.Utils
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.concurrent.TimeUnit

class VerificationCodeActivity : AppCompatActivity() {

    lateinit var timer: CountDownTimer
    lateinit var txtSendCode: TextView
    lateinit var btnNext: Button
    lateinit var btnSendCodeAgain: Button
    lateinit var containerVerificationCode: TextInputLayout
    lateinit var edtxtVerificationCode: TextInputEditText
    lateinit var txtSubtitleEmail: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verifcation_code)

        txtSendCode = findViewById(R.id.txt_send_code_again)
        btnNext = findViewById(R.id.btn_next)
        btnSendCodeAgain = findViewById(R.id.btn_send_code_again)
        containerVerificationCode = findViewById(R.id.container_edtxt_verification_code)
        edtxtVerificationCode = findViewById(R.id.edtxt_verification_code)
        txtSubtitleEmail = findViewById(R.id.txt_subtitle)

        val userEmail = Data.shared.email

        txtSubtitleEmail.text = "Hemos enviado un código de verificación al correo $userEmail. Ayúdanos a ingresarlo para poder continuar."

        // call method to restart countdown timer
        getNewCode()

        edtxtVerificationCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (edtxtVerificationCode.text!!.length != 6) {
                    containerVerificationCode.error = "El código de seguridad debe ser de 6 dígitos"
                    Utils().enableButton(this@VerificationCodeActivity, btnNext,
                        isEnable = false,
                        isVariant = false
                    )
                } else {
                    containerVerificationCode.error = null
                    Utils().enableButton(this@VerificationCodeActivity, btnNext,
                        isEnable = true,
                        isVariant = false
                    )
                }
            }
        })

        // call method to request new code when button is enable
        btnSendCodeAgain.setOnClickListener {
            btnSendCodeAgain.isEnabled = false
            getNewCode()
        }

        btnNext.setOnClickListener {
            val intent = Intent(this@VerificationCodeActivity, CreatePasswordActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getNewCode() {
        // creating a CountdownTimer and adding 30000 millis, that is equals to 30 segs.
        timer = object : CountDownTimer(30000, 1) {
            override fun onTick(millisUntilFinished: Long) {
                // this function count the seconds, here we config the format that we want to show to the final user: "%02d:%02ds"
                val seconds = String.format(
                    "%02d:%02ds",
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                            TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(
                                    millisUntilFinished
                                )
                            )
                )

                val countdownText = "Solicita un código nuevo en $seconds"
                txtSendCode.text = countdownText
            }

            override fun onFinish() {
                // once finish the countdown is necessary to cancel the timer to stop the time.
                // here we have to enable the button to send the new code again.
                timer.cancel()
                btnSendCodeAgain.isEnabled = true
            }
        }
        timer.start()
    }
}