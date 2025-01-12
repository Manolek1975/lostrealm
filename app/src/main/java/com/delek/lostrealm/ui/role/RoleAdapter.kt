package com.delek.lostrealm.ui.role

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.delek.lostrealm.R
import com.delek.lostrealm.database.model.Role


class RoleAdapter(private var roleList: List<Role>) :
    RecyclerView.Adapter<RoleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_role, parent, false)
        return RoleViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoleViewHolder, position: Int) {
        holder.render(roleList[position])
    }

    override fun getItemCount(): Int = roleList.size

}