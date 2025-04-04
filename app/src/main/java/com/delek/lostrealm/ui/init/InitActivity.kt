package com.delek.lostrealm.ui.init

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.os.Bundle
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.children
import androidx.core.view.setPadding
import androidx.recyclerview.widget.GridLayoutManager
import com.delek.lostrealm.R
import com.delek.lostrealm.database.dao.DwellingDAO
import com.delek.lostrealm.database.dao.PlayerDAO
import com.delek.lostrealm.database.dao.RoleDAO
import com.delek.lostrealm.database.dao.SpellDAO
import com.delek.lostrealm.database.model.Player
import com.delek.lostrealm.database.model.Role
import com.delek.lostrealm.databinding.ActivityInitBinding
import com.delek.lostrealm.databinding.ItemVpButtonBinding
import com.delek.lostrealm.ui.nav.PlayerActivity


class InitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInitBinding
    private lateinit var adapter: SpellAdapter
    private lateinit var data: android.content.SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInitBinding.inflate(layoutInflater)
        data = this.getSharedPreferences("data", Context.MODE_PRIVATE)
        setContentView(binding.root)
        hideSystemBars()
        initUI()
    }

    private fun initUI() {
        val i = intent.getIntExtra("role", 0)
        val role = RoleDAO(this).getRoleById(i)
        data.edit().putInt("num_spells", 0).apply()
        data.edit().putInt("total_vp", 0).apply()
        initDwellings(role)
        initSpells(role)
        initVP()

        binding.checkButton.setOnClickListener {
            val selected = data.getInt("selected", 0)
            val totalVP = data.getInt("total_vp", 0)
            println("selected: $selected TotalVp: $totalVP")
            if (role.spells == selected && totalVP == 5){
                val intent = Intent(this, PlayerActivity::class.java)
                val player = Player(0, role.name, role.id, 0, 0, 0, 0, 0,0)
                PlayerDAO(this).insertPlayer(player)
                startActivity(intent)
            } else {
                Toast.makeText(this, "You must select your spells and VP", Toast.LENGTH_LONG).show()
            }

        }
    }

    // Start Dwellings
    private fun initDwellings(role: Role) {
        val dwelling = DwellingDAO(this).getRoleDwellings(role.id)
        binding.tvHead.text = getString(R.string.options, role.name)
        for (d in dwelling) {
            binding.rgDwelling.addView(RadioButton(this).apply {
                id = d.id
                text = d.name
                textSize = 20F
                setTextColor(getColor(R.color.primary))
                isChecked = true
                buttonTintList = ColorStateList.valueOf(getColor(R.color.primary))
            })
        }
    }

    // Start Spells
    private fun initSpells(role: Role) {
        val numSpells = role.spells
        val selected = 0
        val spells = mutableListOf<String>()
        data.edit().putInt("num_spells", numSpells).apply()
        data.edit().putInt("selected", selected).apply()
        data.edit().putStringSet("spells", spells.toSet()).apply()
        val types = SpellDAO(this).getTypesByRole(role.id)
        if (numSpells > 0) {
            for (t in types) {
                val type = TextView(this)
                binding.tvSpellsHead.text = getString(R.string.spells, numSpells.toString())
                type.text = t
                type.textSize = 24f
                type.setTypeface(type.typeface, Typeface.BOLD)
                type.setPadding(32)
                type.setTextColor(getColor(R.color.red_dark))
                type.setBackgroundResource(R.drawable.layout_border)
                binding.typesLayout.addView(type)
                type.setOnClickListener {
                    for (sel in binding.typesLayout.children){
                        sel.setBackgroundResource(R.drawable.layout_border)
                    }
                    type.setBackgroundResource(R.drawable.layout_type)
                    adapter = SpellAdapter(SpellDAO(this).getSpellsByRoleAndType(role.id, t))
                    binding.spellRecyclerView.setHasFixedSize(true)
                    binding.spellRecyclerView.layoutManager = GridLayoutManager(this, 4)
                    binding.spellRecyclerView.adapter = adapter
                }
            }
        }
    }

    // Victory Points
    private fun initVP() {
        var total = 0
        binding.tvVictoryPoints.text = getString(R.string.victory_points, total.toString())
        val vp = setVpNames()
        vp.forEach { v ->
            v.ibLeft.setOnClickListener {
                var dec = v.tvValue.text.toString().toInt()
                if (dec > 0) {
                    dec -= 1
                    total -= 1
                }
                v.tvValue.text = "$dec"
                binding.tvVictoryPoints.text = getString(R.string.victory_points, total.toString())
                data.edit().putInt("total_vp", total).apply()
            }
            v.ibRight.setOnClickListener {
                var inc = v.tvValue.text.toString().toInt()
                if (inc < 5 && total < 5) {
                    inc += 1
                    total += 1
                }
                v.tvValue.text = "$inc"
                binding.tvVictoryPoints.text = getString(R.string.victory_points, total.toString())
                data.edit().putInt("total_vp", total).apply()
            }

        }
    }

    private fun setVpNames(): List<ItemVpButtonBinding> {
        val treasures = binding.itemVpTreasures
        val spells = binding.itemVpSpells
        val fame = binding.itemVpFame
        val notoriety = binding.itemVpNotoriety
        val gold = binding.itemVpGold
        treasures.tvName.text = getString(R.string.vp_treasures)
        spells.tvName.text = getString(R.string.vp_spells)
        fame.tvName.text = getString(R.string.vp_fame)
        notoriety.tvName.text = getString(R.string.vp_notoriety)
        gold.tvName.text = getString(R.string.vp_gold)
        return listOf(treasures, spells, fame, notoriety, gold)
    }

    private fun hideSystemBars() {
        enableEdgeToEdge()
        val controller = WindowInsetsControllerCompat(
            window, window.decorView
        )
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
}