package com.delek.lostrealm.database.helper

class MapHelper {

    companion object {
        const val TABLE_NAME: String = "map"
        const val COLUMN_ID: String = "id"
        const val COLUMN_TILE_ID: String = "tile_id"
        const val COLUMN_POS_X: String = "pos_x"
        const val COLUMN_POS_Y: String = "pos_y"
        const val COLUMN_ROTATE: String = "rotate"
        const val COLUMN_ENCHANT: String = "enchant"


        val SQL_CREATE_ENTRIES = buildString {
            append("CREATE TABLE $TABLE_NAME (")
            append("$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,")
            append("$COLUMN_TILE_ID INTEGER,")
            append("$COLUMN_POS_X INTEGER,")
            append("$COLUMN_POS_Y INTEGER,")
            append("$COLUMN_ROTATE INTEGER,")
            append("$COLUMN_ENCHANT INTEGER)")
        }

        const val SQL_DELETE_ENTRIES: String = "DROP TABLE IF EXISTS $TABLE_NAME"

    }
}