package com.delek.lostrealm.database.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.delek.lostrealm.database.helper.DBHelper
import com.delek.lostrealm.database.helper.DevelopmentHelper
import com.delek.lostrealm.database.model.Development

class DevelopmentDAO(context: Context): SQLiteOpenHelper(context,
    DBHelper.DATABASE_NAME, null,
    DBHelper.DATABASE_VERSION
) {
    override fun onCreate(p0: SQLiteDatabase?) { }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) { }

    fun insertDeveloment(dev: Development) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(DevelopmentHelper.COLUMN_NAME, dev.name)
            put(DevelopmentHelper.COLUMN_LEVEL, dev.level)
            put(DevelopmentHelper.COLUMN_ROLE_ID, dev.roleId)
            put(DevelopmentHelper.COLUMN_CHIT_ID, dev.chitId)
        }
        db.insert(DevelopmentHelper.TABLE_NAME, null, values)
        db.close()
    }

    fun getDevelopmentByRole(id: Int): List<Development> {
        val db = this.readableDatabase
        val devList = mutableListOf<Development>()
        val query = "SELECT * FROM development WHERE role_id = $id"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            val dev = getColumns(cursor)
            devList.add(dev)
        }
        cursor.close()
        db.close()
        return devList
    }

    private fun getColumns(cursor: Cursor): Development {
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(DevelopmentHelper.COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(DevelopmentHelper.COLUMN_NAME))
        val level = cursor.getInt(cursor.getColumnIndexOrThrow(DevelopmentHelper.COLUMN_LEVEL))
        val roleId = cursor.getInt(cursor.getColumnIndexOrThrow(DevelopmentHelper.COLUMN_ROLE_ID))
        val chitId = cursor.getInt(cursor.getColumnIndexOrThrow(DevelopmentHelper.COLUMN_CHIT_ID))

        val dev = Development(id, name, level, roleId, chitId)
        return dev
    }
}