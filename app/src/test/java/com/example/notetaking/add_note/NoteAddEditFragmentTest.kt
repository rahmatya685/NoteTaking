package com.example.notetaking.add_note

import android.os.Build
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.notetaking.R
import com.example.notetaking.ServiceLocator
import com.example.notetaking.mapper.NoteEntityToNoteMapper
import com.example.notetaking.model.Note
import com.example.notetaking.repo.DefaultNoteRepo
import com.example.notetaking.repo.NoteRepo
import com.example.notetaking.repo.local.DefaultNoteLocalDataSource
import com.example.notetaking.repo.saveNoteBlocking
import com.example.repo.local.FakeNoteLocalDataSource
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import org.robolectric.annotation.TextLayoutMode

/**
 * Integration task for [NoteAddEditFragment] fragment
 */
@MediumTest
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
@LooperMode(LooperMode.Mode.PAUSED)
@TextLayoutMode(TextLayoutMode.Mode.REALISTIC)
@Config(sdk = [Build.VERSION_CODES.JELLY_BEAN_MR2])
class NoteAddEditFragmentTest {

    lateinit var noteRepo: NoteRepo

    @Before
    fun setup() {
        noteRepo = DefaultNoteRepo(FakeNoteLocalDataSource(), NoteEntityToNoteMapper())
        ServiceLocator.noteRepo = noteRepo
    }

//    @Test
//    fun noteDetailForUpdate_showInUi() {
//        //GIVEN - save a note on db
//        noteRepo.saveNoteBlocking(Note("Title", "Content"))
//        //WHEN - launch fragment
//        launchFragmentInContainer<NoteAddEditFragment>(null, R.style.AppTheme)
//        //THEN - display info in ui
//        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
//        onView(withId(R.id.tv_content)).check(matches(isDisplayed()))
//    }


}