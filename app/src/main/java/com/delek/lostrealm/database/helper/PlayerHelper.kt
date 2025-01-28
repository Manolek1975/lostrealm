package com.delek.lostrealm.database.helper

class PlayerHelper {

    companion object {
        const val TABLE_NAME: String = "players"
        const val COLUMN_ID: String = "id"
        const val COLUMN_NAME: String = "name"
        const val COLUMN_ROLE_ID: String = "role_id"
        const val COLUMN_ARMOR: String = "armor"
        const val COLUMN_WEAPONS: String = "weapons"
        const val COLUMN_TREASURES: String = "treasures"
        const val COLUMN_SPELLS: String = "spells"
        const val COLUMN_GOLD: String = "gold"
        const val COLUMN_POSITION: String = "position"

        val SQL_CREATE_ENTRIES = buildString {
            append("CREATE TABLE $TABLE_NAME (")
            append("$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,")
            append("$COLUMN_NAME TEXT,")
            append("$COLUMN_ROLE_ID INTEGER,")
            append("$COLUMN_ARMOR INTEGER,")
            append("$COLUMN_WEAPONS INTEGER,")
            append("$COLUMN_TREASURES INTEGER,")
            append("$COLUMN_SPELLS INTEGER,")
            append("$COLUMN_GOLD INTEGER,")
            append("$COLUMN_POSITION INTEGER)")
        }

        const val SQL_DELETE_ENTRIES: String = "DROP TABLE IF EXISTS $TABLE_NAME"


    }
}