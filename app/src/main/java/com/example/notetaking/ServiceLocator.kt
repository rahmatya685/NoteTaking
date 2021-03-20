package com.example.notetaking

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notetaking.mapper.NoteEntityToNoteMapper
import com.example.notetaking.repo.DefaultNoteRepo
import com.example.notetaking.repo.NoteRepo
import com.example.notetaking.repo.local.DefaultNoteLocalDataSource
import com.example.notetaking.repo.local.NoteDatabase
import com.example.notetaking.repo.local.NoteLocalDataSource

/**
 * dependency provide for [NoteRepo] class
 */
object ServiceLocator {

    private val lock = Any()
    private var database: NoteDatabase? = null


    @Volatile
    var noteRepo: NoteRepo? = null
        @VisibleForTesting set

    fun provideNoteRepository(context: Context): NoteRepo {
        synchronized(this) {
            return noteRepo ?: createNoteRepo(context)
        }
    }

    private fun createNoteRepo(context: Context): NoteRepo {
        val noteRepo = DefaultNoteRepo(createNoteLocalDataSource(context), NoteEntityToNoteMapper())
        this.noteRepo = noteRepo
        return noteRepo
    }


    private fun createNoteLocalDataSource(context: Context): NoteLocalDataSource {
        val db = database ?: createDb(context)
        return DefaultNoteLocalDataSource(db.noteDao())
    }

    private fun createDb(context: Context): NoteDatabase {
        val newDb = Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            DB_NAME
        ).build()
        database = newDb
        return newDb
    }


}

private const val DB_NAME = "Notes.db"

