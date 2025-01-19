package com.delek.lostrealm.database.model

data class Spell(
    val id: Int,
    val name: String,
    val type: String,
    val color: String,
    val target: String,
    val duration: String,
    val description: String,
    val shortDescription: String
)