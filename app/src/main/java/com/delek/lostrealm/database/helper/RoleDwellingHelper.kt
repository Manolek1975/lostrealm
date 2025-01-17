package com.delek.lostrealm.database.helper

import android.content.Context
import com.delek.lostrealm.R
import com.delek.lostrealm.database.dao.DwellingDAO
import com.delek.lostrealm.database.model.RoleDwelling

class RoleDwellingHelper {

    companion object {
        const val TABLE_NAME: String = "role_dwellings"
        const val COLUMN_ID: String = "id"
        const val COLUMN_ROLE_ID: String = "role_id"
        const val COLUMN_DWELLING_ID: String = "dwelling_id"

        val SQL_CREATE_ENTRIES = buildString {
            append("CREATE TABLE $TABLE_NAME (")
            append("$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,")
            append("$COLUMN_ROLE_ID INTEGER,")
            append("$COLUMN_DWELLING_ID INTEGER)")
        }

        const val SQL_DELETE_ENTRIES: String = "DROP TABLE IF EXISTS $TABLE_NAME"

        // Load resources from xml files to database
        fun loadRoleDwellings(context: Context){
            val res = context.resources
            val roleId = res.getStringArray(R.array.dwelling_roles_id)
            val dwellingId = res.getStringArray(R.array.dwelling_start)
            for (i in roleId.indices){
                val dwelling = RoleDwelling(0, roleId[i].toInt(), dwellingId[i].toInt())
                DwellingDAO(context).insertRoleStartDwellings(dwelling)
            }
        }
    }

}