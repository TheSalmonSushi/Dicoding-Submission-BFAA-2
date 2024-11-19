package com.salmonboy.dicodingevent.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.salmonboy.dicodingevent.data.local.entity.EventDetailEntity
import com.salmonboy.dicodingevent.data.local.entity.EventEntity
import com.salmonboy.dicodingevent.data.local.room.EventDao
import com.salmonboy.dicodingevent.data.remote.retrofit.ApiService

class EventRepository private constructor(
    private val apiService: ApiService,
    private val eventDao: EventDao
) {


    suspend fun setBookmark(eventId: Int, bookmarkState: Boolean) {
        eventDao.updateEventDetailBookmark(eventId, bookmarkState) // Update in detail entity
        eventDao.updateEventBookmark(eventId, bookmarkState) // Update in list entity
    }

    fun getBookmarkedEvents(): LiveData<Result<List<EventEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val bookmarkedEvents = eventDao.getBookmarkedEvents()
            emitSource(bookmarkedEvents.map { Result.Success(it) })
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getDicodingEvent(active: Int, page: Int, query: String): LiveData<Result<List<EventEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getDicodingEvents(active, query, page)
            val events = response.listEvents
            val eventList = events.map { event ->
                val isBookmarked = eventDao.isEventBookmarked(event.id)
                EventEntity(
                    event.id,
                    event.name,
                    event.imageLogo,
                    active,
                    isBookmarked,
                    page,
                    query
                )
            }
            eventDao.insertEvents(eventList)
        } catch (e: Exception) {
            Log.d("EventRepo", "getUpcomingDicodingEvent: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
        val localData: LiveData<Result<List<EventEntity>>> = eventDao.getEventsByTypeQueryPage(active, query, page).map { Result.Success(it) }
        emitSource(localData)
    }

    fun getDetailDicodingEvent(id: String): LiveData<Result<EventDetailEntity>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getEventDetail(id)
            val event = response.event
            val isBookmarked = eventDao.isEventBookmarked(event.id)
            val eventDetailEntity = EventDetailEntity(
                event.id,
                event.name,
                event.description,
                event.imageLogo,
                event.ownerName,
                event.quota,
                event.registrants,
                event.beginTime,
                event.link,
                isBookmarked
            )
            eventDao.insertEventDetail(eventDetailEntity)
            emit(Result.Success(eventDetailEntity))
        } catch (e: Exception) {
            Log.d("EventRepo", "getDetailDicodingEvent: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
        val localData: LiveData<Result<EventDetailEntity>> = eventDao.getEventDetailById(id.toInt()).map { Result.Success(it) }
        emitSource(localData)
    }

    companion object {
        @Volatile
        private var instance: EventRepository? = null
        fun getInstance(
            apiService: ApiService,
            eventDao: EventDao
        ): EventRepository =
            instance ?: synchronized(this) {
                instance ?: EventRepository(apiService, eventDao)
            }.also { instance = it }
    }
}