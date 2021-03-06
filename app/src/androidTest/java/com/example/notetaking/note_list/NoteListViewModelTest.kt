@file:Suppress("UNCHECKED_CAST")

package com.example.notetaking.note_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.example.notetaking.MainCoroutineRule
import com.example.notetaking.R
import com.example.notetaking.getOrAwaitValue
import com.example.notetaking.mapper.NoteEntityToNoteMapper
import com.example.notetaking.observeForTesting
import com.example.notetaking.repo.DefaultNoteRepo
import com.example.notetaking.repo.NoteRepo
import com.example.notetaking.repo.local.entity.NoteEntity
import junit.framework.Assert.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.example.repo.local.FakeNoteLocalDataSource


@ExperimentalCoroutinesApi
class NoteListViewModelTest {

    //Subject under test
    lateinit var viewModel: NoteListViewModel
    lateinit var noteRep: NoteRepo
    lateinit var fakeLocalRepo: FakeNoteLocalDataSource


    @ExperimentalCoroutinesApi
    @get:Rule
    val instantRuleExecution = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutinesRule = MainCoroutineRule()

    @Before
    fun setup() {
        fakeLocalRepo = FakeNoteLocalDataSource()
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


    @Test
    fun loadNotes_error() {
        fakeLocalRepo.setReturnError(true)

        viewModel.loadNotes()

        viewModel.notes.observeForTesting {

            //loading state should be false
            assertFalse(viewModel.isLoading.getOrAwaitValue())

            //No note should be emitted
            assertEquals(viewModel.notes.getOrAwaitValue().size, 0)

            assertEquals(
                viewModel.error.getOrAwaitValue().getContentIfNotHandled(),
                R.string.msg_error_no_item_has_been_found
            )

        }
    }

}
