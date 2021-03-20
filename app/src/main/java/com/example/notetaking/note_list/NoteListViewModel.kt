package com.example.notetaking.note_list

import androidx.lifecycle.*
import com.example.notetaking.model.Note
import com.example.notetaking.model.Result
import com.example.notetaking.repo.DefaultNoteRepo
import com.example.notetaking.repo.NoteRepo

class NoteListViewModel(noteRep: NoteRepo, savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _notes: LiveData<List<Note>> =
        noteRep.observeNotes().distinctUntilChanged().map {
            when(it){
                is Result.Success-> it.data
                is Result.Error -> {

                    emptyList()
                }
                else -> emptyList()
            }
        }
    val notes: LiveData<List<Note>>
        get() = _notes

    fun loadNotes() {

    }
}