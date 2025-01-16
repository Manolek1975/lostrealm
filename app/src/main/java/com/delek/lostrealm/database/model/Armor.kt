package com.delek.lostrealm.database.model

data class Armor(
    val id: Int,
    val name: String,
    val image: String,
    val defense: String,
    val price: Int,
    val priceDamaged: Int
)