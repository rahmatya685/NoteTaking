package com.example.notetaking.repo.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "NOTE")
class NoteEntity(

    @PrimaryKey
    @ColumnInfo(name = "ID", typeAffinity = ColumnInfo.TEXT)
    var id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "TITLE", typeAffinity = ColumnInfo.TEXT)
    val title: String? = null,

    @ColumnInfo(name = "CONTENT", typeAffinity = ColumnInfo.TEXT)
    val content: String
)