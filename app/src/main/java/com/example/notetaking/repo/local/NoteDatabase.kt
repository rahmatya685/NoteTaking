package com.example.notetaking.repo.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notetaking.repo.local.entity.NoteEntity
import com.example.notetaking.repo.local.entity.dao.NoteDao

@Database(entities = [NoteEntity::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}