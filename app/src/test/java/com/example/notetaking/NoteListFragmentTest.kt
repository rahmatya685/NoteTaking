package com.example.notetaking

import android.os.Build
import androidx.fragment.app.testing.FragmentScenario.launch
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import junit.framework.TestCase
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.notetaking.add_note.AddNoteFragment
import com.example.notetaking.note_list.NoteListFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.apache.tools.ant.Main
import org.junit.Test
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import org.robolectric.annotation.TextLayoutMode

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

    @Test
    fun displayAddNoteFrg_OnBtnAddClicked() {
        //GIVEN - On the List of Note Screen
        launchActivity<MainActivity>()
        //WHEN - on btn add clicked
        onView(withId(R.id.btn_add)).perform(click())
        //THEN - display new note page
        onView(withId(R.id.root_layout)).check(
            ViewAssertions.matches(
                isDisplayed()
            )
        )
    }
}
