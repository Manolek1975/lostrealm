package com.delek.lostrealm.ui.nav.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MapViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is MAP Fragment"
    }
    val text: LiveData<String> = _text
}