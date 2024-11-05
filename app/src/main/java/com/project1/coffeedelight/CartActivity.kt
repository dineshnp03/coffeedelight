package com.project1.coffeedelight

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class CartActivity : AppCompatActivity() {

    private lateinit var recyclerViewCartItems: RecyclerView
    private lateinit var textViewTotal: TextView
    private lateinit var textViewTax: TextView
    private lateinit var textViewBill: TextView
    private lateinit var buttonCheckout: Button
    private lateinit var cartAdapter: CartAdapter
    private val cartItemList = CartManager.getCartItems().toMutableList()

    private val TAX_RATE = 0.13  // 13% tax rate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        recyclerViewCartItems = findViewById(R.id.recyclerViewCartItems)
        textViewTotal = findViewById(R.id.textViewTotal)
        textViewTax = findViewById(R.id.textViewTax)
        textViewBill = findViewById(R.id.textViewBill)
        buttonCheckout = findViewById(R.id.buttonCheckout)
        cartAdapter = CartAdapter(this, cartItemList) { refreshCartItems() }
        recyclerViewCartItems.layoutManager = LinearLayoutManager(this)
        recyclerViewCartItems.adapter = cartAdapter

        updateSummary()
        if(cartItemList.isEmpty()) {
            buttonCheckout.isEnabled = false
            Toast.makeText(this,"No Products added to the Cart", Toast.LENGTH_SHORT).show()
        } else {
            buttonCheckout.isEnabled = true
        }
        buttonCheckout.setOnClickListener {
            if(cartItemList.isEmpty()) {
                Log.e("Empty Cart", "No Data available in the Cart")
                Toast.makeText(this,"No Products added to the Cart", Toast.LENGTH_SHORT).show()
            } else {
                startActivity(Intent(this, CheckoutActivity::class.java))
            }

        }
        // Navigates back to the previous Activity
        val backButton = findViewById<ImageButton>(R.id.back_arrow)
        backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val logoutButton: ImageButton = findViewById(R.id.img_logout)
        // Logout Function
        logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            // Redirect to login activity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun updateSummary() {
        val totalAmount = cartItemList.sumOf { it.quantity * it.product.price }
        val taxAmount = totalAmount * TAX_RATE
        val finalBill = totalAmount + taxAmount

        // Use string interpolation for better readability and to avoid format issues
        textViewTotal.text = "Total: $${"%.2f".format(totalAmount)}"
        textViewTax.text = "Tax (13%): $${"%.2f".format(taxAmount)}"
        textViewBill.text = "Bill: $${"%.2f".format(finalBill)}"
    }

    fun refreshCartItems() {
        cartItemList.clear() // Clear the old list
        cartItemList.addAll(CartManager.getCartItems()) // Update with current cart items
        cartAdapter.notifyDataSetChanged() // Notify adapter of changes
        updateSummary() // Refresh summary after item removal
    }
}
