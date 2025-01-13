package com.delek.lostrealm.database.helper

import android.content.Context
import com.delek.lostrealm.R
import com.delek.lostrealm.database.dao.AdvantageDAO
import com.delek.lostrealm.database.model.Advantage

class AdvantageHelper {

    companion object {
        const val TABLE_NAME: String = "advantages"
        const val COLUMN_ID: String = "id"
        const val COLUMN_NAME: String = "name"
        const val COLUMN_DESCRIPTION: String = "description"

        val SQL_CREATE_ENTRIES = buildString {
            append("CREATE TABLE $TABLE_NAME (")
            append("$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,")
            append("$COLUMN_NAME TEXT,")
            append("$COLUMN_DESCRIPTION TEXT)")
        }

        const val SQL_DELETE_ENTRIES: String = "DROP TABLE IF EXISTS $TABLE_NAME"

        // Load resources from xml files to database
        fun loadAdvantages(context: Context){
            val res = context.resources
            val name = res.getStringArray(R.array.advantages_name)
            val description = res.getStringArray(R.array.advantages_description)
            for (i in name.indices){
                val advantage = Advantage(0, name[i], description[i])
                AdvantageDAO(context).insertAdvantage(advantage)
            }
        }
    }

}