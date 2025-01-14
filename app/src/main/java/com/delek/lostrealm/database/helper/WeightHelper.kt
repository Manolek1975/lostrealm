package com.delek.lostrealm.database.helper

import android.content.Context
import com.delek.lostrealm.R
import com.delek.lostrealm.database.dao.WeightDAO
import com.delek.lostrealm.database.model.Weight

class WeightHelper {

    companion object {
        const val TABLE_NAME: String = "weight"
        const val COLUMN_ID: String = "id"
        const val COLUMN_NAME: String = "name"

        val SQL_CREATE_ENTRIES = buildString {
            append("CREATE TABLE $TABLE_NAME (")
            append("$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,")
            append("$COLUMN_NAME TEXT)")
        }

        const val SQL_DELETE_ENTRIES: String = "DROP TABLE IF EXISTS $TABLE_NAME"

        // Load resources from xml files to database
        fun loadWeight(context: Context){
            val res = context.resources
            val name = res.getStringArray(R.array.weight)
            for (i in name.indices){
                val weight = Weight(0, name[i])
                WeightDAO(context).insertWeight(weight)
            }
        }
    }

}