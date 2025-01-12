package com.delek.lostrealm.ui.role

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.delek.lostrealm.R
import com.delek.lostrealm.database.model.Role
import com.delek.lostrealm.databinding.ItemRoleBinding
import java.lang.reflect.Field

class RoleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var binding = ItemRoleBinding.bind(view)

    fun render(role: Role) {
        val id = getResId(role.image, R.drawable::class.java)
        binding.roleImage.setImageResource(id)
        binding.roleName.text = role.name

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