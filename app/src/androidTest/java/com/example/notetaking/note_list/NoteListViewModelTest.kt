@file:Suppress("UNCHECKED_CAST")

package com.example.notetaking.note_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.example.notetaking.MainCoroutineRule
import com.example.notetaking.getOrAwaitValue
import com.example.notetaking.mapper.NoteEntityToNoteMapper
import com.example.notetaking.observeForTesting
import com.example.notetaking.repo.DefaultNoteRepo
import com.example.notetaking.repo.NoteRepo
import com.example.notetaking.repo.local.FakeNoteLocalDataSource
import com.example.notetaking.repo.local.entity.NoteEntity
import junit.framework.Assert.*
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


@ExperimentalCoroutinesApi
class NoteListViewModelTest {

    //Subject under test
    lateinit var viewModel: NoteListViewModel
    lateinit var noteRep: NoteRepo


    @ExperimentalCoroutinesApi
    @get:Rule
    val instantRuleExecution=InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutinesRule=MainCoroutineRule()

    @Before
    fun setup() {
        val fakeLocalRepo = FakeNoteLocalDataSource()
        val fakeNotes = mutableListOf<NoteEntity>()
        fakeNotes.add(NoteEntity(title = "title", content = "content"))
        fakeNotes.add(NoteEntity(title = "title", content = "content"))
        fakeNotes.add(NoteEntity(title = "title", content = "content"))
        fakeLocalRepo.addNotes(fakeNotes)

        noteRep = DefaultNoteRepo(fakeLocalRepo, NoteEntityToNoteMapper())
        viewModel = NoteListViewModel(noteRep, SavedStateHandle())
    }


    @Test
    fun loadNotes_LoadingTogglesAndEmitListNotes() {
        // Pause dispatcher so we can verify initial values
        mainCoroutinesRule.pauseDispatcher()

        //start loading of tasks
        viewModel.loadNotes()

        viewModel.notes.observeForTesting {

            //set to loading state
            assertTrue(viewModel.isLoading.getOrAwaitValue())

            // Execute pending coroutines actions
            mainCoroutinesRule.resumeDispatcher()

            //stop loading state
            assertFalse(viewModel.isLoading.getOrAwaitValue())

            //check size of note list
            assertEquals(viewModel.notes.getOrAwaitValue().size, 3)
        }
    }


}
