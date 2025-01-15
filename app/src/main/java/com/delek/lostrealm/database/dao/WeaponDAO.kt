package com.delek.lostrealm.database.dao

import android.content.ContentValues
import android.content.Context
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

}