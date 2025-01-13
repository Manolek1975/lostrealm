package com.delek.lostrealm.database.helper

import android.content.Context
import com.delek.lostrealm.R
import com.delek.lostrealm.database.dao.AdvantageDAO
import com.delek.lostrealm.database.model.RoleAdvantage

class RoleAdvantageHelper {

    companion object {
        const val TABLE_NAME: String = "role_advantages"
        const val COLUMN_ID: String = "id"
        const val COLUMN_ROLE_ID: String = "role_id"
        const val COLUMN_ADVANTAGE_ID: String = "advantage_id"

        val SQL_CREATE_ENTRIES = buildString {
            append("CREATE TABLE $TABLE_NAME (")
            append("$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,")
            append("$COLUMN_ROLE_ID INTEGER,")
            append("$COLUMN_ADVANTAGE_ID INTEGER)")
        }

        const val SQL_DELETE_ENTRIES: String = "DROP TABLE IF EXISTS $TABLE_NAME"

        // Load resources from xml files to database
        fun loadRoleAdvantages(context: Context){
            val res = context.resources
            val adv = res.getStringArray(R.array.roles_advantages)
            for (i in adv.indices){
                val advantage = RoleAdvantage(0, (i%16)+1, adv[i].toInt())
                AdvantageDAO(context).insertRoleAdvantage(advantage)
            }
        }
    }

}