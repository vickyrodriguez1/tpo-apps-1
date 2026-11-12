package com.example.soundfriendlyworkplace.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.soundfriendlyworkplace.data.repository.SectorRepository

class HygieneViewModelFactory(
    private val repository: SectorRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HygieneViewModel::class.java)) {
            return HygieneViewModel(repository) as T
        }

        throw IllegalArgumentException("ViewModel desconocido")
    }
}



