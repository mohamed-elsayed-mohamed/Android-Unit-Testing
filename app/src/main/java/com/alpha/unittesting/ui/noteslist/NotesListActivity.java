package com.alpha.unittesting.ui.noteslist;

import android.os.Bundle;

import com.alpha.unittesting.R;
import com.alpha.unittesting.repository.NoteRepository;
import javax.inject.Inject;
import dagger.android.support.DaggerAppCompatActivity;

public class NotesListActivity extends DaggerAppCompatActivity {

    @Inject
    NoteRepository noteRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
    }
}