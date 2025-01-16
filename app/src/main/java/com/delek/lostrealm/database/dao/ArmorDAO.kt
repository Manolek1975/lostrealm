package com.delek.lostrealm.database.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.delek.lostrealm.database.helper.ArmorHelper
import com.delek.lostrealm.database.helper.DBHelper
import com.delek.lostrealm.database.model.Armor

class ArmorDAO(context: Context) : SQLiteOpenHelper(
    context,
    DBHelper.DATABASE_NAME, null,
    DBHelper.DATABASE_VERSION
) {
    override fun onCreate(p0: SQLiteDatabase?) {}

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}

    fun insertArmor(armor: Armor) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(ArmorHelper.COLUMN_NAME, armor.name)
            put(ArmorHelper.COLUMN_IMAGE, armor.image)
            put(ArmorHelper.COLUMN_DEFENSE, armor.defense)
            put(ArmorHelper.COLUMN_PRICE, armor.price)
            put(ArmorHelper.COLUMN_PRICE_DAMAGED, armor.priceDamaged)
        }
        db.insert(ArmorHelper.TABLE_NAME, null, values)
        db.close()
    }

    fun getArmorByDevelopment(role: Int, level: Int): List<Armor> {
        val db = this.readableDatabase
        val query = "SELECT armor.* FROM armor INNER JOIN development " +
                "ON armor.id = development.armor_id " +
                "WHERE development.role_id = $role AND development.level = $level"
        val cursor = db.rawQuery(query, null)
        val armorList = mutableListOf<Armor>()
        while (cursor.moveToNext()) {
            val armor = getColumns(cursor)
            armorList.add(armor)
        }
        cursor.close()
        db.close()
        return armorList
    }

    private fun getColumns(cursor: Cursor): Armor {
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(ArmorHelper.COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(ArmorHelper.COLUMN_NAME))
        val image = cursor.getString(cursor.getColumnIndexOrThrow(ArmorHelper.COLUMN_IMAGE))
        val defense = cursor.getString(cursor.getColumnIndexOrThrow(ArmorHelper.COLUMN_DEFENSE))
        val price = cursor.getString(cursor.getColumnIndexOrThrow(ArmorHelper.COLUMN_PRICE))
        val priceDamaged = cursor.getString(cursor.getColumnIndexOrThrow(ArmorHelper.COLUMN_PRICE_DAMAGED))

        val armor = Armor(id, name, image, defense, price.toInt(), priceDamaged.toInt())
        return armor
    }

}



