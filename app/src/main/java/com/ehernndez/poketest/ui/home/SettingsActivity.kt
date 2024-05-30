package com.ehernndez.poketest.ui.home

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ehernndez.poketest.R
import com.ehernndez.poketest.data.persistantData.Data
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.materialswitch.MaterialSwitch
import java.util.concurrent.Executor

class SettingsActivity : AppCompatActivity() {
    lateinit var btnTakePicture: FloatingActionButton
    lateinit var imgUser: ImageView
    private var image_uri: Uri? = null
    lateinit var txtUsername: TextView
    lateinit var txtBirthday: TextView
    lateinit var txtEmail: TextView
    lateinit var switchBiometric: MaterialSwitch
    lateinit var executor: Executor
    lateinit var biometricPrompt: BiometricPrompt
    lateinit var biometricPromptInfo: BiometricPrompt.PromptInfo

    private var cameraResultActivity: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                imgUser.setImageURI(image_uri)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        btnTakePicture = findViewById(R.id.fb_take_picture)
        imgUser = findViewById(R.id.img_user_settings)
        txtUsername = findViewById(R.id.txt_username)
        txtBirthday = findViewById(R.id.txt_birthday)
        txtEmail = findViewById(R.id.txt_email)
        switchBiometric = findViewById(R.id.switch_biometric)

        val userName = Data.shared.userName + " " + Data.shared.lastName
        txtUsername.text = userName

        val birthday = Data.shared.bornDate
        txtBirthday.text = birthday

        val email = Data.shared.email
        txtEmail.text = email

        btnTakePicture.setOnClickListener {

            if (checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
            ) {
                openCamera()
            } else {
                val permission = arrayOf(
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                requestPermissions(permission, 123)
            }
        }

        makeBiometricPrompt()

        if (Data.shared.useBiometric) {
            switchBiometric.isChecked = true
        }

        switchBiometric.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {

                if (isBiometricReady(this)) {
                    biometricPrompt.authenticate(biometricPromptInfo)
                } else {
                    Toast.makeText(
                        this,
                        "No tienes habilitada la autenticación por huella digital",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Data.shared.useBiometric = false
            }
        }
    }

    fun hasBiometricCapability(context: Context): Int {
        return BiometricManager.from(context)
            .canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
    }

    fun isBiometricReady(context: Context) = hasBiometricCapability(context) == BiometricManager.BIOMETRIC_SUCCESS

    fun makeBiometricPrompt() {
        executor = ContextCompat.getMainExecutor(this)

        biometricPrompt =
            BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                        Toast.makeText(
                            applicationContext,
                            "No podrás iniciar sesión con tu huella digital",
                            Toast.LENGTH_SHORT
                        ).show()
                        Data.shared.useBiometric = false
                        switchBiometric.isChecked = false
                    }

                    Log.d("BIOMETRIC --->", "$errorCode :: $errString")
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)

                    Data.shared.useBiometric = true
                    Toast.makeText(
                        applicationContext,
                        "Inicio de sesión exitoso",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Data.shared.useBiometric = false
                    switchBiometric.isChecked = false
                    Toast.makeText(
                        applicationContext,
                        "Inicio de sesión fallido",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

        biometricPromptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("PokeTest")
            .setSubtitle("Autenticación por huella digital")
            .setDescription("Para acceder a tu cuenta, debe autenticarse con tu huella digital")
            .setNegativeButtonText("Cancelar")
            .setConfirmationRequired(false)
            .build()
    }

    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "Obtener Imagen")
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image from camera")
        image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        cameraResultActivity.launch(intent)
    }
}