package com.delek.lostrealm.database.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.delek.lostrealm.database.helper.DBHelper
import com.delek.lostrealm.database.helper.MapHelper
import com.delek.lostrealm.database.helper.TileHelper
import com.delek.lostrealm.database.model.Map
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

    fun insertMap(map: Map) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(MapHelper.COLUMN_TILE_ID, map.tileId)
            put(MapHelper.COLUMN_POS_X, map.posX)
            put(MapHelper.COLUMN_POS_Y, map.posY)
            put(MapHelper.COLUMN_ROTATE, map.rotate)
            put(MapHelper.COLUMN_ENCHANT, map.enchant)
        }
        db.insert(MapHelper.TABLE_NAME, null, values)
        db.close()
    }


    private fun getColumns(cursor: Cursor): Tile {
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(TileHelper.COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(TileHelper.COLUMN_NAME))
        val short = cursor.getString(cursor.getColumnIndexOrThrow(TileHelper.COLUMN_SHORT))
        val image = cursor.getString(cursor.getColumnIndexOrThrow(TileHelper.COLUMN_IMAGE))
        val imageEnchant = cursor.getString(cursor.getColumnIndexOrThrow(TileHelper.COLUMN_IMAGE_ENCHANT))
        val type = cursor.getString(cursor.getColumnIndexOrThrow(TileHelper.COLUMN_TYPE))
        val tile = Tile(id, name, short, image, imageEnchant, type)
        return tile
    }

    fun getAllTiles(): List<Tile> {
        val db = this.readableDatabase
        val query = "SELECT * FROM tiles"
        val cursor = db.rawQuery(query, null)
        val tileList = mutableListOf<Tile>()
        while (cursor.moveToNext()) {
            val tile = getColumns(cursor)
            tileList.add(tile)
        }
        cursor.close()
        db.close()
        return tileList
    }

}