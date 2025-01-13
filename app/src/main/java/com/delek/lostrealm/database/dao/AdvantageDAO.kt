package com.delek.lostrealm.database.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.delek.lostrealm.database.helper.AdvantageHelper
import com.delek.lostrealm.database.helper.DBHelper
import com.delek.lostrealm.database.helper.RoleAdvantageHelper
import com.delek.lostrealm.database.model.Advantage
import com.delek.lostrealm.database.model.RoleAdvantage

class AdvantageDAO(context: Context): SQLiteOpenHelper(context,
    DBHelper.DATABASE_NAME, null,
    DBHelper.DATABASE_VERSION
) {
    override fun onCreate(p0: SQLiteDatabase?) { }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) { }

    fun insertAdvantage(advantage: Advantage) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(AdvantageHelper.COLUMN_NAME, advantage.name)
            put(AdvantageHelper.COLUMN_DESCRIPTION, advantage.description)
        }
        db.insert(AdvantageHelper.TABLE_NAME, null, values)
        db.close()
    }

    fun insertRoleAdvantage(advantage: RoleAdvantage) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(RoleAdvantageHelper.COLUMN_ROLE_ID, advantage.roleId)
            put(RoleAdvantageHelper.COLUMN_ADVANTAGE_ID, advantage.advantageId)
        }
        db.insert(RoleAdvantageHelper.TABLE_NAME, null, values)
        db.close()
    }

    fun getAdvantagesByRole(role: Int): List<Advantage> {
        val db = this.readableDatabase

        val query = "SELECT advantages.* FROM advantages INNER JOIN role_advantages " +
                "ON advantages.id = role_advantages.advantage_id " +
                "WHERE role_advantages.role_id = $role"

        //val query = "SELECT * FROM role_advantages WHERE role_id = $role"
        val cursor = db.rawQuery(query, null)
        val advantageList = mutableListOf<Advantage>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(AdvantageHelper.COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(AdvantageHelper.COLUMN_NAME))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(AdvantageHelper.COLUMN_DESCRIPTION))
            val advantage = Advantage(id, name, description)
            advantageList.add(advantage)
        }
        cursor.close()
        db.close()
        return advantageList
    }

}