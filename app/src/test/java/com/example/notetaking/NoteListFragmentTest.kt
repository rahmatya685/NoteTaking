package com.example.notetaking

import android.os.Build
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.notetaking.mapper.NoteEntityToNoteMapper
import com.example.notetaking.model.Note
import com.example.notetaking.repo.DefaultNoteRepo
import com.example.notetaking.repo.NoteRepo
import com.example.notetaking.repo.saveNoteBlocking
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import org.robolectric.annotation.TextLayoutMode
import com.example.repo.local.FakeNoteLocalDataSource
import org.hamcrest.Matchers.not
import java.util.regex.Matcher

/**
 * Integration Test for Note List Fragment
 */

@RunWith(AndroidJUnit4::class)
@MediumTest
@LooperMode(LooperMode.Mode.PAUSED)
@TextLayoutMode(TextLayoutMode.Mode.REALISTIC)
@ExperimentalCoroutinesApi
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class NoteListFragmentTest {

    lateinit var noteRepo: NoteRepo

    @Before
    fun setup() {
        noteRepo = DefaultNoteRepo(FakeNoteLocalDataSource(), NoteEntityToNoteMapper())
        ServiceLocator.noteRepo = noteRepo
    }

    @Test
    fun displayAddNoteFrg_OnBtnAddClicked() {
        //GIVEN - On the List of Note Screen
        launchActivity<MainActivity>()
        //WHEN - on btn add clicked
        onView(withId(R.id.btn_add)).perform(click())
        //THEN - display new note page
        onView(withId(R.id.root_layout)).check(
            matches(
                isDisplayed()
            )
        )
    }

    @Test
    fun showAllNotes() {
        //GIVEN - with three list of note saved in db
        noteRepo.saveNoteBlocking(Note("Title1", "Content1"))
        noteRepo.saveNoteBlocking(Note("Title", "Content"))
        noteRepo.saveNoteBlocking(Note("Title", "Content"))
        //WHEN - fragment launches
//        launchFragmentInContainer<NoteListFragment>(null,R.style.AppTheme)
        launchActivity<MainActivity>()
        //THEN - display content
        onView(withId(R.id.rv_notes)).check(matches(isDisplayed()))
        onView(withId(R.id.data_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_add)).check(matches(isDisplayed()))
        onView(withId(R.id.no_data_layout)).check(matches(not(isDisplayed())))
        onView(withText("Title1")).check(matches(isDisplayed()))
        onView(withText("Content1")).check(matches(isDisplayed()))
    }

    @Test
    fun emptyNoteList_showEmptyLayout() {
        //GIVEN - no note saved in db
        //WHEN - fragment launches
        launchActivity<MainActivity>()
        //THEN - display not item available view
        onView(withId(R.id.no_data_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.img_not_found)).check(matches(isDisplayed()))

        onView(withId(R.id.rv_notes)).check(matches(not(isDisplayed())))
        onView(withId(R.id.data_layout)).check(matches(not(isDisplayed())))

        onView(withId(R.id.btn_add)).check(matches(isDisplayed()))

        val context = ApplicationProvider.getApplicationContext<App>()
        onView(withText(context.getString(R.string.msg_error_no_item_has_been_found)))
            .check(matches(isDisplayed()))
    }


}


