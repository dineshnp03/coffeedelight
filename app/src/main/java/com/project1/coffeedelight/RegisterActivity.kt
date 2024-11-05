package com.project1.coffeedelight

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var loginLinkTextView: TextView
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Applying window insets to the root layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.registerLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize UI components
        emailEditText = findViewById(R.id.textEmailRegister)
        passwordEditText = findViewById(R.id.textPasswordRegister)
        confirmPasswordEditText = findViewById(R.id.textConfirmPasswordRegister)
        registerButton = findViewById(R.id.btnRegister)
        loginLinkTextView = findViewById(R.id.linkRegister)

        // Set click listeners
        registerButton.setOnClickListener { handleRegistration() }
        loginLinkTextView.setOnClickListener { navigateToLogin() }
    }

    private fun handleRegistration() {
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()
        val confirmPassword = confirmPasswordEditText.text.toString().trim()

        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        // Creating a new user with Firebase Authentication
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) {
            task -> if (task.isSuccessful) {
                    // Sign in success
                    Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                    navigateToLogin() // Navigate to LoginActivity after successful registration
                } else {
                    // If sign in fails, display a message to the user
                    Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun registerUser(email: String, password: String): Boolean {
        return true // Return true for successful registration
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}