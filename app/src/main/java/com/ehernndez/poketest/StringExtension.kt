package com.ehernndez.poketest


// extension that verify if an email is valid
fun String.isValidEmail() =
    isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
