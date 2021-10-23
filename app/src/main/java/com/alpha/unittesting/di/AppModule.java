package com.alpha.unittesting.di;

import android.app.Application;

import androidx.room.Room;

import com.alpha.unittesting.persistence.NoteDao;
import com.alpha.unittesting.persistence.NoteDatabase;
import com.alpha.unittesting.repository.NoteRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    @Singleton
    @Provides
    static NoteDatabase provideNoteDatabase(Application application){
        return Room.databaseBuilder(application, NoteDatabase.class, NoteDatabase.DATABASE_NAME).build();
    }

    @Singleton
    @Provides
    static NoteDao provideNoteDao(NoteDatabase noteDatabase){
        return noteDatabase.getNoteDao();
    }

    @Singleton
    @Provides
    static NoteRepository provideNoteRepository(NoteDao noteDao){
        return new NoteRepository(noteDao);
    }
}
