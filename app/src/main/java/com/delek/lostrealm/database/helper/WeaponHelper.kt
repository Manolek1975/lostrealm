package com.delek.lostrealm.database.helper

import android.content.Context
import com.delek.lostrealm.R
import com.delek.lostrealm.database.dao.WeaponDAO
import com.delek.lostrealm.database.model.Weapon

class WeaponHelper {

    companion object {
        const val TABLE_NAME: String = "weapons"
        const val COLUMN_ID: String = "id"
        const val COLUMN_NAME: String = "name"
        const val COLUMN_IMAGE: String = "image"
        const val COLUMN_ALERT: String = "alert"
        const val COLUMN_DAMAGE: String = "damage"
        const val COLUMN_PLUS: String = "plus"
        const val COLUMN_PLUS_ALERT: String = "plus_alert"
        const val COLUMN_TYPE: String = "type"
        const val COLUMN_LENGTH: String = "length"
        const val COLUMN_SPEED: String = "speed"
        const val COLUMN_SPEED_ALERT: String = "speed_alert"
        const val COLUMN_PRICE: String = "price"

        val SQL_CREATE_ENTRIES = buildString {
            append("CREATE TABLE $TABLE_NAME (")
            append("$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,")
            append("$COLUMN_NAME TEXT,")
            append("$COLUMN_IMAGE TEXT,")
            append("$COLUMN_ALERT TEXT,")
            append("$COLUMN_DAMAGE TEXT,")
            append("$COLUMN_PLUS TEXT,")
            append("$COLUMN_PLUS_ALERT TEXT,")
            append("$COLUMN_TYPE TEXT,")
            append("$COLUMN_LENGTH INTEGER,")
            append("$COLUMN_SPEED INTEGER,")
            append("$COLUMN_SPEED_ALERT INTEGER,")
            append("$COLUMN_PRICE INTEGER)")
        }

        const val SQL_DELETE_ENTRIES: String = "DROP TABLE IF EXISTS $TABLE_NAME"

        // Load resources from xml files to database
        fun loadWeapons(context: Context) {
            val res = context.resources
            val name = res.getStringArray(R.array.weapon_names)
            val image = res.getStringArray(R.array.weapon_images)
            val alert = res.getStringArray(R.array.weapon_alert)
            val damage = res.getStringArray(R.array.weapon_damage)
            val plus = res.getStringArray(R.array.weapon_plus)
            val plusAlert = res.getStringArray(R.array.weapon_plus_alert)
            val type = res.getStringArray(R.array.weapon_types)
            val length = res.getStringArray(R.array.weapons_length)
            val speed = res.getStringArray(R.array.weapon_speed)
            val speedAlert = res.getStringArray(R.array.weapon_speed_alert)
            val price = res.getStringArray(R.array.weapon_prices)

            for (i in name.indices) {
                val weapon = Weapon(
                    0, name[i], image[i], alert[i], damage[i], plus[i], plusAlert[i], type[i],
                    length[i].toInt(), speed[i].toInt(), speedAlert[i].toInt(), price[i].toInt())
                WeaponDAO(context).insertWeapon(weapon)
            }
        }
    }

}