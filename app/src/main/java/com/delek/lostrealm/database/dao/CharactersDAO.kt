package com.delek.lostrealm.database.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.delek.lostrealm.database.helper.CharactersHelper
import com.delek.lostrealm.database.helper.DBHelper
import com.delek.lostrealm.database.model.Characters

class CharactersDAO(context: Context): SQLiteOpenHelper(context,
    DBHelper.DATABASE_NAME, null,
    DBHelper.DATABASE_VERSION
) {
    override fun onCreate(p0: SQLiteDatabase?) { }
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) { }

    fun insertCharacter(character: Characters){
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(CharactersHelper.COLUMN_NAME, character.name)
            put(CharactersHelper.COLUMN_SYMBOL, character.symbol)
            put(CharactersHelper.COLUMN_ICON, character.icon)
            put(CharactersHelper.COLUMN_WEIGHT, character.weight)
            put(CharactersHelper.COLUMN_ADVANTAGES, character.advantages)
            put(CharactersHelper.COLUMN_DEVELOPMENT, character.development)
            put(CharactersHelper.COLUMN_POSITION, character.position)
            put(CharactersHelper.COLUMN_RELATIONS, character.relations)
            put(CharactersHelper.COLUMN_DIFFICULTY, character.difficulty)
        }

        db.insert(CharactersHelper.TABLE_NAME, null, values)
        db.close()
    }

    fun getName1(): String{
        val db = this.readableDatabase
        val query = "SELECT name FROM characters WHERE id=1"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()
        val name = cursor.getString(cursor.getColumnIndexOrThrow(CharactersHelper.COLUMN_NAME))

        cursor.close()
        db.close()
        return name
    }

}