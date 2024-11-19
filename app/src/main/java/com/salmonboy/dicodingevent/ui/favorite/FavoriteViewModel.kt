package com.salmonboy.dicodingevent.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.salmonboy.dicodingevent.data.EventRepository
import com.salmonboy.dicodingevent.data.Result
import com.salmonboy.dicodingevent.data.local.entity.EventEntity

class FavoriteViewModel(private val eventRepository: EventRepository): ViewModel() {

    fun getBookmarkedEvents(): LiveData<Result<List<EventEntity>>> {
        return eventRepository.getBookmarkedEvents()
    }
}