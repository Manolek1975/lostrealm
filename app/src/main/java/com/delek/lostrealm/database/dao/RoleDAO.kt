package com.delek.lostrealm.database.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.delek.lostrealm.database.helper.DBHelper
import com.delek.lostrealm.database.helper.RoleHelper
import com.delek.lostrealm.database.helper.StartSpellHelper
import com.delek.lostrealm.database.model.Role
import com.delek.lostrealm.database.model.StartSpell

class RoleDAO(context: Context) : SQLiteOpenHelper(
    context,
    DBHelper.DATABASE_NAME, null,
    DBHelper.DATABASE_VERSION
) {
    override fun onCreate(p0: SQLiteDatabase?) {}
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}

    private fun getColumns(cursor: Cursor): Role {
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(RoleHelper.COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(RoleHelper.COLUMN_NAME))
        val symbol = cursor.getString(cursor.getColumnIndexOrThrow(RoleHelper.COLUMN_SYMBOL))
        val image = cursor.getString(cursor.getColumnIndexOrThrow(RoleHelper.COLUMN_IMAGE))
        val detail = cursor.getString(cursor.getColumnIndexOrThrow(RoleHelper.COLUMN_DETAIL))
        val icon = cursor.getString(cursor.getColumnIndexOrThrow(RoleHelper.COLUMN_ICON))
        val weight = cursor.getString(cursor.getColumnIndexOrThrow(RoleHelper.COLUMN_WEIGHT))
        val advantages = cursor.getInt(cursor.getColumnIndexOrThrow(RoleHelper.COLUMN_ADVANTAGES))
        val development = cursor.getInt(cursor.getColumnIndexOrThrow(RoleHelper.COLUMN_DEVELOPMENT))
        val position = cursor.getInt(cursor.getColumnIndexOrThrow(RoleHelper.COLUMN_POSITION))
        val spell = cursor.getInt(cursor.getColumnIndexOrThrow(RoleHelper.COLUMN_SPELLS))
        val relations = cursor.getInt(cursor.getColumnIndexOrThrow(RoleHelper.COLUMN_RELATIONS))
        val difficulty = cursor.getInt(cursor.getColumnIndexOrThrow(RoleHelper.COLUMN_DIFFICULTY))

        val role = Role(
            id, name, symbol, icon, image, detail, weight,
            advantages, development, position, spell, relations, difficulty
        )
        return role
    }

    fun insertRole(role: Role) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(RoleHelper.COLUMN_NAME, role.name)
            put(RoleHelper.COLUMN_SYMBOL, role.symbol)
            put(RoleHelper.COLUMN_ICON, role.icon)
            put(RoleHelper.COLUMN_IMAGE, role.image)
            put(RoleHelper.COLUMN_DETAIL, role.detail)
            put(RoleHelper.COLUMN_WEIGHT, role.weight)
            put(RoleHelper.COLUMN_ADVANTAGES, role.advantages)
            put(RoleHelper.COLUMN_DEVELOPMENT, role.development)
            put(RoleHelper.COLUMN_POSITION, role.position)
            put(RoleHelper.COLUMN_SPELLS, role.spells)
            put(RoleHelper.COLUMN_RELATIONS, role.relations)
            put(RoleHelper.COLUMN_DIFFICULTY, role.difficulty)
        }
        db.insert(RoleHelper.TABLE_NAME, null, values)
        db.close()
    }

    fun insertStartSpell(startSpell: StartSpell) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(StartSpellHelper.COLUMN_ROLE_ID, startSpell.roleId)
            put(StartSpellHelper.COLUMN_TYPE, startSpell.type)
        }
        db.insert(StartSpellHelper.TABLE_NAME, null, values)
        db.close()
    }

    fun getAll(): List<Role> {
        val db = this.readableDatabase
        val roleList = mutableListOf<Role>()
        val query = "SELECT * FROM roles"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            val role = getColumns(cursor)
            roleList.add(role)
        }
        cursor.close()
        db.close()
        return roleList
    }

    fun getRoleById(id: Int): Role {
        val db = this.readableDatabase
        val query = "SELECT * FROM roles WHERE id = $id"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()
        val role = getColumns(cursor)
        cursor.close()
        db.close()
        return role
    }

}