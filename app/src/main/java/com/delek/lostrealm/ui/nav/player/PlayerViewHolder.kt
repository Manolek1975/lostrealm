package com.delek.lostrealm.ui.nav.player

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.delek.lostrealm.R
import com.delek.lostrealm.database.dao.RoleDAO
import com.delek.lostrealm.database.model.Player
import com.delek.lostrealm.databinding.ItemPlayerBinding
import java.lang.reflect.Field

class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemPlayerBinding.bind(view)
    val context: Context = binding.itemPlayer.context

    fun render(player: Player){
        val role = RoleDAO(context).getRoleById(player.roleId)
        val id = getResId(role.icon, R.drawable::class.java)
        binding.iconImage.setImageResource(id)
        binding.roleName.text = player.name

    }

    private fun getResId(resName: String?, c: Class<*>): Int {
        try {
            val idField: Field = c.getDeclaredField(resName!!)
            return idField.getInt(idField)
        } catch (e: Exception) {
            e.printStackTrace()
            return -1
        }
    }
}