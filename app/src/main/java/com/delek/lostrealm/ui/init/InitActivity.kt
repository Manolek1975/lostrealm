package com.delek.lostrealm.ui.init




import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.os.Bundle
import android.widget.RadioButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.setPadding
import androidx.recyclerview.widget.GridLayoutManager
import com.delek.lostrealm.R
import com.delek.lostrealm.database.dao.DwellingDAO
import com.delek.lostrealm.database.dao.RoleDAO
import com.delek.lostrealm.database.dao.SpellDAO
import com.delek.lostrealm.databinding.ActivityInitBinding
import com.delek.lostrealm.databinding.ItemVpButtonBinding


class InitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInitBinding
    private lateinit var adapter: SpellAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemBars()
        initUI()
    }

    private fun initUI() {
        val data = this.getSharedPreferences("data", Context.MODE_PRIVATE)
        data.edit().putInt("num_spells", 0).apply()
        println("INIT")
        // Start Dwellings
        val i = intent.getIntExtra("role", 0)
        val role = RoleDAO(this).getRoleById(i)
        val dwelling = DwellingDAO(this).getRoleDwellings(i)

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

        // Victory Points
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
            }
            v.ibRight.setOnClickListener {
                var inc = v.tvValue.text.toString().toInt()
                if (inc < 5 && total < 5) {
                    inc += 1
                    total += 1
                }
                v.tvValue.text = "$inc"
                binding.tvVictoryPoints.text = getString(R.string.victory_points, total.toString())
            }
        }

        // Spells
        val numSpells = role.spells
        val selected = 0
        val spells = mutableListOf<String>()
        data.edit().putStringSet("spells", spells.toSet()).apply()
        data.edit().putInt("num_spells", numSpells).apply()
        data.edit().putInt("selected", selected).apply()
        val types = SpellDAO(this).getTypesByRole(role.id)
        if (numSpells > 0) {
            for (t in types) {
                binding.tvSpellsHead.text = getString(R.string.spells, numSpells.toString())
                val type = TextView(this)
                type.text = t
                type.textSize = 24f
                type.setTypeface(type.typeface, Typeface.BOLD)
                type.setPadding(32)
                type.setTextColor(getColor(R.color.red_dark))
                type.setBackgroundResource(R.drawable.layout_border)
                binding.typesLayout.addView(type)

                type.setOnClickListener {
                    type.setBackgroundResource(R.drawable.layout_type)
                    adapter = SpellAdapter(SpellDAO(this).getSpellsByRoleAndType(role.id, t))
                    binding.spellRecyclerView.setHasFixedSize(true)
                    binding.spellRecyclerView.layoutManager = GridLayoutManager(this, 4)
                    binding.spellRecyclerView.adapter = adapter
                }
            }
        }


/*        val spells = mutableListOf<String>()
        data.edit().putStringSet("spells", spells.toSet()).apply()

        if (role.spells > 0) {
            val selectedSpells = 0
            val numSpells = role.spells.toString()
            binding.tvSpellsHead.text =
                getString(R.string.spells, selectedSpells.toString(), numSpells)
            adapter = SpellAdapter(SpellDAO(this).getSpellsByRoleAndType(role.id))
            binding.spellRecyclerView.setHasFixedSize(true)
            binding.spellRecyclerView.layoutManager = GridLayoutManager(this, 4)
            binding.spellRecyclerView.adapter = adapter
        }*/



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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}