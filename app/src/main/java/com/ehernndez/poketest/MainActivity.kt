package com.ehernndez.poketest

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    lateinit var btnNext: Button
    lateinit var btnBack: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.e("--->", "method onCreate was called")

        btnNext = findViewById(R.id.btn_next)
        btnBack = findViewById(R.id.btn_back)

        btnBack.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_container_view, FirstFragment())
                    .commit()
            }
        }

        btnNext.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_container_view, SecondFragment())
                    .commit()
            }
        }
    }

    override fun onStart() {
        super.onStart()

        Log.e("-->", "method onStart was called ")
    }

    override fun onStop() {
        super.onStop()

        Log.e("--->", "method onStop was called")
    }

    override fun onRestart() {
        super.onRestart()

        Log.e("--->", "method onRestart was called")
    }

    override fun onResume() {
        super.onResume()

        Log.e("--->", "method onResume was called")
    }
}