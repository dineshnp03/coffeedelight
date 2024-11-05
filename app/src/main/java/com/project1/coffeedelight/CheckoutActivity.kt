package com.project1.coffeedelight
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class CheckoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        // Initialize EditText fields
        val cardNumberEditText = findViewById<EditText>(R.id.cardNumberEditText)
        val cardHolderNameEditText = findViewById<EditText>(R.id.cardHolderNameEditText)
        val expiryDateEditText = findViewById<EditText>(R.id.expiryDateEditText)
        val cvvEditText = findViewById<EditText>(R.id.cvvEditText)
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val phoneEditText = findViewById<EditText>(R.id.phoneEditText)
        val addressEditText = findViewById<EditText>(R.id.addressEditText)
        val cityEditText = findViewById<EditText>(R.id.cityEditText)
        val zipCodeEditText = findViewById<EditText>(R.id.zipCodeEditText)
        val stateEditText = findViewById<EditText>(R.id.stateEditText)
        val submitButton = findViewById<Button>(R.id.submitButton)

        // Set click listener for Submit button
        submitButton.setOnClickListener {
            if (validateInputs(
                    cardNumberEditText, cardHolderNameEditText, expiryDateEditText, cvvEditText,
                    emailEditText, phoneEditText, addressEditText, cityEditText, zipCodeEditText, stateEditText
                )) {
                // If validation succeeds, proceed to Thank You screen
                startActivity(Intent(this, ThanksActivity::class.java))
                finish()
            }
        }
    }

    // Validation function
    private fun validateInputs(
        cardNumberEditText: EditText,
        cardHolderNameEditText: EditText,
        expiryDateEditText: EditText,
        cvvEditText: EditText,
        emailEditText: EditText,
        phoneEditText: EditText,
        addressEditText: EditText,
        cityEditText: EditText,
        zipCodeEditText: EditText,
        stateEditText: EditText
    ): Boolean {
        var isValid = true

        // Validate each field and show errors if needed
        if (cardNumberEditText.text.toString().trim().length != 16) {
            cardNumberEditText.error = "Enter a valid 16-digit card number"
            isValid = false
        }
        if (cardHolderNameEditText.text.toString().trim().isEmpty()) {
            cardHolderNameEditText.error = "Please enter cardholder name"
            isValid = false
        }
        if (!expiryDateEditText.text.toString().matches(Regex("^(0[1-9]|1[0-2])/[0-9]{2}$"))) {
            expiryDateEditText.error = "Enter expiry date in MM/YY format"
            isValid = false
        }
        if (cvvEditText.text.toString().trim().length != 3) {
            cvvEditText.error = "Enter a valid 3-digit CVV"
            isValid = false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailEditText.text.toString().trim()).matches()) {
            emailEditText.error = "Enter a valid email address"
            isValid = false
        }
        if (phoneEditText.text.toString().trim().isEmpty()) {
            phoneEditText.error = "Please enter a valid phone number"
            isValid = false
        }
        if (addressEditText.text.toString().trim().isEmpty()) {
            addressEditText.error = "Please enter your billing address"
            isValid = false
        }
        if (cityEditText.text.toString().trim().isEmpty()) {
            cityEditText.error = "Please enter your city"
            isValid = false
        }
        if (zipCodeEditText.text.toString().trim().length < 5) {
            zipCodeEditText.error = "Enter a valid zip code"
            isValid = false
        }
        if (stateEditText.text.toString().trim().isEmpty()) {
            stateEditText.error = "Please enter your state"
            isValid = false
        }

        return isValid
    }
}