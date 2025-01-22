package com.delek.lostrealm.database.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.delek.lostrealm.database.helper.DBHelper
import com.delek.lostrealm.database.helper.SpellHelper
import com.delek.lostrealm.database.model.Spell

class SpellDAO(context: Context) : SQLiteOpenHelper( context,
    DBHelper.DATABASE_NAME, null,
    DBHelper.DATABASE_VERSION
) {
    override fun onCreate(p0: SQLiteDatabase?) {}

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}

    private fun getColumns(cursor: Cursor): Spell {
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(SpellHelper.COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(SpellHelper.COLUMN_NAME))
        val type = cursor.getString(cursor.getColumnIndexOrThrow(SpellHelper.COLUMN_TYPE))
        val color = cursor.getString(cursor.getColumnIndexOrThrow(SpellHelper.COLUMN_COLOR))
        val target = cursor.getString(cursor.getColumnIndexOrThrow(SpellHelper.COLUMN_TARGET))
        val duration = cursor.getString(cursor.getColumnIndexOrThrow(SpellHelper.COLUMN_DURATION))
        val description =
            cursor.getString(cursor.getColumnIndexOrThrow(SpellHelper.COLUMN_DESCRIPTION))
        val shortDescription =
            cursor.getString(cursor.getColumnIndexOrThrow(SpellHelper.COLUMN_SHORT_DESCRIPTION))

        val spell = Spell(id, name, type, color, target, duration, description, shortDescription)
        return spell
    }

    fun insertSpell(spell: Spell) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(SpellHelper.COLUMN_NAME, spell.name)
            put(SpellHelper.COLUMN_TYPE, spell.type)
            put(SpellHelper.COLUMN_COLOR, spell.color)
            put(SpellHelper.COLUMN_TARGET, spell.target)
            put(SpellHelper.COLUMN_DURATION, spell.duration)
            put(SpellHelper.COLUMN_DESCRIPTION, spell.description)
            put(SpellHelper.COLUMN_SHORT_DESCRIPTION, spell.shortDescription)
        }
        db.insert(SpellHelper.TABLE_NAME, null, values)
        db.close()
    }

    fun getSpellsByRole(role: Int): List<Spell> {
        val db = this.readableDatabase
        val spellList = mutableListOf<Spell>()
        val query = "SELECT spells.* FROM spells INNER JOIN start_spell " +
                "ON spells.type = start_spell.type " +
                "WHERE start_spell.role_id = $role"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            val spell = getColumns(cursor)
            spellList.add(spell)
        }
        cursor.close()
        db.close()
        return spellList
    }

    fun getTypesByRole(id: Int): List<String> {
        val db = this.readableDatabase
        val typeList = mutableListOf<String>()
        val query = "SELECT DISTINCT type FROM start_spell WHERE role_id = $id"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            val type = cursor.getString(cursor.getColumnIndexOrThrow(SpellHelper.COLUMN_TYPE))
            typeList.add(type)
        }
        cursor.close()
        db.close()
        return typeList
    }

    fun getSpellsByRoleAndType(id: Int, t: String): List<Spell> {
        val db = this.readableDatabase
        val spellList = mutableListOf<Spell>()
        val query = "SELECT spells.* FROM spells INNER JOIN start_spell " +
                "ON spells.type = start_spell.type " +
                "WHERE start_spell.role_id = $id AND start_spell.type = '$t' "
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            val spell = getColumns(cursor)
            spellList.add(spell)
        }
        cursor.close()
        db.close()
        return spellList
    }


}