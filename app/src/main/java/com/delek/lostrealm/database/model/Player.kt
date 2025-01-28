package com.delek.lostrealm.database.model

data class Player(
    val id: Int,
    val name: String,
    val roleId: Int,
    val armor: Int,
    val weapons: Int,
    val treasures: Int,
    val spells: Int,
    val gold: Int,
    val position: Int
)
