package com.delek.lostrealm.database.helper

import android.content.Context
import com.delek.lostrealm.R
import com.delek.lostrealm.database.dao.TileDAO
import com.delek.lostrealm.database.model.Tile

class TileHelper {

    companion object {
        const val TABLE_NAME: String = "tiles"
        const val COLUMN_ID: String = "id"
        const val COLUMN_NAME: String = "name"
        const val COLUMN_SHORT: String = "short"
        const val COLUMN_IMAGE: String = "image"
        const val COLUMN_IMAGE_ENCHANT: String = "image_enchant"
        const val COLUMN_TYPE: String = "type"

        val SQL_CREATE_ENTRIES = buildString {
            append("CREATE TABLE $TABLE_NAME (")
            append("$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,")
            append("$COLUMN_NAME TEXT,")
            append("$COLUMN_SHORT TEXT,")
            append("$COLUMN_IMAGE TEXT,")
            append("$COLUMN_IMAGE_ENCHANT TEXT,")
            append("$COLUMN_TYPE TEXT)")
        }

        const val SQL_DELETE_ENTRIES: String = "DROP TABLE IF EXISTS $TABLE_NAME"

        // Load resources from xml files to database
        fun loadTile(context: Context) {
            val res = context.resources
            val name = res.getStringArray(R.array.tile_names)
            val short = res.getStringArray(R.array.tile_short)
            val image = res.getStringArray(R.array.tile_image)
            val imageEnchant = res.getStringArray(R.array.tile_image_enchant)
            val type = res.getStringArray(R.array.tile_type)

            for (i in name.indices) {
                val tile = Tile(0, name[i], short[i], image[i], imageEnchant[i], type[i])
                TileDAO(context).insertTile(tile)
            }
        }

    }
}