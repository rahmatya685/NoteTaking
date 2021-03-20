package com.example.notetaking.repo

import com.example.notetaking.model.Note
import kotlinx.coroutines.runBlocking

fun NoteRepo.saveNoteBlocking(note: Note) = runBlocking {
    this@saveNoteBlocking.saveNote(note)
}