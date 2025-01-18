package com.delek.lostrealm.ui.init


import android.os.Bundle
import android.widget.RadioButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.delek.lostrealm.R
import com.delek.lostrealm.database.dao.DwellingDAO
import com.delek.lostrealm.database.dao.RoleDAO
import com.delek.lostrealm.databinding.ActivityInitBinding


class InitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemBars()
        initUI()
    }

    private fun initUI() {
        val i = intent.getIntExtra("role", 0)
        val role = RoleDAO(this).getRoleById(i)
        val dwelling = DwellingDAO(this).getRoleDwellings(i)

        binding.tvHead.text = getString(R.string.options, role.name)
        for (d in dwelling) {
            binding.rgDwelling.addView(RadioButton(this).apply {
                id = d.id
                text = d.name
                textSize = 20F
                isChecked = true
            })
        }

        binding.itemVpTreasures.tvName.text = getString(R.string.vp_treasures)
        binding.itemVpSpells.tvName.text = getString(R.string.vp_spells)
        binding.itemVpFame.tvName.text = getString(R.string.vp_fame)
        binding.itemVpNotoriety.tvName.text = getString(R.string.vp_notoriety)
        binding.itemVpGold.tvName.text = getString(R.string.vp_gold)
        binding.itemVpTreasures.tvValue.text = "0"
        binding.itemVpSpells.tvValue.text = "0"
        binding.itemVpFame.tvValue.text = "0"
        binding.itemVpNotoriety.tvValue.text = "0"
        binding.itemVpGold.tvValue.text = "0"

        calculateVpValues()
    }

    private fun calculateVpValues() {
        var total = 0
        binding.tvVictoryPoints.text = getString(R.string.victory_points, total.toString())
        var gt = 0
        var sp = 0
        var fm = 0
        var nt = 0
        var go = 0
        binding.itemVpTreasures.ibLeft.setOnClickListener {
            if (gt > 0) {
                gt -= 1
                total -= 1
            }
            binding.itemVpTreasures.tvValue.text = "$gt"
            binding.tvVictoryPoints.text = getString(R.string.victory_points, total.toString())
        }
        binding.itemVpTreasures.ibRight.setOnClickListener {
            if (gt < 5 && total < 5) {
                gt += 1
                total += 1
            }
            binding.itemVpTreasures.tvValue.text = "$gt"
            binding.tvVictoryPoints.text = getString(R.string.victory_points, total.toString())
        }
        binding.itemVpSpells.ibLeft.setOnClickListener {
            if (sp > 0) {
                sp -= 1
                total -= 1
            }
            binding.itemVpSpells.tvValue.text = "$sp"
            binding.tvVictoryPoints.text = getString(R.string.victory_points, total.toString())
        }
        binding.itemVpSpells.ibRight.setOnClickListener {
            if (sp < 5 && total < 5) {
                sp += 1
                total += 1
            }
            binding.itemVpSpells.tvValue.text = "$sp"
            binding.tvVictoryPoints.text = getString(R.string.victory_points, total.toString())
        }
        binding.itemVpFame.ibLeft.setOnClickListener {
            if (fm > 0) {
                fm -= 1
                total -= 1
            }
            binding.itemVpFame.tvValue.text = "$fm"
            binding.tvVictoryPoints.text = getString(R.string.victory_points, total.toString())
        }
        binding.itemVpFame.ibRight.setOnClickListener {
            if (fm < 5 && total < 5) {
                fm += 1
                total += 1
            }
            binding.itemVpFame.tvValue.text = "$fm"
            binding.tvVictoryPoints.text = getString(R.string.victory_points, total.toString())
        }
        binding.itemVpNotoriety.ibLeft.setOnClickListener {
            if (nt > 0) {
                nt -= 1
                total -= 1
            }
            binding.itemVpNotoriety.tvValue.text = "$nt"
            binding.tvVictoryPoints.text = getString(R.string.victory_points, total.toString())
        }
        binding.itemVpNotoriety.ibRight.setOnClickListener {
            if (nt < 5 && total < 5) {
                nt += 1
                total += 1
            }
            binding.itemVpNotoriety.tvValue.text = "$nt"
            binding.tvVictoryPoints.text = getString(R.string.victory_points, total.toString())
        }
        binding.itemVpGold.ibLeft.setOnClickListener {
            if (go > 0) {
                go -= 1
                total -= 1
            }
            binding.itemVpGold.tvValue.text = "$go"
            binding.tvVictoryPoints.text = getString(R.string.victory_points, total.toString())
        }
        binding.itemVpGold.ibRight.setOnClickListener {
            if (go < 5 && total < 5) {
                go += 1
                total += 1
            }
            binding.itemVpGold.tvValue.text = "$go"
            binding.tvVictoryPoints.text = getString(R.string.victory_points, total.toString())
        }


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