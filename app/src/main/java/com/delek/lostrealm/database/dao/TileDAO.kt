package com.delek.lostrealm.database.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.delek.lostrealm.database.helper.DBHelper
import com.delek.lostrealm.database.helper.TileHelper
import com.delek.lostrealm.database.model.Tile

class TileDAO(context: Context) : SQLiteOpenHelper(
    context,
    DBHelper.DATABASE_NAME, null,
    DBHelper.DATABASE_VERSION

) {
    override fun onCreate(p0: SQLiteDatabase?) { }
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) { }

    fun insertTile(tile: Tile) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(TileHelper.COLUMN_NAME, tile.name)
            put(TileHelper.COLUMN_SHORT, tile.short)
            put(TileHelper.COLUMN_IMAGE, tile.image)
            put(TileHelper.COLUMN_IMAGE_ENCHANT, tile.imageEnchant)
            put(TileHelper.COLUMN_TYPE, tile.type)
        }
        db.insert(TileHelper.TABLE_NAME, null, values)
        db.close()
    }

}