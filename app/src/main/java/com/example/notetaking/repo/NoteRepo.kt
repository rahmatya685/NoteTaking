package com.example.notetaking.repo

import androidx.lifecycle.LiveData
import com.example.notetaking.model.Note

/**
 * Main entry for note data layer
 */
interface NoteRepo {
    fun observeNotes(): LiveData<List<Note>>
}