package com.example.notetaking.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.example.notetaking.mapper.NoteEntityToNoteMapper
import com.example.notetaking.model.Note
import com.example.notetaking.model.Result
import com.example.notetaking.repo.local.NoteLocalDataSource


/**
 * Implementation of [NoteRepo] interface for managing local database and remote api
 */
class DefaultNoteRepo(
    private val noteLocalDataSource: NoteLocalDataSource,
    private val mapper: NoteEntityToNoteMapper

) : NoteRepo {
    override fun observeNotes(): LiveData<Result<List<Note>>> {
       return noteLocalDataSource.observeNotes().asLiveData().map {
            when (it) {
                is Result.Success -> Result.Success(mapper.from(it.data))
                is Result.Error -> Result.Error(it.e)
                is Result.Loading -> Result.Loading(it.m)
            }
        }
    }

}