package com.moehoemar.dicodingevents.ui.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moehoemar.dicodingevents.data.EventRepository
import com.moehoemar.dicodingevents.di.Injection
import com.moehoemar.dicodingevents.ui.model.DetailViewModel

class DetailModelFactory private constructor(private val eventRepository: EventRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(eventRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: DetailModelFactory? = null
        fun getInstance(context: Context): DetailModelFactory =
            instance ?: synchronized(this) {
                instance ?: DetailModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }

}