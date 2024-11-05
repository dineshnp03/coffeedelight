package com.project1.coffeedelight

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ThanksActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thanks)

        val orderAgainBtn = findViewById<Button>(R.id.order_again_button)

        orderAgainBtn.setOnClickListener {
            startActivity(Intent(this, ProductsActivity::class.java))
        }
    }
}