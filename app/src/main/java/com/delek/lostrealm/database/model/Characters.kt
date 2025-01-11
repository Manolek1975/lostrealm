package com.delek.lostrealm.database.model

data class Characters(
    val id: Int,
    val name: String,
    val symbol: String,
    val icon: String,
    val weight: Int,
    val advantages: Int,
    val development: Int,
    val position: Int,
    val relations: Int,
    val difficulty: Int
)