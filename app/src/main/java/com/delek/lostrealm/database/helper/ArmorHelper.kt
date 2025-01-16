package com.delek.lostrealm.database.helper

import android.content.Context
import com.delek.lostrealm.R
import com.delek.lostrealm.database.dao.ArmorDAO
import com.delek.lostrealm.database.model.Armor

class ArmorHelper {

    companion object {
        const val TABLE_NAME: String = "armor"
        const val COLUMN_ID: String = "id"
        const val COLUMN_NAME: String = "name"
        const val COLUMN_IMAGE: String = "image"
        const val COLUMN_DEFENSE: String = "defense"
        const val COLUMN_PRICE: String = "price"
        const val COLUMN_PRICE_DAMAGED: String = "price_damaged"

        val SQL_CREATE_ENTRIES = buildString {
            append("CREATE TABLE $TABLE_NAME (")
            append("$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,")
            append("$COLUMN_NAME TEXT,")
            append("$COLUMN_IMAGE TEXT,")
            append("$COLUMN_DEFENSE TEXT,")
            append("$COLUMN_PRICE TEXT,")
            append("$COLUMN_PRICE_DAMAGED INTEGER)")
        }

        const val SQL_DELETE_ENTRIES: String = "DROP TABLE IF EXISTS $TABLE_NAME"

        // Load resources from xml files to database
        fun loadArmor(context: Context) {
            val res = context.resources
            val name = res.getStringArray(R.array.armor_names)
            val image = res.getStringArray(R.array.armor_images)
            val defense = res.getStringArray(R.array.armor_defense)
            val price = res.getStringArray(R.array.armor_prices)
            val priceDamaged = res.getStringArray(R.array.armor_prices_damaged)

            for (i in name.indices) {
                val armor = Armor(
                    0, name[i], image[i], defense[i], price[i].toInt(), priceDamaged[i].toInt()
                )
                ArmorDAO(context).insertArmor(armor)
            }
        }
    }

}