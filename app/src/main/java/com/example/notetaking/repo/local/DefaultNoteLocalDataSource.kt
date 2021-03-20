package com.example.notetaking.repo.local

import com.example.notetaking.model.Result
import com.example.notetaking.repo.local.entity.NoteEntity
import com.example.notetaking.repo.local.entity.NoteNotFoundException
import com.example.notetaking.repo.local.entity.dao.NoteDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

/**
 * Implementation of [NoteLocalDataSource] interface as note data manager
 *
 * @param noteDao [NoteDao] for handling db actions
 * @param dispatcher [CoroutineDispatcher] for handling threading
 */
class DefaultNoteLocalDataSource(
    private val noteDao: NoteDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : NoteLocalDataSource {

    override fun observeNotes(): Flow<Result<List<NoteEntity>>> {
        return noteDao.observeNotes().map {
            if (it.isEmpty())
                Result.Error(NoteNotFoundException())
            else
                Result.Success(it)
        }
    }


    override suspend fun saveNote(newNote: NoteEntity) =
        withContext(dispatcher) {
            noteDao.insert(newNote)
        }

    override suspend fun getNote(id: String): Result<NoteEntity> = withContext(dispatcher) {
        return@withContext noteDao.getNote(id).run {
            if (this == null)
                Result.Error(NoteNotFoundException())
            else
                Result.Success(this)
        }
    }

    override suspend fun deleteNote(note: NoteEntity) =
        withContext(dispatcher) {
            noteDao.delete(note)
        }


}