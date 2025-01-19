package com.delek.lostrealm.database.helper

import android.content.Context
import com.delek.lostrealm.R
import com.delek.lostrealm.database.dao.RoleDAO
import com.delek.lostrealm.database.model.StartSpell

class StartSpellHelper {

    companion object {
        const val TABLE_NAME: String = "start_spell"
        const val COLUMN_ID: String = "id"
        const val COLUMN_ROLE_ID: String = "role_id"
        const val COLUMN_TYPE: String = "type"

        val SQL_CREATE_ENTRIES = buildString {
            append("CREATE TABLE $TABLE_NAME (")
            append("$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,")
            append("$COLUMN_ROLE_ID INTEGER,")
            append("$COLUMN_TYPE TEXT)")
        }

        const val SQL_DELETE_ENTRIES: String = "DROP TABLE IF EXISTS $TABLE_NAME"

        // Load resources from xml files to database
        fun loadAdvantages(context: Context) {
            val res = context.resources
            val roleId = res.getStringArray(R.array.start_role_id)
            val type = res.getStringArray(R.array.start_type)
            for (i in roleId.indices) {
                val startSpell = StartSpell(0, roleId[i].toInt(), type[i])
                RoleDAO(context).insertStartSpell(startSpell)
            }
        }
    }

}