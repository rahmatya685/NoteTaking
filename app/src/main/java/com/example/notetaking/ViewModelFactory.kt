package com.example.notetaking

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.notetaking.note_list.NoteListViewModel
import com.example.notetaking.repo.NoteRepo
import java.lang.IllegalStateException


/**
 * A factory for all ViewModels
 */
class ViewModelFactory constructor(
    private val repo: NoteRepo,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle?
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ) =with(modelClass){
        when{
            isAssignableFrom(NoteListViewModel::class.java) -> NoteListViewModel(repo,handle)
            else -> throw IllegalStateException("Unknown ViewModel class for $name")
        }
    } as T
}