package com.salmonboy.dicodingevent.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salmonboy.dicodingevent.data.EventRepository
import kotlinx.coroutines.launch

class EventDetailViewModel(private val eventRepository: EventRepository) : ViewModel() {

    fun getDetailDicodingEvent(id: String) = eventRepository.getDetailDicodingEvent(id)

    fun toggleBookmark(eventId: Int, isBookmarked: Boolean) {
        viewModelScope.launch {
            eventRepository.setBookmark(eventId, isBookmarked)
        }
    }

//    private val _eventDetail = MutableLiveData<ItemEventDetailResponse>()
//    val eventDetail: LiveData<ItemEventDetailResponse> = _eventDetail
//
//    private val _isLoading = MutableLiveData<Boolean>()
//    val isLoading: LiveData<Boolean> = _isLoading
//
//    private val _errorMessage = MutableLiveData<String>()
//    val errorMessage: LiveData<String> = _errorMessage
//
//    companion object {
//        private const val TAG = "EventDetailViewModel"
//    }

//    fun fetchEventDetail(eventId: String) {
//        _isLoading.value = true
//        val client = ApiConfig.getApiService().getEventDetail(eventId)
//        client.enqueue(object : Callback<EventDetailResponse> {
//            override fun onResponse(
//                call: Call<EventDetailResponse>,
//                response: Response<EventDetailResponse>
//            ) {
//                _isLoading.value = false
//                if (response.isSuccessful) {
//                    if (response.body() != null) {
//                        _eventDetail.value = response.body()!!.event
//                    } else {
//                        handleError("No data found in response.")
//                    }
//                } else {
//                    handleError("Failed to load event detail: ${response.message()} (code ${response.code()})")
//                }
//            }
//
//            override fun onFailure(call: Call<EventDetailResponse>, t: Throwable) {
//                _isLoading.value = false
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