package com.delek.lostrealm.ui.main

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.delek.lostrealm.R
import com.delek.lostrealm.database.helper.AdvantageHelper
import com.delek.lostrealm.database.helper.ArmorHelper
import com.delek.lostrealm.database.helper.ChitHelper
import com.delek.lostrealm.database.helper.DBHelper
import com.delek.lostrealm.database.helper.DevelopmentHelper
import com.delek.lostrealm.database.helper.DwellingHelper
import com.delek.lostrealm.database.helper.RoleAdvantageHelper
import com.delek.lostrealm.database.helper.RoleDwellingHelper
import com.delek.lostrealm.database.helper.RoleHelper
import com.delek.lostrealm.database.helper.SpellHelper
import com.delek.lostrealm.database.helper.StartSpellHelper
import com.delek.lostrealm.database.helper.TileHelper
import com.delek.lostrealm.database.helper.WeaponHelper
import com.delek.lostrealm.databinding.ActivityMainBinding
import com.delek.lostrealm.ui.nav.PlayerActivity
import com.delek.lostrealm.ui.role.RoleActivity


class MainActivity : AppCompatActivity() {

    companion object {
        private lateinit var player: MediaPlayer
        fun stopPlayer() {
            player.stop()
        }
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var data: android.content.SharedPreferences
    private lateinit var db: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemBars()
        initUI()

    }

    private fun initUI() {
        binding.tvHome.text = getString(R.string.text_main_button)
        binding.tvVersion.text = getString(R.string.app_version)
        binding.tvHome.blink()

        player = MediaPlayer.create(applicationContext, R.raw.danza_macabra)
        player.isLooping = true // Set looping
        player.setVolume(100f, 100f)
        player.start()

        db = DBHelper(this)
        binding.root.setOnClickListener {
            if (db.isEmpty("roles")) {
                val i = Intent(this, RoleActivity::class.java)
                Toast.makeText(this, "Loading Data...", Toast.LENGTH_SHORT).show()
                loadTables()
                startActivity(i)
            } else {
                val i = Intent(this, PlayerActivity::class.java)
                startActivity(i)
            }
        }
    }

    private fun loadTables() {
        data = this.getSharedPreferences("data", Context.MODE_PRIVATE)
        data.edit().putInt("initial", 0).apply()
        RoleHelper.loadCharacters(this)
        AdvantageHelper.loadAdvantages(this)
        RoleAdvantageHelper.loadRoleAdvantages(this)
        ChitHelper.loadChits(this)
        DevelopmentHelper.loadDeveloments(this)
        WeaponHelper.loadWeapons(this)
        ArmorHelper.loadArmor(this)
        DwellingHelper.loadDwelling(this)
        RoleDwellingHelper.loadRoleDwellings(this)
        SpellHelper.loadSpells(this)
        StartSpellHelper.loadAdvantages(this)
        TileHelper.loadTile(this)
    }

    private fun View.blink(
        times: Int = Animation.INFINITE,
        duration: Long = 1000L,
        offset: Long = 20L,
        minAlpha: Float = 0.0f,
        maxAlpha: Float = 1.0f,
        repeatMode: Int = Animation.REVERSE
    ) {
        startAnimation(AlphaAnimation(minAlpha, maxAlpha).also {
            it.duration = duration
            it.startOffset = offset
            it.repeatMode = repeatMode
            it.repeatCount = times
        })
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