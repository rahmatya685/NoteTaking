package com.example.notetaking.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

/**
 * Data model for a Note
 * @param title: title of the note
 * @param content: content of the note
 */
@Parcelize
data class Note @JvmOverloads constructor(
    val title: String? = null,
    val content: String
) : Parcelable {
    var id: String = UUID.randomUUID().toString()
}