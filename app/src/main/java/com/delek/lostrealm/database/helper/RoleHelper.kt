package com.delek.lostrealm.database.helper

import android.content.Context
import com.delek.lostrealm.R
import com.delek.lostrealm.database.dao.RoleDAO
import com.delek.lostrealm.database.model.Role

class RoleHelper {

        companion object {
            const val TABLE_NAME: String = "roles"
            const val COLUMN_ID: String = "id"
            const val COLUMN_NAME: String = "name"
            const val COLUMN_SYMBOL: String = "symbol"
            const val COLUMN_ICON: String = "icon"
            const val COLUMN_IMAGE: String = "image"
            const val COLUMN_DETAIL: String = "detail"
            const val COLUMN_WEIGHT: String = "weight"
            const val COLUMN_ADVANTAGES: String = "advantages"
            const val COLUMN_DEVELOPMENT: String = "development"
            const val COLUMN_POSITION: String = "position"
            const val COLUMN_SPELLS: String = "spells"
            const val COLUMN_RELATIONS: String = "relations"
            const val COLUMN_DIFFICULTY: String = "difficulty"

            val SQL_CREATE_ENTRIES = buildString {
                append("CREATE TABLE $TABLE_NAME (")
                append("$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,")
                append("$COLUMN_NAME TEXT,")
                append("$COLUMN_SYMBOL TEXT,")
                append("$COLUMN_ICON TEXT,")
                append("$COLUMN_IMAGE TEXT,")
                append("$COLUMN_DETAIL TEXT,")
                append("$COLUMN_WEIGHT TEXT,")
                append("$COLUMN_ADVANTAGES INTEGER,")
                append("$COLUMN_DEVELOPMENT INTEGER,")
                append("$COLUMN_POSITION INTEGER,")
                append("$COLUMN_SPELLS INTEGER,")
                append("$COLUMN_RELATIONS INTEGER,")
                append("$COLUMN_DIFFICULTY TEXT)")
            }

            const val SQL_DELETE_ENTRIES: String = "DROP TABLE IF EXISTS $TABLE_NAME"

            // Load resources from xml files to database
            fun loadCharacters(context: Context){
                val res = context.resources
                val name = res.getStringArray(R.array.roles_name)
                val symbol = res.getStringArray(R.array.roles_symbol)
                val icon = res.getStringArray(R.array.roles_icon)
                val image = res.getStringArray(R.array.roles_image)
                val detail = res.getStringArray(R.array.roles_detail)
                val weight = res.getStringArray(R.array.roles_weight)
                val spell = res.getStringArray(R.array.role_spells)
                val difficulty = res.getStringArray(R.array.roles_difficulty)

            for (i in name.indices){
                val role = Role(0, name[i], symbol[i], icon[i], image[i], detail[i], weight[i],
                    0,0,0, spell[i].toInt(),0,difficulty[i])
                RoleDAO(context).insertRole(role)
            }
        }
    }
}