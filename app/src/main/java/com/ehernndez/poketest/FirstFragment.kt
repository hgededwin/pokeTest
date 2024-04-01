package com.ehernndez.poketest

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class FirstFragment : Fragment(R.layout.fragment_first) {

    var active = false
    override fun onStart() {
        super.onStart()
        Log.e("[FIRST FRAGMENT] --->", "method onStart was called")
        active = true
    }

    override fun onStop() {
        super.onStop()
        Log.e("[FIRST FRAGMENT] --->", "method onStop was called")
        active = false
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        Log.e("[FIRST FRAGMENT] --->", "method onAttach was called")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.e("[FIRST FRAGMENT --->", "method onViewCreated was called")
    }

    override fun onDestroyView() {
        super.onDestroyView()

        Log.e("[FIRST FRAGMENT -->", "method onDestroyView was called")
    }
}