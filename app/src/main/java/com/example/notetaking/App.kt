package com.example.notetaking

import android.app.Application
import com.example.notetaking.repo.NoteRepo

class App : Application() {
    val noteRepo: NoteRepo
        get() = ServiceLocator.provideNoteRepository(this)


    //TODO add Timber here
}