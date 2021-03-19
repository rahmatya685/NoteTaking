package com.example.notetaking.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.example.notetaking.mapper.NoteEntityToNoteMapper
import com.example.notetaking.model.Note
import com.example.notetaking.repo.local.NoteLocalDataSource


/**
 * Implementation of [NoteRepo] interface for managing local database and remote api
 */
class DefaultNoteRepo(
    private val noteLocalDataSource: NoteLocalDataSource,
    private val mapper: NoteEntityToNoteMapper

) : NoteRepo {
    override fun observeNotes(): LiveData<List<Note>> =noteLocalDataSource.observeNotes().asLiveData().map {
        mapper.from(it)
    }
}