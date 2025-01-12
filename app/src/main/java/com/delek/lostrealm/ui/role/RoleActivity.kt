package com.delek.lostrealm.ui.role

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.delek.lostrealm.R
import com.delek.lostrealm.database.dao.RoleDAO
import com.delek.lostrealm.databinding.ActivityRoleBinding


class RoleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRoleBinding
    private lateinit var adapter: RoleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemBars()
        initUI()
    }

    private fun initUI() {
        adapter = RoleAdapter(RoleDAO(this).getAll())
        binding.roleRecyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.roleRecyclerView.adapter = adapter
    }

    private fun hideSystemBars() {
        enableEdgeToEdge()
        val controller = WindowInsetsControllerCompat(
            window, window.decorView
        )
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}