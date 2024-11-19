package com.salmonboy.dicodingevent.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event")
class EventEntity (
    @field:ColumnInfo(name = "id")
    @field:PrimaryKey
    val id: Int,

    @field:ColumnInfo(name = "name")
    val name: String,

    @field:ColumnInfo(name = "imageLogo")
    val imageLogo: String,

    @field:ColumnInfo(name = "type")
    val type: Int,

    @field:ColumnInfo(name = "bookmarked")
    var isBookmarked: Boolean = false, // Manage bookmark state here

    @field:ColumnInfo(name = "page")
    val page: Int, // Page number for pagination

    @field:ColumnInfo(name = "query")
    val query: String // Query string for filtering
)

@Entity(tableName = "event_details")
class EventDetailEntity (
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "image_logo")
    val imageLogo: String,

    @ColumnInfo(name = "owner_name")
    val ownerName: String,

    @ColumnInfo(name = "quota")
    val quota: Int,

    @ColumnInfo(name = "registrants")
    val registrants: Int,

    @ColumnInfo(name = "begin_time")
    val beginTime: String,

    @ColumnInfo(name = "link")
    val link: String,

    @field:ColumnInfo(name = "bookmarked")
    var isBookmarked: Boolean = false,
)

