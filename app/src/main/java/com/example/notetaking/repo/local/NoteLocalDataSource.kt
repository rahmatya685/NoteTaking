package com.example.notetaking.repo.local

import com.example.notetaking.model.Result
import com.example.notetaking.repo.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

/**
 * Local entry for the notes in db
 */
interface NoteLocalDataSource {
    fun observeNotes(): Flow<Result<List<NoteEntity>>>
    suspend fun saveNote(newNote: NoteEntity)
    suspend fun getNote(id: String): Result<NoteEntity>
    suspend fun deleteNote(newNote: NoteEntity)
}