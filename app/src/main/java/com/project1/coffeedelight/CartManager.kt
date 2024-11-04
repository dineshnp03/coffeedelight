package com.project1.coffeedelight

object CartManager {
    private val cartItemList = mutableListOf<CartItem>()

    fun addItemToCart(product: Product, quantity: Int) {
        // Check if the product already exists in the cart
        val existingCartItem = cartItemList.find { it.product.name == product.name }
        if (existingCartItem != null) {
            // Update quantity if product exists
            existingCartItem.quantity += quantity
        } else {
            // Add new item if product is not in the cart
            cartItemList.add(CartItem(product, quantity))
        }
    }

    fun getCartItems(): List<CartItem> = cartItemList.toList()

    fun removeItemFromCart(cartItem: CartItem) {
        cartItemList.remove(cartItem)
    }

    fun clearCart() {
        cartItemList.clear()
    }

    fun getTotalPrice(): Double = cartItemList.sumOf { it.product.price * it.quantity }

    fun getTax(taxRate: Double): Double = getTotalPrice() * taxRate

    fun getFinalBill(taxRate: Double): Double = getTotalPrice() + getTax(taxRate)
}