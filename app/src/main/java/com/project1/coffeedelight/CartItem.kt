package com.project1.coffeedelight

import java.io.Serializable

data class CartItem(
    var product: Product,
    var quantity: Int
) : Serializable