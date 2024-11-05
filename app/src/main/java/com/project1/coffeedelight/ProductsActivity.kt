package com.project1.coffeedelight

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProductsActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var productAdapter: ProductAdapter
    private lateinit var productList: ArrayList<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        productList = ArrayList()


        database = FirebaseDatabase.getInstance().getReference("Products")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                productList.clear()
                for (productSnapshot in snapshot.children) {
                    val product = productSnapshot.getValue(Product::class.java)
                    if (product != null) {
                        productList.add(product)
                    }
                }
                productAdapter = ProductAdapter(productList,this@ProductsActivity)
                recyclerView.adapter = productAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ProductsActivity, "Failed to load products", Toast.LENGTH_SHORT).show()
            }
        })
        val logoutButton: ImageButton = findViewById(R.id.img_logout)
        val goToCartButton: ImageButton = findViewById(R.id.img_goto_cart)
        // Logout Function
        logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            // Redirect to login activity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        // Go to Cart function
        goToCartButton.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
    }
}