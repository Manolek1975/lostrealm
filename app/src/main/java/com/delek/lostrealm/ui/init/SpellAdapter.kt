package com.delek.lostrealm.ui.init

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.delek.lostrealm.R
import com.delek.lostrealm.database.model.Spell

class SpellAdapter(private var spellList: List<Spell>):
    RecyclerView.Adapter<SpellViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpellViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_spell, parent, false)
        return SpellViewHolder(view)
    }

    override fun onBindViewHolder(holder: SpellViewHolder, position: Int) {
        holder.render(spellList[position])
    }

    override fun getItemCount(): Int = spellList.size

}
