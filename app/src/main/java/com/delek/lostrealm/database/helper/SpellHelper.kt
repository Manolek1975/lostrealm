package com.delek.lostrealm.database.helper

import android.content.Context
import com.delek.lostrealm.R
import com.delek.lostrealm.database.dao.SpellDAO
import com.delek.lostrealm.database.model.Spell

class SpellHelper {

    companion object {
        const val TABLE_NAME: String = "spells"
        const val COLUMN_ID: String = "id"
        const val COLUMN_NAME: String = "name"
        const val COLUMN_TYPE: String = "type"
        const val COLUMN_COLOR: String = "color"
        const val COLUMN_TARGET: String = "target"
        const val COLUMN_DURATION: String = "duration"
        const val COLUMN_DESCRIPTION: String = "description"
        const val COLUMN_SHORT_DESCRIPTION: String = "short_description"


        val SQL_CREATE_ENTRIES = buildString {
            append("CREATE TABLE $TABLE_NAME (")
            append("$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,")
            append("$COLUMN_NAME TEXT,")
            append("$COLUMN_TYPE TEXT,")
            append("$COLUMN_COLOR TEXT,")
            append("$COLUMN_TARGET TEXT,")
            append("$COLUMN_DURATION TEXT,")
            append("$COLUMN_DESCRIPTION TEXT,")
            append("$COLUMN_SHORT_DESCRIPTION TEXT)")
        }

        const val SQL_DELETE_ENTRIES: String = "DROP TABLE IF EXISTS $TABLE_NAME"

        // Load resources from xml files to database
        fun loadSpells(context: Context){
            val res = context.resources
            val name = res.getStringArray(R.array.spell_name)
            val type = res.getStringArray(R.array.spell_type)
            val color = res.getStringArray(R.array.spell_color)
            val target = res.getStringArray(R.array.spell_target)
            val duration = res.getStringArray(R.array.spell_duration)
            val description = res.getStringArray(R.array.spell_description)
            val shortDescription = res.getStringArray(R.array.spell_short_description)
            for (i in name.indices){
                val spell = Spell(0, name[i], type[i], color[i], target[i], duration[i],
                    description[i], shortDescription[i])
                SpellDAO(context).insertSpell(spell)
            }
        }
    }

}