package com.ehernndez.poketest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    lateinit var txtHelloWorld: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtHelloWorld = findViewById(R.id.txt_hello_world)
        txtHelloWorld.text = "Hola mundo ahora en español"
        
    }
}