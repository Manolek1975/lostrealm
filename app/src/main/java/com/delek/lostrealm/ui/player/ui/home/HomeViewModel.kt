package com.delek.lostrealm.ui.player.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "List of Characters"
    }
    val text: LiveData<String> = _text
}