package com.ehernndez.poketest.ui.home.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.ehernndez.poketest.R
import com.ehernndez.poketest.data.api.models.firebaseModels.Post
import com.ehernndez.poketest.ui.LoginActivity
import com.google.android.material.card.MaterialCardView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class HomeFragment : Fragment(R.layout.fragment_home) {
    // lateinit var cardView: MaterialCardView
    lateinit var dbRef: DatabaseReference
    lateinit var btnSaveData: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFirebaseRDB()

        btnSaveData = view.findViewById(R.id.btn_save_data)
        btnSaveData.setOnClickListener {
            savePostInfo()
        }
    }

    private fun initFirebaseRDB() {
        dbRef = FirebaseDatabase.getInstance().getReference("posts")
    }

    private fun savePostInfo() {
        val postId = dbRef.push().key!!

        val post = Post(
            postId,
            "Festival de Pokemon Go",
            "Te invitamos al evento super magnifico de Pokemon Go que se llevará acabo en la CDMX.",
            ""
        )
        dbRef.child(postId).setValue(post).addOnCompleteListener {
            Toast.makeText(context, "Post creado", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(context, "Error al crear el post", Toast.LENGTH_SHORT).show()
        }
    }
}