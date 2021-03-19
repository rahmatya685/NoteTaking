package com.example.notetaking.repo.local

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.runner.AndroidJUnitRunner
import com.example.notetaking.model.Result
import com.example.notetaking.model.succeeded
import com.example.notetaking.repo.local.entity.NoteEntity
import com.example.notetaking.repo.local.entity.NoteNotFoundException
import com.google.common.truth.ExpectFailure
import com.google.common.truth.Truth
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

/**
 * Integration test for [NoteLocalDataSource]
 */
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class DefaultNoteLocalDataSourceTest : TestCase() {
    private lateinit var database: NoteDatabase
    private lateinit var dataSource: NoteLocalDataSource


    /**
     *  Executes each task synchronously using Architecture Components such as Room
     *  Removing this will throw Job has not completed yet exception
     *  since Room is under construction on Background thread
     */
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NoteDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        dataSource = DefaultNoteLocalDataSource(
            database.noteDao(),
            Dispatchers.Main
        )
    }

    @After
    fun cleanUp() {
        database.close()
        Dispatchers.resetMain()
    }


    @Test
    fun saveNote_retrieveNote() = runBlockingTest {
        //GIVEN - a new note saved in the database
        val title = "SampleTitle"
        val content = "testContent"
        val newNote = NoteEntity(title = title, content = content)
        dataSource.saveNote(newNote)
        //WHEN - Note retrieved by ID
        val result = dataSource.getNote(newNote.id)
        //THEN - Same note is returned
        Truth.assertThat(result).isInstanceOf(Result.Success::class.java)
        val savedNote = result as Result.Success
        assertThat(savedNote.data.title, `is`(title))
        assertThat(savedNote.data.content, `is`(content))
    }

    @Test
    fun deleteNote_retrieveNote() = runBlockingTest {
        //GIVEN - a note inserted and deleted
        val title = "SampleTitle"
        val content = "testContent"
        val newNote = NoteEntity(title = title, content = content)
        dataSource.saveNote(newNote)
        dataSource.deleteNote(newNote)
        //WHEN - retrieving deleted note
        val result = dataSource.getNote(newNote.id)
        //THEN - Note not found exception is expected
        Truth.assertThat(result).isInstanceOf(Result.Error::class.java)
        val error = result as Result.Error
        Truth.assertThat(error.e).isInstanceOf(NoteNotFoundException::class.java)
    }


}