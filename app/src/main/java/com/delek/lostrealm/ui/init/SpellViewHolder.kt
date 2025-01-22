package com.delek.lostrealm.ui.init

import android.content.Context
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.delek.lostrealm.R
import com.delek.lostrealm.database.model.Spell
import com.delek.lostrealm.databinding.ItemSpellBinding

class SpellViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemSpellBinding.bind(view)
    val context: Context = binding.itemSpell.context

    fun render(spell: Spell) {
        val data = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        var spellListSet = data.getStringSet("spells", emptySet())?.toMutableList()?: mutableListOf()
        val numSpells = data.getInt("num_spells", 0)
        //val selected = data.getInt("selected", 0)
        if (spellListSet.contains(spell.id.toString())) {
            binding.lyItemSpell.background = AppCompatResources.getDrawable(context, R.drawable.layout_head)
        }
        binding.duration.text = spell.duration
        binding.target.text = spell.target
        binding.name.text = spell.name
        binding.description.text = spell.shortDescription
        val type = spell.type
        val color = spell.color
        binding.typeColor.text = context.getString(R.string.type_color, type, color)

        binding.itemSpell.setOnClickListener {
            var selected = data.getInt("selected", 0)
            spellListSet = data.getStringSet("spells", emptySet())?.toMutableList()?: mutableListOf()
            if (spellListSet.contains(spell.id.toString())) {
                binding.lyItemSpell.background =
                    AppCompatResources.getDrawable(context, R.drawable.layout_item_no_border)
                spellListSet.removeIf { it == spell.id.toString() }
                data.edit().putStringSet("spells", spellListSet.toSet()).apply()
                --selected
                data.edit().putInt("selected", selected).apply()
            } else if (selected < numSpells) {
                spellListSet.add(spell.id.toString())
                selected = spellListSet.count()
                data.edit().putStringSet("spells", spellListSet.toSet()).apply()
                data.edit().putInt("selected", selected).apply()
                println("Total spells: $selected")
                binding.lyItemSpell.background =
                    AppCompatResources.getDrawable(context, R.drawable.layout_item_border)
                println(spellListSet)
            }

        }

    }

}


