package com.delek.lostrealm.ui.nav.settings

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity.AUDIO_SERVICE
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import com.delek.lostrealm.R
import com.delek.lostrealm.database.helper.DBHelper
import com.delek.lostrealm.database.model.SettingsModel
import com.delek.lostrealm.databinding.FragmentSettingsBinding
import com.delek.lostrealm.ui.main.MainActivity
import com.delek.lostrealm.ui.settings.SettingsActivity
import com.delek.lostrealm.ui.settings.dataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsFragment : Fragment() {

    companion object {
        const val VOLUME_LVL = "volume_lvl"
        const val KEY_VIBRATE = "key_vibrate"
    }

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private var initial: Boolean = true
    private lateinit var context: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //val settingsViewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        context = requireContext()
        getValues()
        initUI()

        return root
    }

    private fun initUI() {

        val audioManager = context.applicationContext.getSystemService(AUDIO_SERVICE) as AudioManager

        binding.volumeBar.addOnChangeListener { _, value, _ ->
            CoroutineScope(Dispatchers.IO).launch {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, value.toInt()*2, 0)
                activity?.runOnUiThread {
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
                saveOptions(SettingsActivity.KEY_VIBRATE, value)
            }
        }

        binding.restart.setOnClickListener {
            showRestartDialog()
        }
    }

    private fun getValues() {
        CoroutineScope(Dispatchers.IO).launch {
            getSettings().filter { initial }.collect { settingsModel ->
                if (settingsModel != null)
                    activity?.runOnUiThread {
                        binding.volumeBar.setValues(settingsModel.volume.toFloat())
                        binding.switchVibrate.isChecked = settingsModel.vibrate
                        initial = !initial
                    }
            }
        }
    }

    private fun getSettings(): Flow<SettingsModel?> {
        return requireContext().dataStore.data.map {
            SettingsModel(
                volume = it[intPreferencesKey(VOLUME_LVL)] ?: 40,
                vibrate = it[booleanPreferencesKey(KEY_VIBRATE)] ?: true
            )
        }
    }

    private suspend fun saveVolume(value: Int) {
        requireContext().dataStore.edit {
            it[intPreferencesKey(SettingsActivity.VOLUME_LVL)] = value
        }
    }

    private suspend fun saveOptions(key: String, value: Boolean) {
        requireContext().dataStore.edit {
            it[booleanPreferencesKey(key)] = value
        }
    }

    private fun showRestartDialog(){
        val dialogBuilder = AlertDialog.Builder(context, R.style.AppTheme_AlertDialogStyle_NoActionBar)
        val db = DBHelper(context)
        dialogBuilder.setIcon(android.R.drawable.stat_sys_warning)
        dialogBuilder.setTitle("CAUTION")
        dialogBuilder.setMessage("Creating new game, ALL data for the current game will be deleted. Do you want to continue?")
        dialogBuilder.setNegativeButton("NO") { _, _ -> }
        dialogBuilder.setPositiveButton("DELETE") { _, _: Int ->
            db.onDelete()
            val i = Intent(activity, MainActivity::class.java)
            MainActivity.stopPlayer()
            startActivity(i) // To Main Activity
        }.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}