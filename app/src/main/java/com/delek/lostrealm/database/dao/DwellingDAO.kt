package com.delek.lostrealm.database.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.delek.lostrealm.database.helper.ChitHelper
import com.delek.lostrealm.database.helper.DBHelper
import com.delek.lostrealm.database.helper.DwellingHelper
import com.delek.lostrealm.database.helper.RoleDwellingHelper
import com.delek.lostrealm.database.model.Dwelling
import com.delek.lostrealm.database.model.RoleDwelling

class DwellingDAO(context: Context): SQLiteOpenHelper(context,
    DBHelper.DATABASE_NAME, null,
    DBHelper.DATABASE_VERSION
) {
    override fun onCreate(p0: SQLiteDatabase?) { }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) { }

    fun insertDwelling(dwelling: Dwelling) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(DwellingHelper.COLUMN_NAME, dwelling.name)
            put(DwellingHelper.COLUMN_IMAGE, dwelling.image)
            put(DwellingHelper.COLUMN_TILE, dwelling.tile)
            put(DwellingHelper.COLUMN_CLEARING, dwelling.clearing)
        }
        db.insert(DwellingHelper.TABLE_NAME, null, values)
        db.close()
    }

    fun insertRoleStartDwellings(roleDwelling: RoleDwelling) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(RoleDwellingHelper.COLUMN_ROLE_ID, roleDwelling.roleId)
            put(RoleDwellingHelper.COLUMN_DWELLING_ID, roleDwelling.dwellingId)
        }
        db.insert(RoleDwellingHelper.TABLE_NAME, null, values)
        db.close()
    }

    fun getRoleDwellings(id: Int): List<Dwelling> {
        val db = this.readableDatabase
        val dwellingList = mutableListOf<Dwelling>()
        val query = "SELECT dwellings.* FROM dwellings INNER JOIN role_dwellings " +
                "ON dwellings.id = role_dwellings.dwelling_id " +
                "WHERE role_dwellings.role_id = $id"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            val dwelling = getColumns(cursor)
            dwellingList.add(dwelling)
        }
        cursor.close()
        db.close()
        return dwellingList
    }

    private fun getColumns(cursor: Cursor): Dwelling {
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(ChitHelper.COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(DwellingHelper.COLUMN_NAME))
        val image = cursor.getString(cursor.getColumnIndexOrThrow(DwellingHelper.COLUMN_IMAGE))
        val tile = cursor.getString(cursor.getColumnIndexOrThrow(DwellingHelper.COLUMN_TILE))
        val clearing = cursor.getString(cursor.getColumnIndexOrThrow(DwellingHelper.COLUMN_CLEARING))

        val dwelling = Dwelling(id, name, image, tile, clearing.toInt())
        return dwelling

    }

}