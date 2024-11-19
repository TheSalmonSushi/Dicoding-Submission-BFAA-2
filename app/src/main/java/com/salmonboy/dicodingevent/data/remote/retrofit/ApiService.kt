package com.salmonboy.dicodingevent.data.remote.retrofit

import com.salmonboy.dicodingevent.data.remote.response.DicodingEventResponse
import com.salmonboy.dicodingevent.data.remote.response.EventDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {


    @GET("events")
    suspend fun getDicodingEvents(
        @Query("active") active: Int = 1,
        @Query("q") query: String? = null,
        @Query("limit") page: Int = 100
    ): DicodingEventResponse

    @GET("events/{id}")
    suspend fun getEventDetail(
        @Path("id") id: String
    ): EventDetailResponse
}