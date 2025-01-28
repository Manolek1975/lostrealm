package com.delek.lostrealm.database.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.delek.lostrealm.database.helper.DBHelper
import com.delek.lostrealm.database.helper.PlayerHelper
import com.delek.lostrealm.database.model.Player

class PlayerDAO(context: Context): SQLiteOpenHelper(
    context, DBHelper.DATABASE_NAME, null, DBHelper.DATABASE_VERSION) {

    override fun onCreate(p0: SQLiteDatabase?) { }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) { }

    private fun getColumns(cursor: Cursor): Player {
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(PlayerHelper.COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(PlayerHelper.COLUMN_NAME))
        val roleId = cursor.getInt(cursor.getColumnIndexOrThrow(PlayerHelper.COLUMN_ROLE_ID))
        val armor = cursor.getInt(cursor.getColumnIndexOrThrow(PlayerHelper.COLUMN_ARMOR))
        val weapons = cursor.getInt(cursor.getColumnIndexOrThrow(PlayerHelper.COLUMN_WEAPONS))
        val treasures = cursor.getInt(cursor.getColumnIndexOrThrow(PlayerHelper.COLUMN_TREASURES))
        val spells = cursor.getInt(cursor.getColumnIndexOrThrow(PlayerHelper.COLUMN_SPELLS))
        val gold = cursor.getInt(cursor.getColumnIndexOrThrow(PlayerHelper.COLUMN_GOLD))
        val position = cursor.getInt(cursor.getColumnIndexOrThrow(PlayerHelper.COLUMN_POSITION))

        val player = Player(id, name, roleId, armor, weapons, treasures, spells, gold, position)
        return player
    }

    fun insertPlayer(player: Player) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(PlayerHelper.COLUMN_NAME, player.name)
            put(PlayerHelper.COLUMN_ROLE_ID, player.roleId)
            put(PlayerHelper.COLUMN_ARMOR, player.armor)
            put(PlayerHelper.COLUMN_WEAPONS, player.weapons)
            put(PlayerHelper.COLUMN_TREASURES, player.treasures)
            put(PlayerHelper.COLUMN_SPELLS, player.spells)
            put(PlayerHelper.COLUMN_GOLD, player.gold)
            put(PlayerHelper.COLUMN_POSITION, player.position)
        }
        db.insert(PlayerHelper.TABLE_NAME, null, values)
        db.close()
    }

    fun getAllPlayers(): List<Player> {
        val db = readableDatabase
        val playerList = mutableListOf<Player>()
        val query = "SELECT * FROM players"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            val player = getColumns(cursor)
            playerList.add(player)
        }
        cursor.close()
        db.close()
        return playerList
    }
}