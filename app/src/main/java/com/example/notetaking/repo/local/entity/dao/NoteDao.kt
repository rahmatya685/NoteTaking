package com.example.notetaking.repo.local.entity.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.notetaking.repo.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the Note table
 */
@Dao
interface NoteDao : BaseDao<NoteEntity> {

    /**
     * Observe the list of Notes
     *
     * @return [Flow] all notes
     */
    @Query("SELECT * from NOTE")
    fun observeNotes(): Flow<List<NoteEntity>>


    /**
     * fetch all notes from table
     *
     * @return  all notes
     */
    @Query("SELECT * from NOTE")
    suspend fun getAllNotes(): List<NoteEntity>

    @Query("SELECT * from NOTE WHERE ID like :id")
    suspend fun getNote(id: String): NoteEntity?


}