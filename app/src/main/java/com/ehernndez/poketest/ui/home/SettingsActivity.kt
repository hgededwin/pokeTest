package com.ehernndez.poketest.ui.home

import android.content.ContentValues
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
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ehernndez.poketest.R
import com.ehernndez.poketest.data.persistantData.Data
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.concurrent.Executor

class SettingsActivity : AppCompatActivity() {
    lateinit var btnTakePicture: FloatingActionButton
    lateinit var imgUser: ImageView
    private var image_uri: Uri? = null
    lateinit var txtUsername: TextView
    lateinit var txtBirthday: TextView
    lateinit var txtEmail: TextView

    lateinit var executor: Executor
    lateinit var biometricPrompt: BiometricPrompt
    lateinit var biometricPromptInfo: BiometricPrompt.PromptInfo

    private var cameraResultActivity: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
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

        val userName = Data.shared.userName + " " + Data.shared.lastName
        txtUsername.text = userName

        val birthday = Data.shared.bornDate
        txtBirthday.text = birthday

        val email = Data.shared.email
        txtEmail.text = email

        btnTakePicture.setOnClickListener {

            if (checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else {
                val  permission = arrayOf(
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permission, 123)
            }
        }

        executor = ContextCompat.getMainExecutor(this)

        biometricPrompt = BiometricPrompt(this, executor, object: BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    Toast.makeText(applicationContext, "No podrás iniciar sesión con tu huella digital", Toast.LENGTH_SHORT).show()
                }

                Log.d("BIOMETRIC --->", "$errorCode :: $errString")
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)

                Toast.makeText(applicationContext, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()

                Toast.makeText(applicationContext, "Inicio de sesión fallido", Toast.LENGTH_SHORT).show()
            }
        })
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