package com.delek.lostrealm.database.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.delek.lostrealm.database.helper.ChitHelper
import com.delek.lostrealm.database.helper.DBHelper
import com.delek.lostrealm.database.model.Chit

class ChitDAO(context: Context): SQLiteOpenHelper(context,
    DBHelper.DATABASE_NAME, null,
    DBHelper.DATABASE_VERSION
) {
    override fun onCreate(p0: SQLiteDatabase?) { }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) { }

    fun insertChit(chit: Chit) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(ChitHelper.COLUMN_NAME, chit.name)
            put(ChitHelper.COLUMN_TYPE, chit.type)
            put(ChitHelper.COLUMN_SPEED, chit.speed)
            put(ChitHelper.COLUMN_EFFORT, chit.effort)
        }
        db.insert(ChitHelper.TABLE_NAME, null, values)
        db.close()
    }

    fun getChitsByDevelopment(id: Int): List<Chit> {
        val db = this.readableDatabase
        val chitList = mutableListOf<Chit>()
        val query = "SELECT chits.* FROM chits INNER JOIN development " +
                "ON chits.id = development.chit_id " +
                "WHERE development.role_id = $id"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            val chit = getColumns(cursor)
            chitList.add(chit)
        }
        cursor.close()
        db.close()
        return chitList
    }

    private fun getColumns(cursor: Cursor): Chit{
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(ChitHelper.COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(ChitHelper.COLUMN_NAME))
        val type = cursor.getString(cursor.getColumnIndexOrThrow(ChitHelper.COLUMN_TYPE))
        val speed = cursor.getInt(cursor.getColumnIndexOrThrow(ChitHelper.COLUMN_SPEED))
        val effort = cursor.getString(cursor.getColumnIndexOrThrow(ChitHelper.COLUMN_EFFORT))

        val chit = Chit(id, name, type, speed, effort)
        return chit

    }
}