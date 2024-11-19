package com.salmonboy.dicodingevent.ui.upcoming

import androidx.lifecycle.ViewModel
import com.salmonboy.dicodingevent.data.EventRepository

class UpcomingViewModel(private val eventRepository: EventRepository) : ViewModel() {


    fun getUpcomingDicodingEvent() = eventRepository.getDicodingEvent(1, 100, "")


//    fun filterEvents(query: String) {
//        val events = _listEvent.value ?: return
//        _filteredEvents.value = if (query.isEmpty()) {
//            events
//        } else {
//            events.filter { event ->
//                event.name.contains(query, ignoreCase = true)
//            }
//        }
//    }

}