package com.ehernndez.poketest.ui.home

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.ehernndez.poketest.R
import com.ehernndez.poketest.data.persistantData.Data
import com.ehernndez.poketest.ui.home.fragments.HomeFragment
import com.ehernndez.poketest.ui.home.fragments.PokedexFragment
import com.ehernndez.poketest.ui.home.fragments.PokemonFragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.navigation.NavigationView
import java.io.File
import java.util.concurrent.Executor

class HomeActivity : AppCompatActivity() {
    lateinit var navigationView: BottomNavigationView
    lateinit var toolbar: MaterialToolbar

    lateinit var actionDrawerToggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    lateinit var drawerNavigation: NavigationView

    lateinit var executor: Executor
    lateinit var biometricPrompt: BiometricPrompt
    lateinit var biometricPromptInfo: BiometricPrompt.PromptInfo

    var isBiometricEnabled = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        navigationView = findViewById(R.id.navigation_view)
        toolbar = findViewById(R.id.home_toolbar)
        drawerLayout = findViewById(R.id.drawer_layout_home)
        drawerNavigation = findViewById(R.id.drawer_navigation_view)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // setting first fragment to show when load the home activity
        replaceFragment(HomeFragment())

        createBottomNavigationMenu()
        createDrawerMenu()

        makeBiometricPrompt()
    }

    private fun createDrawerMenu() {
        actionDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.txt_open_drawer,
            R.string.txt_close_drawer
        )
        drawerLayout.addDrawerListener(actionDrawerToggle)
        actionDrawerToggle.syncState()

        drawerNavigation.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_notifications -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    Toast.makeText(this, "Notifications", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.item_share -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.item_info -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    Toast.makeText(this, "Info", Toast.LENGTH_SHORT).show()
                    true
                }

                else -> false
            }
        }

        customHeader()

    }

    private fun customHeader() {
        val headerView = drawerNavigation.getHeaderView(0)
        val txtUserName = headerView.findViewById<TextView>(R.id.txt_username)
        val username = Data.shared.userName + " " + Data.shared.lastName
        txtUserName.text = username

        val txtBirthday = headerView.findViewById<TextView>(R.id.txt_birthday)
        txtBirthday.text = Data.shared.bornDate

        val txtEmail = headerView.findViewById<TextView>(R.id.txt_email)
        txtEmail.text = Data.shared.email

        val swBiometric = headerView.findViewById<MaterialSwitch>(R.id.switch_biometric)

        swBiometric.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                swBiometric.trackTintList = getColorStateList(R.color.primary_color)
                swBiometric.thumbTintList = getColorStateList(R.color.white_color)

                if (isBiometricReady(this)) {
                    biometricPrompt.authenticate(biometricPromptInfo)
                } else {
                    Toast.makeText(
                        this,
                        "No tienes habilitada la autenticación por huella digital",
                        Toast.LENGTH_SHORT
                    ).show()
                    swBiometric.isChecked = false
                    Data.shared.useBiometric = false
                }

            } else {
                swBiometric.trackTintList = getColorStateList(R.color.primary_container_color)
                swBiometric.thumbTintList = getColorStateList(R.color.gray_text_color)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionDrawerToggle.onOptionsItemSelected(item)) {
            return false
        }
        return super.onOptionsItemSelected(item)
    }

    private fun createBottomNavigationMenu() {
        navigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_home -> {
                    replaceFragment(HomeFragment())
                    true
                }

                R.id.item_pokedex -> {
                    replaceFragment(PokedexFragment())
                    true
                }

                R.id.item_new_pokemon -> {
                    replaceFragment(PokemonFragment())
                    true
                }

                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment).commit()
        }
    }

    fun makeBiometricPrompt() {
        executor = ContextCompat.getMainExecutor(this)

        biometricPrompt =
            BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                        Toast.makeText(
                            this@HomeActivity,
                            "No podrás iniciar sesión con tu huella digital",
                            Toast.LENGTH_SHORT
                        ).show()
                        Data.shared.useBiometric = false
                    }

                    Log.d("BIOMETRIC --->", "$errorCode :: $errString")
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)

                    Data.shared.useBiometric = true
                    Toast.makeText(
                        this@HomeActivity,
                        "Inicio de sesión exitoso",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Data.shared.useBiometric = false
                    Toast.makeText(
                        this@HomeActivity,
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

    fun hasBiometricCapability(context: Context): Int {
        return BiometricManager.from(context)
            .canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
    }

    fun isBiometricReady(context: Context) =
        hasBiometricCapability(context) == BiometricManager.BIOMETRIC_SUCCESS

}