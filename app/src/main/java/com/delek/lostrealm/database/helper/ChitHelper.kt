package com.delek.lostrealm.database.helper

import android.content.Context
import com.delek.lostrealm.R
import com.delek.lostrealm.database.dao.ChitDAO
import com.delek.lostrealm.database.model.Chit

class ChitHelper {

    companion object {
        const val TABLE_NAME: String = "chits"
        const val COLUMN_ID: String = "id"
        const val COLUMN_NAME: String = "name"
        const val COLUMN_TYPE: String = "type"
        const val COLUMN_SPEED: String = "speed"
        const val COLUMN_EFFORT: String = "effort"

        val SQL_CREATE_ENTRIES = buildString {
            append("CREATE TABLE $TABLE_NAME (")
            append("$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,")
            append("$COLUMN_NAME TEXT,")
            append("$COLUMN_TYPE TEXT,")
            append("$COLUMN_SPEED TEXT,")
            append("$COLUMN_EFFORT TEXT)")
        }

        const val SQL_DELETE_ENTRIES: String = "DROP TABLE IF EXISTS $TABLE_NAME"

        // Load resources from xml files to database
        fun loadChits(context: Context) {
            val res = context.resources
            val name = res.getStringArray(R.array.chit_names)
            val type = res.getStringArray(R.array.chit_types)
            val speed = res.getStringArray(R.array.chit_speed)
            val effort = res.getStringArray(R.array.chit_effort)
            for (i in type.indices) {
                val chit = Chit(0, name[i], type[i], speed[i], effort[i])
                ChitDAO(context).insertChit(chit)
            }
        }
    }

}