package com.project1.coffeedelight

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage

class ProductAdapter(private val productList: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productName: TextView = itemView.findViewById(R.id.txtProductName)
        val productDescription: TextView = itemView.findViewById(R.id.txtProductDescription)
        val productPrice: TextView = itemView.findViewById(R.id.txtProductPrice)
        val productImage: ImageView = itemView.findViewById(R.id.imgProduct)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product_card, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.productName.text = product.name
        holder.productDescription.text = product.description
        holder.productPrice.text = "$${product.price}"
        val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(product.imgUrl)
        storageReference.downloadUrl.addOnSuccessListener { uri ->
            // Load the image into the ImageView using Glide
            Glide.with(holder.itemView.context).load(uri).into(holder.productImage)

        }.addOnFailureListener {
            // Handle error: set a default image or error image
            exception -> Log.e("ProductAdapter", "Error occurred while loading image: ${exception.message}")
        }
    }

    override fun getItemCount() = productList.size
}
