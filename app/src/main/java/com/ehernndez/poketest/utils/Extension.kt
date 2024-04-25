package com.ehernndez.poketest.utils

import kotlin.random.Random


// extension that verify if an email is valid
fun String.isValidEmail() =
    isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()