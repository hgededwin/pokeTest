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
import com.ehernndez.poketest.data.api.models.firebaseModels.User
import com.ehernndez.poketest.ui.LoginActivity
import com.google.android.material.card.MaterialCardView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment(R.layout.fragment_home) {
    // lateinit var cardView: MaterialCardView
    lateinit var dbRef: DatabaseReference
    lateinit var btnSaveData: Button
    lateinit var postsList: ArrayList<Post>
    lateinit var btnGetPost: Button
    lateinit var postId: String
    lateinit var btnUpdatePost: Button
    lateinit var btnDeletePost: Button
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFirebaseRDB()

        postsList = arrayListOf()

        btnSaveData = view.findViewById(R.id.btn_save_data)
        btnSaveData.setOnClickListener {
            savePostInfo()
        }

        btnGetPost = view.findViewById(R.id.btn_get_data)
        btnGetPost.setOnClickListener {
            getPosts()
        }

        btnUpdatePost = view.findViewById(R.id.btn_update_data)
        btnUpdatePost.setOnClickListener {
            updatePostInfo()
        }

        btnDeletePost = view.findViewById(R.id.btn_delete_data)
        btnDeletePost.setOnClickListener {
            deletePost()
        }
    }

    private fun initFirebaseRDB() {
        dbRef = FirebaseDatabase.getInstance().getReference("posts")
        // dbRef = FirebaseDatabase.getInstance().getReference("user")
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

        /* val userId = dbRef.push().key!!
         val user = User(userId, "Pedro que gusto de verte", "supe_que_eras_licenciado@gmail.com")

         dbRef.child(userId).setValue(user).addOnCompleteListener {
             Toast.makeText(context, "Usuario creado", Toast.LENGTH_SHORT).show()
         }.addOnFailureListener {
             Toast.makeText(context, "Error al crear el usuario", Toast.LENGTH_SHORT).show()
         } */

    }

    private fun getPosts() {
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                postsList.clear()
                if (snapshot.exists()) {
                    for (postSnapshot in snapshot.children) {
                        val post = postSnapshot.getValue(Post::class.java)
                        postsList.add(post!!)
                    }

                    Log.e("postsList --->", postsList.toString())
                    postId = postsList[0].postId.toString()

                    Log.e("postId --->", postId)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("HomeFragment", "Error al obtener los posts --> ${error.message}")
            }
        })
    }

    private fun updatePostInfo() {
        val post = Post(
            postId,
            "Pokemon go Fest 2024",
            "Ya no estás invitado al evento de pokemon",
            "https://lh3.googleusercontent.com/EtiK3Ov875c98VKVJfP4QEdvsAoXdBI2W1a2kIgNKSjPSF0iWSyuYpoyk9aawlUocT784WQdi3beXsyoaMguj2QvDl2uQK8PK37rxrfSO7xjnZk=rw-e365-w1800"
        )

        dbRef.child(postId).setValue(post).addOnCompleteListener {
            Toast.makeText(context, "Post actualizado", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(context, "Error al actualizar el post", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deletePost() {
        dbRef.child(postId).removeValue().addOnCompleteListener {
            Toast.makeText(context, "Post eliminado", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(context, "Error al eliminar el post", Toast.LENGTH_SHORT).show()
        }
    }
}