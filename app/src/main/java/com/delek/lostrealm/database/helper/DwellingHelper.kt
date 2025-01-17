package com.delek.lostrealm.database.helper

import android.content.Context
import com.delek.lostrealm.R
import com.delek.lostrealm.database.dao.DwellingDAO
import com.delek.lostrealm.database.model.Dwelling

class DwellingHelper {

    companion object {
        const val TABLE_NAME: String = "dwellings"
        const val COLUMN_ID: String = "id"
        const val COLUMN_NAME: String = "name"
        const val COLUMN_IMAGE: String = "image"
        const val COLUMN_TILE: String = "tile"
        const val COLUMN_CLEARING: String = "clearing"

        val SQL_CREATE_ENTRIES = buildString {
            append("CREATE TABLE $TABLE_NAME (")
            append("$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,")
            append("$COLUMN_NAME TEXT,")
            append("$COLUMN_IMAGE TEXT,")
            append("$COLUMN_TILE TEXT,")
            append("$COLUMN_CLEARING TEXT)")
        }

        const val SQL_DELETE_ENTRIES: String = "DROP TABLE IF EXISTS $TABLE_NAME"

        // Load resources from xml files to database
        fun loadDwelling(context: Context){
            val res = context.resources
            val name = res.getStringArray(R.array.dwelling_names)
            val image = res.getStringArray(R.array.dwelling_image)
            val tile = res.getStringArray(R.array.dwelling_tile)
            val clearing = res.getStringArray(R.array.dwelling_clearing)
            for (i in name.indices){
                val dwelling = Dwelling(0, name[i], image[i], "", 0)
                DwellingDAO(context).insertDwelling(dwelling)
            }
        }
    }

}