package com.delek.lostrealm.database.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.delek.lostrealm.database.helper.DBHelper
import com.delek.lostrealm.database.helper.MapHelper
import com.delek.lostrealm.database.model.Map

class MapDAO(context: Context) : SQLiteOpenHelper(
    context,
    DBHelper.DATABASE_NAME, null,
    DBHelper.DATABASE_VERSION

) {
    override fun onCreate(p0: SQLiteDatabase?) { }
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) { }

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

    private fun getColumns(cursor: Cursor): Map {
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(MapHelper.COLUMN_ID))
        val tileId = cursor.getInt(cursor.getColumnIndexOrThrow(MapHelper.COLUMN_TILE_ID))
        val posX = cursor.getInt(cursor.getColumnIndexOrThrow(MapHelper.COLUMN_POS_X))
        val posY = cursor.getInt(cursor.getColumnIndexOrThrow(MapHelper.COLUMN_POS_Y))
        val rotate = cursor.getInt(cursor.getColumnIndexOrThrow(MapHelper.COLUMN_ROTATE))
        val enchant = cursor.getInt(cursor.getColumnIndexOrThrow(MapHelper.COLUMN_ENCHANT))
        val map = Map(id, tileId, posX, posY, rotate, enchant)
        return map
    }

    fun getAllMap(): List<Map> {
        val db = this.readableDatabase
        val query = "SELECT * FROM map"
        val cursor = db.rawQuery(query, null)
        val mapList = mutableListOf<Map>()
        while (cursor.moveToNext()) {
            val map = getColumns(cursor)
            mapList.add(map)
        }
        cursor.close()
        db.close()
        return mapList
    }

}