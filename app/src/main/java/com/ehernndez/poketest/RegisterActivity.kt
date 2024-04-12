package com.ehernndez.poketest

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ehernndez.poketest.utils.DateValidatorRange
import com.ehernndez.poketest.utils.isValidEmail
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.snackbar.Snackbar
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
            // Toast.makeText(this, "El botón ha sido habilitado", Toast.LENGTH_LONG).show()
            val intent = Intent(this@RegisterActivity, VerificationCodeActivity::class.java)
            startActivity(intent)
        }
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
            .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
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

            val format = SimpleDateFormat("dd/MM/yyyy", Locale.US)
            val formattedDate = format.format(calendar.time)

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

/*
package com.cacao.dock.credito.ui.view.register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import com.cacao.dock.credito.R
import com.cacao.dock.credito.core.RetrofitInstance
import com.cacao.dock.credito.data.api.helper.createPassword.CreatePasswordApiHelper
import com.cacao.dock.credito.data.model.createPassword.CreatePasswordRequest
import com.cacao.dock.credito.data.model.createUser.CreateUserRequest
import com.cacao.dock.credito.data.model.login.CreateLoginRequest
import com.cacao.dock.credito.data.persistentData.Data
import com.cacao.dock.credito.data.utils.Status
import com.cacao.dock.credito.databinding.ActivityCreatePasswordBinding
import com.cacao.dock.credito.ui.utils.Flow
import com.cacao.dock.credito.ui.utils.Utils
import com.cacao.dock.credito.ui.utils.alerts.Alerts
import com.cacao.dock.credito.ui.utils.biometric.BiometricUtils
import com.cacao.dock.credito.ui.utils.biometric.CryptoManager
import com.cacao.dock.credito.ui.utils.biometric.CryptographyManager
import com.cacao.dock.credito.ui.utils.inactivity.BaseActivity
import com.cacao.dock.credito.ui.utils.inactivity.InactivitySingleton
import com.cacao.dock.credito.ui.utils.loader.LoaderUtils
import com.cacao.dock.credito.ui.view.resetPassword.SuccessResetPasswordActivity
import com.cacao.dock.credito.ui.viewModel.createPassword.CreatePasswordViewModel
import com.cacao.dock.credito.ui.viewModel.createPassword.CreatePasswordViewModelFactory
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class CreatePasswordActivity : BaseActivity() {
    private lateinit var binding: ActivityCreatePasswordBinding
    private lateinit var createPasswordVieModel: CreatePasswordViewModel
    private lateinit var deviceId: String
    private lateinit var cryptoManager: CryptoManager
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var biometricPromptInfo: BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        Utils().setThemeDisableButton(this@CreatePasswordActivity, binding.btnNext)
        InactivitySingleton.currentFlow = intent.getStringExtra("FLOW_TYPE")!!

        if (InactivitySingleton.currentFlow == Flow.RESETPASSWORD.type || InactivitySingleton.currentFlow == Flow.CHANGEPASSWORD.type) {
            binding.toolbar.visibility = View.VISIBLE
            binding.toolbar.setTitleTextAppearance(this, R.style.RoobertToolbarAppareance)

            val window = window
            window.statusBarColor = resources.getColor(R.color.blueDarkDockColor)

            binding.icDock.visibility = View.INVISIBLE
            binding.txtTitleAccess.visibility = View.INVISIBLE
            binding.containerBiometric.visibility = View.GONE
            binding.containerSteps.visibility = View.GONE

        } else {
            binding.toolbar.visibility = View.GONE
            binding.icDock.visibility = View.VISIBLE
            binding.txtTitleAccess.visibility = View.VISIBLE
            binding.containerBiometric.visibility = View.VISIBLE
        }

        binding.edtxtPassword.addTextChangedListener(textWatcherPassword)
        binding.edtxtConfirmPassword.addTextChangedListener(textWatcherConfirmPassword)
        binding.btnNext.setOnClickListener {

            when (InactivitySingleton.currentFlow) {
                Flow.ENROLLMENT.type -> {
                    val request = CreateUserRequest(
                        Data.userShared.userMobileId,
                        Data.userShared.userEmail,
                        Data.userShared.userPhone,
                        Data.userShared.userCardNumber,
                        binding.edtxtConfirmPassword.text.toString(),
                        Data.userShared.userName,
                        "",
                        Data.userShared.userMaternalName,
                        Data.userShared.userPaternalName,
                        deviceId
                    )
                    createUser(request)
                }

                Flow.CHANGEPASSWORD.type -> {
                    val request = CreatePasswordRequest(
                        Data.userShared.userMobileId,
                        binding.edtxtConfirmPassword.text.toString()
                    )
                    createPassword(request)
                }

                Flow.RESETPASSWORD.type -> {
                    val request = CreatePasswordRequest(
                        Data.userShared.userMobileId,
                        binding.edtxtConfirmPassword.text.toString()
                    )
                    createPassword(request)
                }
            }
        }

        binding.swBiometric.setOnCheckedChangeListener { _, ischecked ->
            if (ischecked) {
                println("ACTIVATE ")
                Data.settingsShared.isEnabledBiometric = true
            } else {
                println("DESACTIVATE")
                Data.settingsShared.isEnabledBiometric = false
            }
        }

        deviceId = Utils().getDeviceId(this)
        setUpViewModel()
        cryptoManager = CryptographyManager()
        biometricPrompt = createBiometricPrompt()
        biometricPromptInfo = BiometricUtils().createPromptInfo(
            getString(R.string.txt_use_fingerprint_login_description),
            getString(R.string.txt_use_fingerprint_description)
        )
    }

    private fun createBiometricPrompt(): BiometricPrompt {
        val executor = ContextCompat.getMainExecutor(this)

        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                println("ERROR AUTENTICACION")
                Data.settingsShared.isEnabledBiometric = false
                binding.swBiometric.isChecked = false
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                println("ERROR UNOPWN RASON")
                failedAuth()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                println("SUCCESS")
                val res = BiometricUtils().saveDataToLogin(
                    result.cryptoObject,
                    binding.edtxtConfirmPassword.text.toString(),
                    cryptoManager
                )
                if (res) {
                    successAuth()
                }
            }
        }
        return BiometricPrompt(this, executor, callback)
    }

    fun successAuth() {
        Data.userShared.data = binding.edtxtConfirmPassword.text.toString()
        val intent = Intent(this, SuccessRegisterActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun failedAuth() {
        Alerts.showCustomDialog(
            this,
            layoutInflater,
            false,
            getString(R.string.txt_btn_understand),
            getString(R.string.txt_general_error),
            completion = {
            }
        )
    }

    fun createUser(request: CreateUserRequest) {
        createPasswordVieModel.createUser(request).observe(this) {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data.let { data ->
                            when (data?.codeResponse) {
                                getString(R.string.success_response) -> {
                                    LoaderUtils.hideLoader()
                                    // IMPLEMENT BIOMETRIC VALIDATION
                                    if (Data.settingsShared.isEnabledBiometric) {
                                        BiometricUtils().authenticateToEncrypt(
                                            this,
                                            cryptoManager,
                                            biometricPrompt,
                                            biometricPromptInfo
                                        )
                                    } else {
                                        successAuth()
                                    }
                                }

                                else -> {
                                    Alerts.showCustomDialog(
                                        this,
                                        layoutInflater,
                                        false,
                                        getString(R.string.txt_btn_understand),
                                        getString(R.string.txt_general_error),
                                        completion = {
                                        }
                                    )
                                }
                            }
                        }
                    }
                    Status.ERROR -> {
                        LoaderUtils.hideLoader()
                    }
                    Status.LOADING -> {
                        LoaderUtils.showLoader(this)
                    }
                }
            }
        }
    }

    fun createPassword(request: CreatePasswordRequest) {
        createPasswordVieModel.createPassword(request).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        LoaderUtils.hideLoader()
                        resource.data.let { data ->
                            when (data?.codeResponse) {
                                getString(R.string.success_response) -> {
                                    if (InactivitySingleton.currentFlow == Flow.CHANGEPASSWORD.type) {
                                        val req = CreateLoginRequest(
                                            Data.userShared.userEmail,
                                            Data.userShared.userPhone,
                                            binding.edtxtConfirmPassword.text.toString(),
                                            deviceId,
                                            Data.userShared.userMobileId
                                        )
                                        createLogin(req)
                                    } else {
                                        Data.userShared.data =
                                            binding.edtxtConfirmPassword.text.toString()
                                        Data.settingsShared.isEnabledBiometric = false

                                        val intent =
                                            Intent(this, SuccessResetPasswordActivity::class.java)
                                        intent.putExtra(
                                            "FLOW_TYPE",
                                            InactivitySingleton.currentFlow
                                        )
                                        startActivity(intent)
                                        finish()
                                    }
                                }
                                "7777" -> {
                                    Alerts.showCustomDialog(
                                        this,
                                        layoutInflater,
                                        false,
                                        getString(R.string.txt_btn_understand),
                                        getString(R.string.txt_password_has_been_used),
                                        completion = {
                                        }
                                    )
                                }
                                else -> {
                                    Alerts.showCustomDialog(
                                        this,
                                        layoutInflater,
                                        false,
                                        getString(R.string.txt_btn_understand),
                                        getString(R.string.txt_general_error),
                                        completion = {
                                        }
                                    )
                                }
                            }
                        }

                    }
                    Status.ERROR -> {
                        LoaderUtils.hideLoader()
                        Alerts.showCustomDialog(
                            this,
                            layoutInflater,
                            false,
                            getString(R.string.txt_btn_understand),
                            getString(R.string.txt_general_error),
                            completion = {
                            }
                        )
                    }
                    Status.LOADING -> {
                        LoaderUtils.showLoader(this)
                    }
                }
            }
        })
    }

    private fun createLogin(request: CreateLoginRequest) {
        createPasswordVieModel.createLogin(request).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        LoaderUtils.hideLoader()
                        resource.data?.let { data ->
                            when (data.codeResponse) {
                                getString(R.string.success_response) -> {
                                    Data.accessShared.accessTokenSession =
                                        data.accessToken.toString()
                                    Data.accessShared.refreshTokenSession =
                                        data.refreshToken.toString()
                                    Data.accessShared.expiresInTokenSession = data.expiresIn!!
                                    Data.userShared.data =
                                        binding.edtxtConfirmPassword.text.toString()
                                    Data.settingsShared.isEnabledBiometric = false

                                    val intent =
                                        Intent(this, SuccessResetPasswordActivity::class.java)
                                    intent.putExtra("FLOW_TYPE", InactivitySingleton.currentFlow)
                                    startActivity(intent)
                                    finish()
                                }
                                else -> {
                                    Alerts.showCustomDialog(
                                        this,
                                        layoutInflater,
                                        false,
                                        getString(R.string.txt_btn_understand),
                                        getString(R.string.txt_general_error),
                                        completion = {
                                        }
                                    )
                                }
                            }
                        }
                    }
                    Status.ERROR -> {
                        LoaderUtils.hideLoader()
                        Alerts.showCustomDialog(
                            this,
                            layoutInflater,
                            false,
                            getString(R.string.txt_btn_understand),
                            getString(R.string.txt_general_error),
                            completion = {
                            }
                        )
                    }
                    Status.LOADING -> {
                    }
                }
            }
        })
    }

    fun setUpViewModel() {
        createPasswordVieModel = ViewModelProvider(
            this, CreatePasswordViewModelFactory(
                CreatePasswordApiHelper(RetrofitInstance.apiService, this)
            )
        )[CreatePasswordViewModel::class.java]
    }

    private val textWatcherConfirmPassword = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            if (binding.edtxtPassword.text.toString() == s.toString()) {
                binding.containerConfirmCardNumber.error = null
                Utils().setThemeButton(this@CreatePasswordActivity, binding.btnNext)
            } else {
                binding.containerConfirmCardNumber.error = "La contraseña no coincide."
                Utils().setThemeDisableButton(this@CreatePasswordActivity, binding.btnNext)
            }
        }
    }

    private val textWatcherPassword = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            val hasUpperCase = s.toString() != s.toString().lowercase(Locale.getDefault())
            val hasLowerCase = s.toString() != s.toString().uppercase(Locale.getDefault())
            val eightCharacters = 8

            val patSameDigits: Pattern =
                Pattern.compile("0{3,}|1{3,}|2{3,}|3{3,}|4{3,}|5{3,}|6{3,}|7{3,}|8{3,}|9{3,}")
            val sameDigits: Matcher = patSameDigits.matcher(s.toString())

            val patSecDigits: Pattern =
                Pattern.compile("111|222|333|444|555|666|777|888|999|00|012|123|234|345|456|567|678|789|890")
            val secuenceDigits: Matcher = patSecDigits.matcher(s.toString())

            val patSecDigitsReversed: Pattern =
                Pattern.compile("111|222|333|444|555|666|777|888|999|00|012|123|234|345|456|567|678|789|890".reversed())
            val secuenceDigitsReversed: Matcher = patSecDigitsReversed.matcher(s.toString())

            val patDigs: Pattern = Pattern.compile("[0-9]")
            val digits: Matcher = patDigs.matcher(s.toString())

            if (hasUpperCase) {
                binding.icUpperCaseLetter.setImageResource(R.drawable.ic_valid_character)
            } else {
                binding.icUpperCaseLetter.setImageResource(R.drawable.ic_invalid_character)
                Utils().setThemeDisableButton(this@CreatePasswordActivity, binding.btnNext)
            }

            if (hasLowerCase) {
                binding.icLowercaseLetter.setImageResource(R.drawable.ic_valid_character)
            } else {
                binding.icLowercaseLetter.setImageResource(R.drawable.ic_invalid_character)
                Utils().setThemeDisableButton(this@CreatePasswordActivity, binding.btnNext)
            }

            if (s!!.length == eightCharacters) {
                binding.icEightCharacters.setImageResource(R.drawable.ic_valid_character)
            } else {
                binding.icEightCharacters.setImageResource(R.drawable.ic_invalid_character)
                Utils().setThemeDisableButton(this@CreatePasswordActivity, binding.btnNext)
            }

            if (!sameDigits.find() && digits.find() && !secuenceDigits.find() && !secuenceDigitsReversed.find()) {
                binding.icNotConsecutivesNumber.setImageResource(R.drawable.ic_valid_character)
            } else {
                binding.icNotConsecutivesNumber.setImageResource(R.drawable.ic_invalid_character)
                Utils().setThemeDisableButton(this@CreatePasswordActivity, binding.btnNext)
            }
        }
    }
}
*/
