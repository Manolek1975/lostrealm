package com.delek.lostrealm.database.model

data class Map(
    val id: Int,
    val tileId: Int,
    val posX: Int,
    val posY: Int,
    val rotate: Float,
    val enchant: Int
)
