package com.delek.lostrealm.database.model

data class Weapon(
    val id: Int,
    val name: String,
    val image: String,
    val alert: String,
    val damage: String,
    val plus: String,
    val plusAlert: String,
    val type: String,
    val length: Int,
    val speed: Int,
    val speedAlert: Int,
    val price: Int
) {
}