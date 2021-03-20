package com.example.notetaking.repo.local

import androidx.annotation.VisibleForTesting
import com.example.notetaking.model.Result
import com.example.notetaking.repo.local.entity.NoteEntity
import com.example.notetaking.repo.local.entity.NoteNotFoundException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.lang.Exception

class FakeNoteLocalDataSource : NoteLocalDataSource {

    var fakeData: LinkedHashMap<String, NoteEntity> = LinkedHashMap()

    private var shouldReturnError = false


    fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }


    override fun observeNotes(): Flow<Result<List<NoteEntity>>> = flowOf(getTasks())

    private fun getTasks(): Result<List<NoteEntity>> {
        if (shouldReturnError)
            return Result.Error(Exception("EmptyList"))
        return Result.Success()
    }

    override suspend fun saveNote(newNote: NoteEntity) {
        fakeData[newNote.id] = newNote
    }

    override suspend fun getNote(id: String): Result<NoteEntity> {
        val error = Result.Error(NoteNotFoundException())
        if (shouldReturnError)
            return error
        fakeData[id]?.let {
            return Result.Success()
        }
        return error
    }

    override suspend fun deleteNote(newNote: NoteEntity) {
        fakeData.remove(newNote.id)
    }

    @VisibleForTesting
    fun addNotes(fakeNotes: MutableList<NoteEntity>) {
        fakeNotes.forEach { fakeData.put(it.id, it) }
    }
}