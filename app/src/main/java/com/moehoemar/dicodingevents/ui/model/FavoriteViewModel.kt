package com.moehoemar.dicodingevents.ui.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moehoemar.dicodingevents.data.EventRepository
import com.moehoemar.dicodingevents.data.local.entity.EventEntity

class FavoriteViewModel(private val eventRepository: EventRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    suspend fun getEventFavorite() = eventRepository.getEventFavorite()

    suspend fun deleteEvent(event: EventEntity) {
        eventRepository.setEventFavorite(event, false)
    }

    suspend fun saveEvent(event: EventEntity) {
        eventRepository.setEventFavorite(event, true)
    }

}