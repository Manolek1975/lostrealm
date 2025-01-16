package com.delek.lostrealm.database.dao

import android.content.ContentValues
import android.content.Context
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

}