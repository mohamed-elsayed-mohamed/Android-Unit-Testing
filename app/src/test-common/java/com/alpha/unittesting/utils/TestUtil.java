package com.alpha.unittesting.utils;

import com.alpha.unittesting.models.Note;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestUtil {
    public static final String TIMESTAMP_1 = "10/2021";
    public static final Note TEST_NOTE_1 = new Note("Take out the trash", "It's garbage day tomorrow.", TIMESTAMP_1);

    public static final String TIMESTAMP_2 = "11/2021";
    public static final Note TEST_NOTE_2 = new Note("Anniversary gift", "Buy an anniversary gift.", TIMESTAMP_2);

    public static final List<Note> TEST_NOTES_LIST = Collections.unmodifiableList(
            new ArrayList<Note>(){{
                add(new Note(1, "Take out the trash", "It's garbage day tomorrow.", TIMESTAMP_1));
                add(new Note(2, "Anniversary gift", "Buy an anniversary gift.", TIMESTAMP_2));
            }}
    );
}
