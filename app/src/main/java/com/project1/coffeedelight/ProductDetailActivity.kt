package com.project1.coffeedelight

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage

class ProductDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_product_detail)

        val imageViewProductDetail = findViewById<ImageView>(R.id.imageViewProductDetail)
        val textViewProductNameDetail = findViewById<TextView>(R.id.textViewProductNameDetail)
        val textViewProductDescriptionDetail = findViewById<TextView>(R.id.textViewProductDescriptionDetail)
        val textViewProductPriceDetail = findViewById<TextView>(R.id.textViewProductPriceDetail)
        val textViewQuantity = findViewById<TextView>(R.id.textViewQuantity)
        val buttonIncreaseQuantity = findViewById<Button>(R.id.buttonIncreaseQuantity)
        val buttonDecreaseQuantity = findViewById<Button>(R.id.buttonDecreaseQuantity)
        val buttonAddToCart = findViewById<Button>(R.id.buttonAddToCart)
        val buttonGoToCart = findViewById<Button>(R.id.buttonGoToCart)

        // Retrieve data from intent
        val name = intent.getStringExtra("name")
        val descriptionDetail = intent.getStringExtra("descriptionDetail")
        val price = intent.getDoubleExtra("price", 0.0)
        val imgUrl = intent.getStringExtra("imgUrl")

        // Set data to views
        textViewProductNameDetail.text = name
        textViewProductDescriptionDetail.text = descriptionDetail
        textViewProductPriceDetail.text = "$${price}"

        // Load the image using Glide or similar library
        if (imgUrl != null) {
            val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(imgUrl)
            storageReference.downloadUrl.addOnSuccessListener { uri ->
                Glide.with(this)
                    .load(uri)
                    .into(imageViewProductDetail)
            }.addOnFailureListener { exception ->
                Log.e("ProductDetailActivity", "Error loading image: ${exception.message}")
                Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show()
            }
        } else {
            Log.e("ProductDetailActivity", "Image URL is null")
        }

        // Handle quantity changes
        var quantity = 1
        textViewQuantity.text = quantity.toString()

        buttonIncreaseQuantity.setOnClickListener {
            quantity++
            textViewQuantity.text = quantity.toString()
        }

        buttonDecreaseQuantity.setOnClickListener {
            if (quantity > 1) {
                quantity--
                textViewQuantity.text = quantity.toString()
            }
        }

        // Handle Add to Cart and Go to Cart actions
        buttonAddToCart.setOnClickListener {
            // Code to add the item with quantity to cart
            // Create a Product object
            // Create a Product object with non-null values
            val product = Product(
                name ?: "Unknown Product", // Provide a default name if null
                descriptionDetail ?: "",    // Default to an empty string if null
                price,                      // Price can remain as is since it's a Double
                imgUrl ?: "",               // Default to an empty string if null
                descriptionDetail ?: ""      // You may want to use a different default for descriptionDetail
            )

            // Add the Product to the cart with the specified quantity
            CartManager.addItemToCart(product, quantity)
            Toast.makeText(this, "$name added to cart", Toast.LENGTH_SHORT).show()
        }

        buttonGoToCart.setOnClickListener {
            // Code to navigate to the CartActivity
            val cartIntent = Intent(this, CartActivity::class.java)
            startActivity(cartIntent)
        }
    }
}