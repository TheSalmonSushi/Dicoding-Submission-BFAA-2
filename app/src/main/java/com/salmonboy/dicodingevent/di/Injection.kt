package com.salmonboy.dicodingevent.di

import android.content.Context
import com.salmonboy.dicodingevent.data.EventRepository
import com.salmonboy.dicodingevent.data.local.room.EventDatabase
import com.salmonboy.dicodingevent.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): EventRepository {
        val apiService = ApiConfig.getApiService()
        val database = EventDatabase.getInstance(context)
        val dao = database.eventDao()
        return EventRepository.getInstance(apiService, dao)
    }
}