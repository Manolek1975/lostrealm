package com.delek.lostrealm.database.model

data class Role(
    val id: Int,
    val name: String,
    val symbol: String,
    val image: String,
    val icon: String,
    val weight: String,
    val advantages: Int,
    val development: Int,
    val position: Int,
    val relations: Int,
    val difficulty: Int
)