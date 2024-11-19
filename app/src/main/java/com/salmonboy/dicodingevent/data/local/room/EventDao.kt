package com.salmonboy.dicodingevent.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.salmonboy.dicodingevent.data.local.entity.EventDetailEntity
import com.salmonboy.dicodingevent.data.local.entity.EventEntity

@Dao
interface EventDao {

    @Query("SELECT * FROM event_details WHERE id = :id")
    fun getEventDetailById(id: Int): LiveData<EventDetailEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEventDetail(eventDetailEntity: EventDetailEntity)

    @Query("UPDATE event_details SET bookmarked = :bookmarkState WHERE id = :id")
    suspend fun updateEventDetailBookmark(id: Int, bookmarkState: Boolean)

    @Query("UPDATE event SET bookmarked = :bookmarkState WHERE id = :id")
    suspend fun updateEventBookmark(id: Int, bookmarkState: Boolean)

    @Query("SELECT * FROM event WHERE bookmarked = 1")
    fun getBookmarkedEvents(): LiveData<List<EventEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEvents(events: List<EventEntity>)

    @Update
    suspend fun updateEventDetail(eventDetailEntity: EventDetailEntity)


    @Query("DELETE FROM event_details WHERE bookmarked = 0")
    suspend fun deleteAll()

    @Query("DELETE FROM event WHERE type = :type AND `query` = :query AND page = :page")
    suspend fun deleteEventsByTypeAndQuery(type: Int, query: String, page: Int)

    @Query("SELECT EXISTS(SELECT * FROM event_details WHERE id = :id AND bookmarked = 1)")
    suspend fun isEventBookmarked(id: Int): Boolean

    @Query("SELECT * FROM event WHERE type = :type AND `query` = :query AND page = :page")
    fun getEventsByTypeQueryPage(type: Int, query: String, page: Int): LiveData<List<EventEntity>>
}