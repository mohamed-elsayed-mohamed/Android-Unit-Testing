package com.alpha.unittesting;

import android.database.sqlite.SQLiteConstraintException;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.alpha.unittesting.models.Note;
import com.alpha.unittesting.utils.LiveDataTestUtil;
import com.alpha.unittesting.utils.TestUtil;

import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class NoteDaoTest extends NoteDatabaseTest {

    public static final String TEST_TITLE = "This is a test title";
    public static final String TEST_CONTENT = "This is some test content";
    public static final String TEST_TIMESTAMP = "12/2021";

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    // Insert, Read, Delete
    @Test
    public void insertReadDelete() throws Exception{
        Note note = new Note(TestUtil.TEST_NOTE_1);

        // Insert
        getNoteDao().insertNote(note).blockingGet(); // wait until insert

        // Read
        LiveDataTestUtil<List<Note>> liveDataTestUtil = new LiveDataTestUtil<>();
        List<Note> insertedNotes = liveDataTestUtil.getValue(getNoteDao().getNotes());

        assertNotNull(insertedNotes);
        assertEquals(insertedNotes.size(), 1);
        assertEquals(note.getContent(), insertedNotes.get(0).getContent());
        assertEquals(note.getTimestamp(), insertedNotes.get(0).getTimestamp());
        assertEquals(note.getTitle(), insertedNotes.get(0).getTitle());

        note.setId(insertedNotes.get(0).getId());
        assertEquals(note, insertedNotes.get(0));

        // Delete
        getNoteDao().deleteNote(note).blockingGet();

        // Confirm the database is empty
        insertedNotes = liveDataTestUtil.getValue(getNoteDao().getNotes());
        assertEquals(insertedNotes.size(), 0);
    }

    // Insert, Read, Update, Read, Delete
    @Test
    public void insertReadUpdateReadDelete() throws Exception{
        Note note = new Note(TestUtil.TEST_NOTE_1);

        // Insert
        getNoteDao().insertNote(note).blockingGet(); // wait until insert

        // Read
        LiveDataTestUtil<List<Note>> liveDataTestUtil = new LiveDataTestUtil<>();
        List<Note> insertedNotes = liveDataTestUtil.getValue(getNoteDao().getNotes());

        assertNotNull(insertedNotes);
        assertEquals(insertedNotes.size(), 1);
        assertEquals(note.getContent(), insertedNotes.get(0).getContent());
        assertEquals(note.getTimestamp(), insertedNotes.get(0).getTimestamp());
        assertEquals(note.getTitle(), insertedNotes.get(0).getTitle());

        note.setId(insertedNotes.get(0).getId());
        assertEquals(note, insertedNotes.get(0));

        // Update
        note.setTitle(TEST_TITLE);
        note.setContent(TEST_CONTENT);
        note.setTimestamp(TEST_TIMESTAMP);
        getNoteDao().updateNote(note).blockingGet();

        // Read
        insertedNotes = liveDataTestUtil.getValue(getNoteDao().getNotes());
        assertEquals(TEST_TITLE, insertedNotes.get(0).getTitle());
        assertEquals(TEST_CONTENT, insertedNotes.get(0).getContent());
        assertEquals(TEST_TIMESTAMP, insertedNotes.get(0).getTimestamp());
        note.setId(insertedNotes.get(0).getId());
        assertEquals(note, insertedNotes.get(0));

        // Delete
        getNoteDao().deleteNote(note).blockingGet();

        // Confirm the database is empty
        insertedNotes = liveDataTestUtil.getValue(getNoteDao().getNotes());
        assertEquals(insertedNotes.size(), 0);
    }

    // Insert note with no title, throw exception
    @Test(expected = SQLiteConstraintException.class)
    public void insertWithNoTitle_throwSQLiteConstraintException() throws Exception {
        final Note note = new Note(TestUtil.TEST_NOTE_1);
        note.setTitle(null);

        // Insert
        getNoteDao().insertNote(note).blockingGet();
    }

    // Insert, Update with no title, throw exception
    @Test(expected = SQLiteConstraintException.class)
    public void updateWithNoTitle_throwSQLiteConstraintException() throws Exception {
        Note note = new Note(TestUtil.TEST_NOTE_1);

        // Insert
        getNoteDao().insertNote(note).blockingGet();

        // Read
        LiveDataTestUtil<List<Note>> liveDataTestUtil = new LiveDataTestUtil<>();
        List<Note> insertedNotes = liveDataTestUtil.getValue(getNoteDao().getNotes());
        assertNotNull(insertedNotes);
        assertEquals(insertedNotes.size(), 1);

        // Update
        note = new Note(insertedNotes.get(0));
        note.setTitle(null);
        getNoteDao().updateNote(note).blockingGet();
    }
}
