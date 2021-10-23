package com.alpha.unittesting.repository;

import com.alpha.unittesting.models.Note;
import com.alpha.unittesting.persistence.NoteDao;
import com.alpha.unittesting.ui.Resource;
import com.alpha.unittesting.utils.TestUtil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Not;

import io.reactivex.Single;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NoteRepositoryTest {
    private NoteRepository SUT;

//    @Mock
    private NoteDao noteDao;

    @BeforeEach
    public void initEach(){
//        MockitoAnnotations.initMocks(this);
        noteDao = mock(NoteDao.class);
        SUT = new NoteRepository(noteDao);
    }

    /*
    Insert note
    verify the correct method is called
    confirm observer is triggered
    confirm new rows inserted
     */

    @Test
    void insertNote_returnRow() throws Exception {
        // Arrange
        final Long insertRow = 1L;
        final Single<Long> returnedData = Single.just(insertRow);

        when(noteDao.insertNote(any(Note.class))).thenReturn(returnedData);

        // Act
        final Resource<Integer> returnedValue = SUT.insertNote(TestUtil.TEST_NOTES_LIST.get(0)).blockingFirst();

        // Assert
        verify(noteDao).insertNote(any(Note.class));
        verifyNoMoreInteractions(noteDao);

        assertEquals(Resource.success(1, NoteRepository.INSERT_SUCCESS), returnedValue);

        // Or test using RxJava
        SUT.insertNote(TestUtil.TEST_NOTES_LIST.get(0))
                .test()
                .await()
                .assertValue(Resource.success(1, NoteRepository.INSERT_SUCCESS));
    }

    /*
    Insert note
    Failure (return -1)
     */

    @Test
    void insertNote_returnFailure() throws Exception {
        // Arrange
        final Long failedInsert = -1L;
        final Single<Long> returnedData = Single.just(failedInsert);

        when(noteDao.insertNote(any(Note.class))).thenReturn(returnedData);

        // Act
        final Resource<Integer> returnedValue = SUT.insertNote(TestUtil.TEST_NOTES_LIST.get(0)).blockingFirst();

        // Assert
        verify(noteDao).insertNote(any(Note.class));
        verifyNoMoreInteractions(noteDao);

        assertEquals(Resource.error(null, NoteRepository.INSERT_FAILURE), returnedValue);
    }

    /*
    Insert note
    null title
    confirm throw exception
     */

    @Test
    void insertNote_nullTitle_throwException() throws Exception {
        assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                final Note note = new Note(TestUtil.TEST_NOTE_1);
                note.setTitle(null);
                SUT.insertNote(note);
            }
        });
    }
}