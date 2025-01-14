package com.delek.lostrealm.database.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.delek.lostrealm.database.helper.ChitHelper
import com.delek.lostrealm.database.helper.DBHelper
import com.delek.lostrealm.database.helper.WeightHelper
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
            put(ChitHelper.COLUMN_TYPE, chit.type)
            put(ChitHelper.COLUMN_WEIGHT, chit.weight)
            put(ChitHelper.COLUMN_SPEED, chit.speed)
            put(ChitHelper.COLUMN_EFFORT, chit.effort)
        }
        db.insert(ChitHelper.TABLE_NAME, null, values)
        db.close()
    }

    fun getName1(): String{
        val db = this.readableDatabase
        val query = "SELECT * FROM weight WHERE id=1"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()
        val name = cursor.getString(cursor.getColumnIndexOrThrow(WeightHelper.COLUMN_NAME))
        cursor.close()
        db.close()
        return name
    }
}