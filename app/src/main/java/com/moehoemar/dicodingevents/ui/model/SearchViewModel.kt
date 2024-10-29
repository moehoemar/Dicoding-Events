package com.moehoemar.dicodingevents.ui.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moehoemar.dicodingevents.data.EventRepository
import com.moehoemar.dicodingevents.data.Result
import com.moehoemar.dicodingevents.data.remote.response.ListEventsItem
import com.moehoemar.dicodingevents.util.EventWrapper


class SearchViewModel(private val eventRepository: EventRepository): ViewModel() {


    private val _listEvent = MutableLiveData<List<ListEventsItem>>()
    val listEvent: LiveData<List<ListEventsItem>> = _listEvent

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<EventWrapper<String>>()
    val errorMessage: LiveData<EventWrapper<String>> = _errorMessage


    fun fetchSearchEvent(query: String) {
        _isLoading.value = true

        eventRepository.fetchSearchEvent(query).observeForever { result ->
            when (result) {
                is Result.Loading -> _isLoading.value = true
                is Result.Success -> {
                    _isLoading.value = false
                    _listEvent.value = result.data
                }

                is Result.Error -> {
                    _isLoading.value = false
                    _errorMessage.value = EventWrapper("Error: ${result.error}")
                }
            }
        }
    }

}