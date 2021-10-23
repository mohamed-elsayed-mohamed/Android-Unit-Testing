package com.alpha.unittesting.ui.note;

import android.arch.lifecycle.LiveDataReactiveStreams;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.alpha.unittesting.models.Note;
import com.alpha.unittesting.repository.NoteRepository;
import com.alpha.unittesting.ui.Resource;

import javax.inject.Inject;

public class NoteViewModel extends ViewModel {

    private static final String TAG = "NoteViewModel";

    // inject
    private final NoteRepository noteRepository;

    // vars
    private MutableLiveData<Note> note = new MutableLiveData<>();

    @Inject
    public NoteViewModel(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public android.arch.lifecycle.LiveData<Resource<Integer>> insertNote() throws Exception {
        return LiveDataReactiveStreams.fromPublisher(
          noteRepository.insertNote(note.getValue())
        );
    }

    public LiveData<Note> observeNote(){
        return note;
    }

    void setNote(Note note) throws Exception{
        if(note.getTitle() == null || note.getTitle().equals(""))
            throw  new Exception(NoteRepository.NOTE_TITLE_NULL);

        this.note.setValue(note);
    }
}
