package com.ehernndez.poketest.ui.home.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import com.ehernndez.poketest.R
import com.ehernndez.poketest.ui.LoginActivity
import com.google.android.material.card.MaterialCardView

class HomeFragment : Fragment(R.layout.fragment_home) {
   // lateinit var cardView: MaterialCardView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       // adding intent from fragment to new activity
      /*  cardView = view.findViewById(R.id.card_view_home)
        cardView.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        } */


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