package com.delek.lostrealm.database.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.delek.lostrealm.database.helper.DBHelper
import com.delek.lostrealm.database.helper.WeaponHelper
import com.delek.lostrealm.database.model.Weapon

class WeaponDAO(context: Context) : SQLiteOpenHelper(
    context,
    DBHelper.DATABASE_NAME, null,
    DBHelper.DATABASE_VERSION
) {
    override fun onCreate(p0: SQLiteDatabase?) {}

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}

    fun insertWeapon(weapon: Weapon) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(WeaponHelper.COLUMN_NAME, weapon.name)
            put(WeaponHelper.COLUMN_IMAGE, weapon.image)
            put(WeaponHelper.COLUMN_ALERT, weapon.alert)
            put(WeaponHelper.COLUMN_DAMAGE, weapon.damage)
            put(WeaponHelper.COLUMN_PLUS, weapon.plus)
            put(WeaponHelper.COLUMN_PLUS_ALERT, weapon.plusAlert)
            put(WeaponHelper.COLUMN_TYPE, weapon.type)
            put(WeaponHelper.COLUMN_LENGTH, weapon.length)
            put(WeaponHelper.COLUMN_SPEED, weapon.speed)
            put(WeaponHelper.COLUMN_SPEED_ALERT, weapon.speedAlert)
            put(WeaponHelper.COLUMN_PRICE, weapon.price)
        }
        db.insert(WeaponHelper.TABLE_NAME, null, values)
        db.close()
    }

    fun getWeaponsByDevelopment(id: Int): String {
        val db = this.readableDatabase
        val query = "SELECT name FROM weapons WHERE id = $id"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()
            val weapon = cursor.getString(cursor.getColumnIndexOrThrow(WeaponHelper.COLUMN_NAME))
        cursor.close()
        db.close()
        return weapon
    }

    private fun getColumns(cursor: Cursor): Weapon {
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(WeaponHelper.COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(WeaponHelper.COLUMN_NAME))
        val image = cursor.getString(cursor.getColumnIndexOrThrow(WeaponHelper.COLUMN_IMAGE))
        val alert = cursor.getString(cursor.getColumnIndexOrThrow(WeaponHelper.COLUMN_ALERT))
        val damage = cursor.getString(cursor.getColumnIndexOrThrow(WeaponHelper.COLUMN_DAMAGE))
        val plus = cursor.getString(cursor.getColumnIndexOrThrow(WeaponHelper.COLUMN_PLUS))
        val plusAlert =
            cursor.getString(cursor.getColumnIndexOrThrow(WeaponHelper.COLUMN_PLUS_ALERT))
        val type = cursor.getString(cursor.getColumnIndexOrThrow(WeaponHelper.COLUMN_TYPE))
        val length = cursor.getString(cursor.getColumnIndexOrThrow(WeaponHelper.COLUMN_LENGTH))
        val speed = cursor.getString(cursor.getColumnIndexOrThrow(WeaponHelper.COLUMN_SPEED))
        val speedAlert =
            cursor.getString(cursor.getColumnIndexOrThrow(WeaponHelper.COLUMN_SPEED_ALERT))
        val price = cursor.getString(cursor.getColumnIndexOrThrow(WeaponHelper.COLUMN_PRICE))

        val weapon = Weapon(
            id, name, image, alert, damage, plus, plusAlert, type,
            length.toInt(), speed.toInt(), speedAlert.toInt(), price.toInt()
        )
        return weapon
    }

}