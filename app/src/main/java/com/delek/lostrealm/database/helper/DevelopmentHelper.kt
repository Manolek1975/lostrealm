package com.delek.lostrealm.database.helper

import android.content.Context
import com.delek.lostrealm.R
import com.delek.lostrealm.database.dao.DevelopmentDAO
import com.delek.lostrealm.database.model.Development

class DevelopmentHelper {

    companion object {
        const val TABLE_NAME: String = "development"
        const val COLUMN_ID: String = "id"
        const val COLUMN_NAME: String = "name"
        const val COLUMN_LEVEL: String = "level"
        const val COLUMN_ROLE_ID: String = "role_id"
        const val COLUMN_CHIT_ID: String = "chit_id"
        const val COLUMN_WEAPON_ID: String = "weapon_id"
        const val COLUMN_ARMOR_ID: String = "armor_id"


        val SQL_CREATE_ENTRIES = buildString {
            append("CREATE TABLE $TABLE_NAME (")
            append("$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,")
            append("$COLUMN_NAME TEXT,")
            append("$COLUMN_LEVEL INTEGER,")
            append("$COLUMN_ROLE_ID INTEGER,")
            append("$COLUMN_CHIT_ID INTEGER,")
            append("$COLUMN_WEAPON_ID INTEGER,")
            append("$COLUMN_ARMOR_ID INTEGER)")
        }

        const val SQL_DELETE_ENTRIES: String = "DROP TABLE IF EXISTS $TABLE_NAME"

        // Load resources from xml files to database
        fun loadDeveloments(context: Context) {
            val res = context.resources
            val name = res.getStringArray(R.array.development_names)
            val level = res.getStringArray(R.array.development_levels)
            val roleId = res.getStringArray(R.array.development_roles)
            val chitId = res.getStringArray(R.array.development_chits)
            val weaponId = res.getStringArray(R.array.development_weapons)
            val armorID = res.getStringArray(R.array.development_armor)

            for (i in name.indices) {
                val development =
                    Development(0, name[i], level[i].toInt(), roleId[i].toInt(),
                        chitId[i].toInt(), weaponId[i].toInt(), armorID[i].toInt())
                DevelopmentDAO(context).insertDeveloment(development)
            }
        }
    }

}