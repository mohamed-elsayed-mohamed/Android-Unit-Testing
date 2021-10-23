package com.alpha.unittesting.di;

import com.alpha.unittesting.ui.noteslist.NotesListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract  class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract NotesListActivity contributesListActivity();
}
