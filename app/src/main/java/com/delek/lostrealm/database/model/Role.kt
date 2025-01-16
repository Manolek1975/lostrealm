package com.delek.lostrealm.database.model

import java.io.Serializable

data class Role(
    val id: Int,
    val name: String,
    val symbol: String,
    val icon: String,
    val image: String,
    val detail: String,
    val weight: String,
    val advantages: Int,
    val development: Int,
    val position: Int,
    val relations: Int,
    val difficulty: Int
): Serializable