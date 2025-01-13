package com.delek.lostrealm.ui.role

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.delek.lostrealm.R
import com.delek.lostrealm.database.model.Role
import com.delek.lostrealm.databinding.ItemRoleBinding
import com.delek.lostrealm.ui.detail.DetailActivity
import java.lang.reflect.Field

class RoleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var binding = ItemRoleBinding.bind(view)

    fun render(role: Role) {
        val context = binding.roleImage.context
        val id = getResId(role.image, R.drawable::class.java)
        binding.roleImage.setImageResource(id)
        binding.roleName.text = role.name

        binding.roleImage.setOnClickListener {
            val i = Intent(context, DetailActivity::class.java)
            i.putExtra("role", role.id)
            context.startActivity(i)
        }

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