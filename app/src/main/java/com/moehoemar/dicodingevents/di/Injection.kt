package com.moehoemar.dicodingevents.di

import android.content.Context
import com.moehoemar.dicodingevents.data.EventRepository
import com.moehoemar.dicodingevents.data.local.room.EventDatabase
import com.moehoemar.dicodingevents.data.remote.retrofit.ApiConfig
import com.moehoemar.dicodingevents.util.AppExecutors

object Injection {
    fun provideRepository(context: Context): EventRepository {
        val apiService = ApiConfig.getApiService()
        val database = EventDatabase.getInstance(context)
        val dao = database.eventDao()
        val appExecutors = AppExecutors()
        return EventRepository.getInstance(apiService, dao, appExecutors)
    }
}