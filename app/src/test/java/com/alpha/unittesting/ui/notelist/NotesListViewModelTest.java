package com.alpha.unittesting.ui.notelist;

import androidx.lifecycle.MutableLiveData;

import com.alpha.unittesting.models.Note;
import com.alpha.unittesting.repository.NoteRepository;
import com.alpha.unittesting.ui.Resource;
import com.alpha.unittesting.ui.noteslist.NotesListViewModel;
import com.alpha.unittesting.utils.InstantExecutorExtension;
import com.alpha.unittesting.utils.LiveDataTestUtil;
import com.alpha.unittesting.utils.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;

import static com.alpha.unittesting.repository.NoteRepository.DELETE_FAILURE;
import static com.alpha.unittesting.repository.NoteRepository.DELETE_SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(InstantExecutorExtension.class)
public class NotesListViewModelTest {
    // system under test
    private NotesListViewModel viewModel;

    @Mock
    private NoteRepository noteRepository;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
        viewModel = new NotesListViewModel(noteRepository);
    }

    /*
        Retrieve list of notes
        observe list
        return list
     */

    @Test
    void retrieveNotes_returnNotesList() throws Exception {
        // Arrange
        List<Note> returnedData = TestUtil.TEST_NOTES_LIST;
        LiveDataTestUtil<List<Note>> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<List<Note>> returnedValue = new MutableLiveData<>();
        returnedValue.setValue(returnedData);
        when(noteRepository.getNotes()).thenReturn(returnedValue);

        // Act
        viewModel.getNotes();
        List<Note> observedData = liveDataTestUtil.getValue(viewModel.observeNotes());

        // Assert
        assertEquals(returnedData, observedData);
    }

    /*
        Retrieve list of notes
        observe the list
        return empty list
     */

    @Test
    void retrieveNotes_returnEmptyNotes() throws Exception{
        // Arrange
        List<Note> returnedData = new ArrayList<>();
        LiveDataTestUtil<List<Note>> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<List<Note>> returnedValue = new MutableLiveData<>();
        returnedValue.setValue(returnedData);
        when(noteRepository.getNotes()).thenReturn(returnedValue);

        // Act
        viewModel.getNotes();
        List<Note> observedData = liveDataTestUtil.getValue(viewModel.observeNotes());

        // Assert
        assertEquals(returnedData, observedData);
    }

    /*
        Delete note
        observe Resource.success
        return Resource.success
     */

    @Test
    void deleteNote_observeResourceSuccess() throws Exception {
        // Arrange
        Note deletedNote = new Note(TestUtil.TEST_NOTE_1);
        Resource<Integer> returnedData = Resource.success(1, DELETE_SUCCESS);
        LiveDataTestUtil<Resource<Integer>> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<Resource<Integer>> returnedValue = new MutableLiveData<>();
        returnedValue.setValue(returnedData);

        // Act
        when(noteRepository.deleteNote(any(Note.class))).thenReturn(returnedValue);

        // Assert
        Resource<Integer> observedValue = liveDataTestUtil.getValue(viewModel.deleteNote(deletedNote));

        assertEquals(returnedData, observedValue);
    }

    /*
        Delete note
        observe Resource.error
        return Resource.error
     */
    @Test
    void deleteNote_observeResourceError() throws Exception {
        // Arrange
        Note deletedNote = new Note(TestUtil.TEST_NOTE_1);
        Resource<Integer> returnedData = Resource.error(null, DELETE_FAILURE);
        LiveDataTestUtil<Resource<Integer>> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<Resource<Integer>> returnedValue = new MutableLiveData<>();
        returnedValue.setValue(returnedData);

        // Act
        when(noteRepository.deleteNote(any(Note.class))).thenReturn(returnedValue);

        // Assert
        Resource<Integer> observedValue = liveDataTestUtil.getValue(viewModel.deleteNote(deletedNote));

        assertEquals(returnedData, observedValue);
    }
}
