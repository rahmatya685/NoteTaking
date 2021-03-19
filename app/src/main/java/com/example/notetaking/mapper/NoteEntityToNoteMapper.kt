package com.example.notetaking.mapper

import com.example.notetaking.model.Note
import com.example.notetaking.repo.local.entity.NoteEntity

class NoteEntityToNoteMapper:Mapper<NoteEntity,Note>() {
    override fun from(i: NoteEntity): Note = Note(
        title = i.title,
        content = i.content
    )
}