package com.ehernndez.poketest

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.e("--->", "method onCreate was called")

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container_view, FirstFragment())
                .commit()
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