package com.moehoemar.dicodingevents.ui.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moehoemar.dicodingevents.data.EventRepository
import com.moehoemar.dicodingevents.di.Injection
import com.moehoemar.dicodingevents.ui.model.FinishedViewModel

class FinishedModelFactory private constructor(private val eventRepository: EventRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FinishedViewModel::class.java)) {
            return FinishedViewModel(eventRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: FinishedModelFactory? = null
        fun getInstance(context: Context): FinishedModelFactory =
            instance ?: synchronized(this) {
                instance ?: FinishedModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}