package com.delek.lostrealm.ui.nav.player

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlayerViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "List of Characters"
    }
    val text: LiveData<String> = _text
}