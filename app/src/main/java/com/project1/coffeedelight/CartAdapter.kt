package com.project1.coffeedelight

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.storage.FirebaseStorage

class CartAdapter(
    private val context: Context,
    private val cartItemList: List<CartItem>,
    private val cartItemListener: () -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = cartItemList[position]
        val product = cartItem.product

        holder.textViewProductName.text = product.name
        holder.textViewProductPrice.text = "$${product.price}"
        holder.textViewProductQuantity.text = cartItem.quantity.toString()

        val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(product.imgUrl)
        storageReference.downloadUrl.addOnSuccessListener { uri ->
            // Load the image into the ImageView using Glide
            Glide.with(holder.itemView.context).load(uri).into(holder.imageViewProduct)

        }.addOnFailureListener {
            // Handling the error and throwing the exception
                exception -> Log.e("ProductAdapter", "Error occurred while loading image: ${exception.message}")
        }

        holder.buttonIncreaseQuantity.setOnClickListener {
            cartItem.quantity += 1
            notifyItemChanged(position)
            cartItemListener()
        }

        holder.buttonDecreaseQuantity.setOnClickListener {
            if (cartItem.quantity > 1) {
                cartItem.quantity -= 1
                notifyItemChanged(position)
                cartItemListener()
            }
        }

        holder.buttonRemoveItem.setOnClickListener {
            CartManager.removeItemFromCart(cartItem)
            notifyItemRemoved(position)
            cartItemListener()
        }
    }

    override fun getItemCount(): Int = cartItemList.size

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewProduct: ImageView = itemView.findViewById(R.id.imageViewProduct)
        val textViewProductName: TextView = itemView.findViewById(R.id.textViewProductName)
        val textViewProductPrice: TextView = itemView.findViewById(R.id.textViewProductPrice)
        val textViewProductQuantity: TextView = itemView.findViewById(R.id.textViewProductQuantity)
        val buttonIncreaseQuantity: Button = itemView.findViewById(R.id.buttonIncreaseQuantity)
        val buttonDecreaseQuantity: Button = itemView.findViewById(R.id.buttonDecreaseQuantity)
        val buttonRemoveItem: Button = itemView.findViewById(R.id.buttonRemoveItem)
    }
}
