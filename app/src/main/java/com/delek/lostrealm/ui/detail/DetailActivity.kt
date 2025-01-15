package com.delek.lostrealm.ui.detail


import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.delek.lostrealm.R
import com.delek.lostrealm.database.dao.AdvantageDAO
import com.delek.lostrealm.database.dao.ChitDAO
import com.delek.lostrealm.database.dao.DevelopmentDAO
import com.delek.lostrealm.database.dao.RoleDAO
import com.delek.lostrealm.databinding.ActivityDetailBinding
import java.lang.reflect.Field


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemBars()
        initUI()
    }

    private fun initUI() {
        //var chits = mutableListOf<Chit>()
        val i = intent.getIntExtra("role", 0)
        val role = RoleDAO(this).getRoleById(i)
        val adv = AdvantageDAO(this).getAdvantagesByRole(i)
        val devs = DevelopmentDAO(this).getDevelopmentByRole(role.id)
        val chits = ChitDAO(this).getChitsByDevelopment(role.id)

        val id = getResId(role.icon, R.drawable::class.java)
        binding.roleIcon.setImageResource(id)
        binding.tvName.text = role.name
        binding.tvSymbol.text = role.symbol
        binding.tvWeight.text = role.weight
        binding.tvAdv1.text = adv[0].name
        binding.tvAdv1Desc.text = adv[0].description
        binding.tvAdv2.text = adv[1].name
        binding.tvAdv2Desc.text = adv[1].description

        //Level 1
        binding.dev.tvDevLevel1.text = devs[0].name
        binding.dev.chit1.name.text = chits[0].name
        binding.dev.chit1.type.text = chits[0].type
        binding.dev.chit1.speed.text = chits[0].speed.toString()
        binding.dev.chit1.effort.text = chits[0].effort

        binding.dev.chit2.name.text = chits[1].name
        binding.dev.chit2.type.text = chits[1].type
        binding.dev.chit2.speed.text = chits[1].speed.toString()
        binding.dev.chit2.effort.text = chits[1].effort

        binding.dev.chit3.name.text = chits[2].name
        binding.dev.chit3.type.text = chits[2].type
        binding.dev.chit3.speed.text = chits[2].speed.toString()
        binding.dev.chit3.effort.text = chits[2].effort

        //Level 2
        binding.dev.tvDevLevel2.text = devs[4].name
        binding.dev.chit4.name.text = chits[3].name
        binding.dev.chit4.type.text = chits[3].type
        binding.dev.chit4.speed.text = chits[3].speed.toString()
        binding.dev.chit4.effort.text = chits[3].effort

        binding.dev.chit5.name.text = chits[4].name
        binding.dev.chit5.type.text = chits[4].type
        binding.dev.chit5.speed.text = chits[4].speed.toString()
        binding.dev.chit5.effort.text = chits[4].effort

        binding.dev.chit6.name.text = chits[5].name
        binding.dev.chit6.type.text = chits[5].type
        binding.dev.chit6.speed.text = chits[5].speed.toString()
        binding.dev.chit6.effort.text = chits[5].effort

        //Level 3
        binding.dev.tvDevLevel3.text = devs[7].name
        binding.dev.chit7.name.text = chits[6].name
        binding.dev.chit7.type.text = chits[6].type
        binding.dev.chit7.speed.text = chits[6].speed.toString()
        binding.dev.chit7.effort.text = chits[6].effort

        binding.dev.chit8.name.text = chits[7].name
        binding.dev.chit8.type.text = chits[7].type
        binding.dev.chit8.speed.text = chits[7].speed.toString()
        binding.dev.chit8.effort.text = chits[7].effort

        binding.dev.chit9.name.text = chits[8].name
        binding.dev.chit9.type.text = chits[8].type
        binding.dev.chit9.speed.text = chits[8].speed.toString()
        binding.dev.chit9.effort.text = chits[8].effort

        //Level 4
        binding.dev.tvDevLevel4.text = devs[10].name
        binding.dev.chit10.name.text = chits[9].name
        binding.dev.chit10.type.text = chits[9].type
        binding.dev.chit10.speed.text = chits[9].speed.toString()
        binding.dev.chit10.effort.text = chits[9].effort

        binding.dev.chit11.name.text = chits[10].name
        binding.dev.chit11.type.text = chits[10].type
        binding.dev.chit11.speed.text = chits[10].speed.toString()
        binding.dev.chit11.effort.text = chits[10].effort

        binding.dev.chit12.name.text = chits[11].name
        binding.dev.chit12.type.text = chits[11].type
        binding.dev.chit12.speed.text = chits[11].speed.toString()
        binding.dev.chit12.effort.text = chits[11].effort


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