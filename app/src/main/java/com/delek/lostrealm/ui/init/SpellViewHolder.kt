package com.delek.lostrealm.ui.init

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.delek.lostrealm.R
import com.delek.lostrealm.database.model.Spell
import com.delek.lostrealm.databinding.ItemSpellBinding

class SpellViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemSpellBinding.bind(view)

    fun render(spell: Spell) {
        val context = binding.name.context
        binding.duration.text = spell.duration
        binding.target.text = spell.target
        binding.name.text = spell.name
        binding.description.text = spell.shortDescription
        val type = spell.type
        val color = spell.color
        binding.typeColor.text = context.getString(R.string.type_color, type, color)
    }
}
