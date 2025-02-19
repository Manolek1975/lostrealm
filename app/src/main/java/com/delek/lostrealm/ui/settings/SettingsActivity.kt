package com.delek.lostrealm.ui.settings

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.delek.lostrealm.R
import com.delek.lostrealm.database.helper.DBHelper
import com.delek.lostrealm.database.model.SettingsModel
import com.delek.lostrealm.databinding.ActivitySettingsBinding
import com.delek.lostrealm.ui.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsActivity : AppCompatActivity() {

    companion object {
        const val VOLUME_LVL = "volume_lvl"
        const val KEY_VIBRATE = "key_vibrate"
    }

    private lateinit var binding: ActivitySettingsBinding
    private var initial: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemBars()
        getValues()
        initUI()

    }

    private fun getValues() {
        CoroutineScope(Dispatchers.IO).launch {
            getSettings().filter { initial }.collect { settingsModel ->
                if (settingsModel != null)
                    runOnUiThread {
                        binding.volumeBar.setValues(settingsModel.volume.toFloat())
                        binding.switchVibrate.isChecked = settingsModel.vibrate
                        initial = !initial
                    }
            }
        }
    }

    private fun initUI() {

        val audioManager = applicationContext.getSystemService(AUDIO_SERVICE) as AudioManager

        binding.volumeBar.addOnChangeListener { _, value, _ ->
            CoroutineScope(Dispatchers.IO).launch {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, value.toInt()*2, 0)
                runOnUiThread {
                    if (value.toInt() == 0) {
                        binding.iconMusic.setImageResource(R.drawable.ic_music_off)
                    } else {
                        binding.iconMusic.setImageResource(R.drawable.ic_music)
                    }
                }
                saveVolume(value.toInt())
            }
        }

        binding.soundBar.addOnChangeListener { _, value, _ ->
            if (value.toInt() == 0) {
                binding.iconSound.setImageResource(R.drawable.ic_sound_off)
            } else {
                binding.iconSound.setImageResource(R.drawable.ic_sound)
            }
        }

        binding.switchVibrate.setOnCheckedChangeListener { _, value ->
            CoroutineScope(Dispatchers.IO).launch {
                saveOptions(KEY_VIBRATE, value)
            }
        }

        binding.restart.setOnClickListener {
            showRestartDialog()
        }
    }

    private suspend fun saveVolume(value: Int) {
        dataStore.edit {
            it[intPreferencesKey(VOLUME_LVL)] = value
        }
    }

    private suspend fun saveOptions(key: String, value: Boolean) {
        dataStore.edit {
            it[booleanPreferencesKey(key)] = value
        }
    }

    private fun getSettings(): Flow<SettingsModel?> {
        return dataStore.data.map {
            SettingsModel(
                volume = it[intPreferencesKey(VOLUME_LVL)] ?: 40,
                vibrate = it[booleanPreferencesKey(KEY_VIBRATE)] ?: true
            )
        }
    }

    private fun showRestartDialog(){
        val dialogBuilder = AlertDialog.Builder(this, R.style.AppTheme_AlertDialogStyle_NoActionBar)
        val db = DBHelper(this)
        dialogBuilder.setIcon(android.R.drawable.stat_sys_warning)
        dialogBuilder.setTitle("CAUTION")
        dialogBuilder.setMessage("Creating new game, ALL data for the current game will be deleted. Do you want to continue?")
        dialogBuilder.setNegativeButton("NO") { _, _ -> }
        dialogBuilder.setPositiveButton("DELETE") { _, _: Int ->
            db.onDelete()
            getSharedPreferences("data", 0).edit().clear().apply()
            val i = Intent(this, MainActivity::class.java)
            startActivity(i) // To Main Activity
        }.show()
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