package com.example.notetaking.note_list

import androidx.lifecycle.ViewModel
import com.example.notetaking.mapper.NoteEntityToNoteMapper
import com.example.notetaking.repo.DefaultNoteRepo
import com.example.notetaking.repo.NoteRepo
import com.example.notetaking.repo.local.FakeNoteLocalDataSource
import com.example.notetaking.repo.local.entity.NoteEntity
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class NoteListViewModelTest : TestCase(){

    //Subject under test
    lateinit var viewModel: NoteListViewModel
    lateinit var noteRep:NoteRepo

    @Before
    fun setup(){
        val fakeLocalRepo =FakeNoteLocalDataSource()
        val fakeNotes = mutableListOf<NoteEntity>()
        fakeNotes.add(NoteEntity(title = "title",content = "content"))
        fakeNotes.add(NoteEntity(title = "title",content = "content"))
        fakeNotes.add(NoteEntity(title = "title",content = "content"))
        fakeLocalRepo.addNotes(fakeNotes)

        noteRep = DefaultNoteRepo(fakeLocalRepo, NoteEntityToNoteMapper())
        viewModel = NoteListViewModel()
    }


    @Test
    fun loadNotes_LoadingTogglesAndEmitListNotes(){
        viewModel.loadNotes()
    }


}