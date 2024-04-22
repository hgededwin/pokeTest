package com.ehernndez.poketest.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ehernndez.poketest.R
import java.util.UUID

class FirstFragment : Fragment(R.layout.fragment_first) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("[FIRST FRAGMENT --->", "method onViewCreated was called")
    }

    override fun onStart() {
        super.onStart()
        Log.e("[FIRST FRAGMENT] --->", "method onStart was called")
    }

    override fun onStop() {
        super.onStop()
        Log.e("[FIRST FRAGMENT] --->", "method onStop was called")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        Log.e("[FIRST FRAGMENT] --->", "method onAttach was called")
    }


    override fun onDestroyView() {
        super.onDestroyView()

        Log.e("[FIRST FRAGMENT -->", "method onDestroyView was called")
    }
}