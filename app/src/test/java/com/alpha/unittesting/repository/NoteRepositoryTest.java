package com.alpha.unittesting.repository;

import androidx.lifecycle.MutableLiveData;

import com.alpha.unittesting.models.Note;
import com.alpha.unittesting.persistence.NoteDao;
import com.alpha.unittesting.ui.Resource;
import com.alpha.unittesting.utils.InstantExecutorExtension;
import com.alpha.unittesting.utils.LiveDataTestUtil;
import com.alpha.unittesting.utils.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.internal.matchers.Not;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

import static com.alpha.unittesting.repository.NoteRepository.DELETE_FAILURE;
import static com.alpha.unittesting.repository.NoteRepository.DELETE_SUCCESS;
import static com.alpha.unittesting.repository.NoteRepository.INVALID_NOTE_ID;
import static com.alpha.unittesting.utils.TestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(InstantExecutorExtension.class)
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
        final Resource<Integer> returnedValue = SUT.insertNote(TEST_NOTES_LIST.get(0)).blockingFirst();

        // Assert
        verify(noteDao).insertNote(any(Note.class));
        verifyNoMoreInteractions(noteDao);

        assertEquals(Resource.success(1, NoteRepository.INSERT_SUCCESS), returnedValue);

        // Or test using RxJava
        SUT.insertNote(TEST_NOTES_LIST.get(0))
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
        final Resource<Integer> returnedValue = SUT.insertNote(TEST_NOTES_LIST.get(0)).blockingFirst();

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
                final Note note = new Note(TEST_NOTE_1);
                note.setTitle(null);
                SUT.insertNote(note);
            }
        });
    }

    /*
        Update note
        verify correct method is called
        confirm observer is trigger
        confirm number of rows updated
     */

    @Test
    void updateNote_returnNumRowsUpdated() throws Exception {
        // Arrange
        final int updateRow = 1;
        when(noteDao.updateNote(any(Note.class))).thenReturn(Single.just(updateRow));

        // Act
        final Resource<Integer> returnedValue = SUT.updateNote(TEST_NOTE_1).blockingFirst();

        // Assert
        verify(noteDao).updateNote(any(Note.class));
        verifyNoMoreInteractions(noteDao);

        assertEquals(Resource.success(updateRow, NoteRepository.UPDATE_SUCCESS), returnedValue);
    }

    /*
        Update note
        Failure (-1)
     */

    @Test
    void updateNote_returnFailure() throws Exception {
        // Arrange
        final int failedInsert = -1;
        final Single<Integer> returnedData = Single.just(failedInsert);

        when(noteDao.updateNote(any(Note.class))).thenReturn(returnedData);

        // Act
        final Resource<Integer> returnedValue = SUT.updateNote(TEST_NOTE_1).blockingFirst();

        // Assert
        verify(noteDao).updateNote(any(Note.class));
        verifyNoMoreInteractions(noteDao);

        assertEquals(Resource.error(null, NoteRepository.UPDATE_FAILURE), returnedValue);
    }

    /*
        Update note
        null title
        throw exception
     */
    @Test
    void updateNote_nullTitle_throwException() throws Exception {
        Exception exception = assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                final Note note = new Note(TEST_NOTE_1);
                note.setTitle(null);
                SUT.updateNote(note);
            }
        });

        assertEquals(NoteRepository.NOTE_TITLE_NULL, exception.getMessage());
    }


    /*
        Delete note
        null id
        throw exception
     */

    @Test
    void deleteNote_nullId_throwException() {
        Exception exception = assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                final Note note = new Note(TEST_NOTE_1);
                note.setId(-1);
                SUT.deleteNote(note);
            }
        });

        assertEquals(exception.getMessage(), INVALID_NOTE_ID);
    }

    /*
        Delete note
        delete success
        return Resource.success with deleted row
     */

    @Test
    void deleteNote_deleteSuccess_returnResourceSuccess() throws Exception {
        // Arrange
        final int deletedRow = 1;
        Resource<Integer> successResponse = Resource.success(deletedRow, DELETE_SUCCESS);
        LiveDataTestUtil<Resource<Integer>> liveDataTestUtil = new LiveDataTestUtil<>();
        when(noteDao.deleteNote(any(Note.class))).thenReturn(Single.just(deletedRow));

        // Act
        Resource<Integer> observedResource = liveDataTestUtil.getValue(SUT.deleteNote(TEST_NOTE_1));

        // Assert
        assertEquals(successResponse, observedResource);
    }

    /*
        Delete note
        delete failure
        return Resource.error
     */

    @Test
    void deleteNote_deleteFailure_returnResourceError() throws Exception {
        // Arrange
        final int deletedRow = -1;
        Resource<Integer> errorResponse = Resource.error(null, DELETE_FAILURE);
        LiveDataTestUtil<Resource<Integer>> liveDataTestUtil = new LiveDataTestUtil<>();
        when(noteDao.deleteNote(any(Note.class))).thenReturn(Single.just(deletedRow));

        // Act
        Resource<Integer> observedResource = liveDataTestUtil.getValue(SUT.deleteNote(TEST_NOTE_1));

        // Assert
        assertEquals(errorResponse, observedResource);
    }

    /*
        Retrieve notes
        return list of notes
     */

    @Test
    void getNotes_returnListWithNotes() throws Exception{
        // Arrange
        List<Note> notes = TEST_NOTES_LIST;
        LiveDataTestUtil<List<Note>> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<List<Note>> returnedData = new MutableLiveData<>();
        returnedData.setValue(notes);
        when(noteDao.getNotes()).thenReturn(returnedData);

        // Act
        List<Note> observedData = liveDataTestUtil.getValue(SUT.getNotes());

        // Assert

        assertEquals(notes, observedData);
    }

    /*
        Retrieve notes
        return empty list
     */

    @Test
    void getNotes_returnEmptyList() throws Exception{
        // Arrange
        List<Note> notes = new ArrayList<>();
        LiveDataTestUtil<List<Note>> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<List<Note>> returnedData = new MutableLiveData<>();
        returnedData.setValue(notes);
        when(noteDao.getNotes()).thenReturn(returnedData);

        // Act
        List<Note> observedData = liveDataTestUtil.getValue(SUT.getNotes());

        // Assert

        assertEquals(notes, observedData);
    }
}