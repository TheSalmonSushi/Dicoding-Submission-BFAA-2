package com.salmonboy.dicodingevent.data.remote.response

import com.google.gson.annotations.SerializedName

data class DicodingEventResponse(

    @field:SerializedName("listEvents")
    val listEvents: List<ListEventsItem>,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)


data class ListEventsItem(

    @field:SerializedName("summary")
    val summary: String,

    @field:SerializedName("imageLogo")
    val imageLogo: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int

)


data class EventDetailResponse(

    @field:SerializedName("event")
    val event: ItemEventDetailResponse,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String

)


data class ItemEventDetailResponse(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("summary")
    val summary: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("imageLogo")
    val imageLogo: String,

    @field:SerializedName("ownerName")
    val ownerName: String,

    @field:SerializedName("quota")
    val quota: Int,

    @field:SerializedName("registrants")
    val registrants: Int,

    @field:SerializedName("beginTime")
    val beginTime: String,

    @field:SerializedName("link")
    val link: String

)
