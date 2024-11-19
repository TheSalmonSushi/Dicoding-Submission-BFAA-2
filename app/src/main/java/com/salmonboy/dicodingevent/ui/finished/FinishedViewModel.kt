package com.salmonboy.dicodingevent.ui.finished

import androidx.lifecycle.ViewModel
import com.salmonboy.dicodingevent.data.EventRepository

class FinishedViewModel(private val eventRepository: EventRepository) : ViewModel() {

    fun getFinishedDicodingEvent() = eventRepository.getDicodingEvent(0, 1000,"")



//    private val _listEvent = MutableLiveData<List<ListEventsItem>>()
//    val listEvent: LiveData<List<ListEventsItem>> = _listEvent
//
//    private val _filteredEvents = MutableLiveData<List<ListEventsItem>>()
//    val filteredEvents: LiveData<List<ListEventsItem>> = _filteredEvents
//
//    private val _isLoading = MutableLiveData<Boolean>()
//    val isLoading: LiveData<Boolean> = _isLoading
//
//    private val _errorMessage = MutableLiveData<String>()
//    val errorMessage: LiveData<String> = _errorMessage
//
//    companion object {
//        private const val TAG = "FinishedViewModel"
//    }

//    init {
//        showFinishedEventList()
//    }

//    private fun showFinishedEventList() {
//        _isLoading.value = true
//        val client = ApiConfig.getApiService().getDicodingEvents(active = 0)
//        client.enqueue(object : Callback<DicodingEventResponse> {
//            override fun onResponse(
//                call: Call<DicodingEventResponse>,
//                response: Response<DicodingEventResponse>
//            ) {
//                _isLoading.value = false
//                if (response.isSuccessful) {
//                    if (response.body() != null) {
//                        _listEvent.value = response.body()!!.listEvents
//                        _filteredEvents.value = response.body()!!.listEvents
//                    } else {
//                        handleError("No data found in response.")
//                    }
//                } else {
//                    handleError("Failed to load event detail: ${response.message()} (code ${response.code()})")
//                }
//            }
//
//            override fun onFailure(call: Call<DicodingEventResponse>, t: Throwable) {
//                _isLoading.value = false
//                val errorMessage = when (t) {
//                    is IOException -> "Network error: ${t.message}"
//                    else -> "Unknown error: ${t.localizedMessage}"
//                }
//                handleError(errorMessage)
//                Log.e(TAG, "onFailure: ${t.message}")
//            }
//
//        })
//    }
//
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
//
//    private fun handleError(errorMessage: String) {
//        _errorMessage.value = errorMessage
//        Log.e(TAG, errorMessage)
//    }
}