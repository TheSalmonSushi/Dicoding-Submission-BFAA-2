package com.salmonboy.dicodingevent.ui.home

import androidx.lifecycle.ViewModel
import com.salmonboy.dicodingevent.data.EventRepository

class HomeViewModel(private val eventRepository: EventRepository) : ViewModel() {

    fun getUpcomingDicodingEvent() = eventRepository.getDicodingEvent(1, 5, "")

    fun getFinishedDicodingEvent() = eventRepository.getDicodingEvent(0, 5, "")


//    private val _finishedEvents = MutableLiveData<List<ListEventsItem>>()
//    val finishedEvents: LiveData<List<ListEventsItem>> = _finishedEvents
//
//    private val _upcomingEvents = MutableLiveData<List<ListEventsItem>>()
//    val upcomingEvents: LiveData<List<ListEventsItem>> = _upcomingEvents
//
//    private val _isFinishedLoading = MutableLiveData<Boolean>()
//    val isFinishedLoading: LiveData<Boolean> = _isFinishedLoading
//
//    private val _isUpcomingLoading = MutableLiveData<Boolean>()
//    val isUpcomingLoading: LiveData<Boolean> = _isUpcomingLoading
//
//    private val _errorMessage = MutableLiveData<String>()
//    val errorMessage: LiveData<String> = _errorMessage
//
//
//    companion object {
//        private const val TAG = "HomeViewModel"
//    }
//
//    init {
//        loadFinishedEvents()
//        loadUpcomingEvents()
//    }

//    private fun loadFinishedEvents() {
//        _isFinishedLoading.value = true
//        val client = ApiConfig.getApiService().getDicodingEvents(active = 0, page = 5)
//        client.enqueue(object : Callback<DicodingEventResponse> {
//            override fun onResponse(
//                call: Call<DicodingEventResponse>,
//                response: Response<DicodingEventResponse>
//            ) {
//                _isFinishedLoading.value = false
//                if (response.isSuccessful) {
//                    if (response.body() != null) {
//                        _finishedEvents.value = response.body()!!.listEvents
//                    } else {
//                        handleError("No data found in response.")
//                    }
//                } else {
//                    handleError("Failed to load event detail: ${response.message()} (code ${response.code()})")
//                }
//            }
//
//            override fun onFailure(call: Call<DicodingEventResponse>, t: Throwable) {
//                _isFinishedLoading.value = false
//                val errorMessage = when (t) {
//                    is IOException -> "Network error: ${t.message}"
//                    else -> "Unknown error: ${t.localizedMessage}"
//                }
//                handleError(errorMessage)
//                Log.e(TAG, "onFailure: ${t.message}")
//            }
//        })
//    }

//    private fun loadUpcomingEvents() {
//        _isUpcomingLoading.value = true
//        val client = ApiConfig.getApiService().getDicodingEvents(page = 5)
//        client.enqueue(object : Callback<DicodingEventResponse> {
//            override fun onResponse(
//                call: Call<DicodingEventResponse>,
//                response: Response<DicodingEventResponse>
//            ) {
//                _isUpcomingLoading.value = false
//                if (response.isSuccessful) {
//                    if (response.body() != null) {
//                        _upcomingEvents.value = response.body()!!.listEvents
//                    } else {
//                        handleError("No data found in response.")
//                    }
//                } else {
//                    handleError("Failed to load event detail: ${response.message()} (code ${response.code()})")
//                }
//            }
//
//            override fun onFailure(call: Call<DicodingEventResponse>, t: Throwable) {
//                _isUpcomingLoading.value = false
//                val errorMessage = when (t) {
//                    is IOException -> "Network error: ${t.message}"
//                    else -> "Unknown error: ${t.localizedMessage}"
//                }
//                handleError(errorMessage)
//                Log.e(TAG, "onFailure: ${t.message}")
//            }
//        })
//    }
//
//    private fun handleError(errorMessage: String) {
//        _errorMessage.value = errorMessage
//        Log.e(TAG, errorMessage)
//    }
}