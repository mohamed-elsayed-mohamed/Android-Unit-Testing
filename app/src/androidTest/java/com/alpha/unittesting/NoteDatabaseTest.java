package com.alpha.unittesting;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.alpha.unittesting.persistence.NoteDao;
import com.alpha.unittesting.persistence.NoteDatabase;

import org.junit.After;
import org.junit.Before;

public abstract class NoteDatabaseTest {

    private NoteDatabase SUT;

    public NoteDao getNoteDao(){
        return SUT.getNoteDao();
    }

    @Before
    public void setUp() throws Exception {
        SUT = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), NoteDatabase.class).build();
    }

    @After
    public void tearDown() throws Exception {
        SUT.close();
    }
}
