package com.example.notetaking.repo

import androidx.lifecycle.LiveData
import com.example.notetaking.model.Note
import com.example.notetaking.model.Result

/**
 * Main entry for note data layer
 */
interface NoteRepo {
    fun observeNotes(): LiveData<Result<List<Note>>>
}