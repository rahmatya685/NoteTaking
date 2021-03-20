package com.example.notetaking.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.example.notetaking.mapper.NoteEntityToNoteMapper
import com.example.notetaking.model.Note
import com.example.notetaking.model.Result
import com.example.notetaking.model.succeeded
import com.example.notetaking.repo.local.NoteLocalDataSource
import com.example.notetaking.repo.local.entity.NoteNotFoundException
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.lang.Exception
import java.util.*


/**
 * Implementation of [NoteRepo] interface for managing local database and remote api
 */
class DefaultNoteRepo(
    private val noteLocalDataSource: NoteLocalDataSource,
    private val mapper: NoteEntityToNoteMapper
) : NoteRepo {
    override fun observeNotes(): LiveData<Result<List<Note>>> =
        noteLocalDataSource.observeNotes().asLiveData().map {
            when (it) {
                is Result.Success -> Result.Success(mapper.from(it.data))
                is Result.Error -> Result.Error(it.e)
                else -> Result.Error(NoteNotFoundException())
            }
        }

    override suspend fun saveNote(note: Note) {
        noteLocalDataSource.saveNote(mapper.to(note))
    }

}