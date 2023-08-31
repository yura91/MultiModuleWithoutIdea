package com.example.multimoduleapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, SignInFragment(), "fragmentTag")
            .disallowAddToBackStack()
            .commit()
    }
}