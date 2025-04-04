package com.delek.lostrealm.database.helper

import android.content.Context
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME: String = "db_realm"
        const val DATABASE_VERSION: Int = 2
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(RoleHelper.SQL_CREATE_ENTRIES)
        db?.execSQL(AdvantageHelper.SQL_CREATE_ENTRIES)
        db?.execSQL(RoleAdvantageHelper.SQL_CREATE_ENTRIES)
        db?.execSQL(ChitHelper.SQL_CREATE_ENTRIES)
        db?.execSQL(DevelopmentHelper.SQL_CREATE_ENTRIES)
        db?.execSQL(WeaponHelper.SQL_CREATE_ENTRIES)
        db?.execSQL(ArmorHelper.SQL_CREATE_ENTRIES)
        db?.execSQL(DwellingHelper.SQL_CREATE_ENTRIES)
        db?.execSQL(RoleDwellingHelper.SQL_CREATE_ENTRIES)
        db?.execSQL(SpellHelper.SQL_CREATE_ENTRIES)
        db?.execSQL(StartSpellHelper.SQL_CREATE_ENTRIES)
        db?.execSQL(PlayerHelper.SQL_CREATE_ENTRIES)
        db?.execSQL(TileHelper.SQL_CREATE_ENTRIES)
        db?.execSQL(MapHelper.SQL_CREATE_ENTRIES)

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(RoleHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(AdvantageHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(RoleAdvantageHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(ChitHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(DevelopmentHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(WeaponHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(ArmorHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(DwellingHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(RoleDwellingHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(SpellHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(StartSpellHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(PlayerHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(TileHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(MapHelper.SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    fun onDelete() {
        val db = writableDatabase
        db?.execSQL(RoleHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(AdvantageHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(RoleAdvantageHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(ChitHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(DevelopmentHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(WeaponHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(ArmorHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(DwellingHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(RoleDwellingHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(SpellHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(StartSpellHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(PlayerHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(TileHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(MapHelper.SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    fun isEmpty(table: String?): Boolean {
        val database = this.readableDatabase
        val numRows = DatabaseUtils.queryNumEntries(database, table)

        return numRows == 0L
    }
}