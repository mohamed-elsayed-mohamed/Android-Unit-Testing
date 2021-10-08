package com.alpha.unittesting.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NoteTest {

    public static final String TIMESTAMP1 = "8/10/2021";
    public static final String TIMESTAMP2 = "9/10/2021";

    // Compare two equal Notes
    @Test
    void isNotesEquals_identicalProperties_true() {
        // Arrange
        Note note1 = new Note("Go to Alex", "Go to Alex and have fun", TIMESTAMP1);
        note1.setId(1);
        Note note2 = new Note("Go to Alex", "Go to Alex and have fun", TIMESTAMP1);
        note2.setId(1);

        // Act

        // Assert
        assertEquals(note1, note2);
        print("The notes are equals");
    }

    // Compare notes with different ids
    @Test
    void isNotesEquals_differentIds_false() {
        // Arrange
        Note note1 = new Note("Go to Alex", "Go to Alex and have fun", TIMESTAMP1);
        note1.setId(1);
        Note note2 = new Note("Go to Alex", "Go to Alex and have fun", TIMESTAMP1);
        note2.setId(2);

        // Act

        // Assert
        assertNotEquals(note1, note2);
        print("The notes are not equals they have different Ids");
    }


    // Compare two notes with different titles
    @Test
    void isNotesEquals_differentTitles_false() {
        // Arrange
        Note note1 = new Note("Go to Alex", "Go to Alex and have fun", TIMESTAMP1);
        note1.setId(1);
        Note note2 = new Note("Go to Cairo", "Go to Alex and have fun", TIMESTAMP1);
        note2.setId(1);

        // Act

        // Assert
        assertNotEquals(note1, note2);
        print("The notes are not equals they have different titles");
    }

    // Compare two notes with different content
    @Test
    void isNotesEquals_differentContent_true() {
        // Arrange
        Note note1 = new Note("Go to Alex", "Go to Alex and have fun", TIMESTAMP1);
        note1.setId(1);
        Note note2 = new Note("Go to Alex", "Go to the ITI", TIMESTAMP1);
        note2.setId(1);

        // Act

        // Assert
        assertEquals(note1, note2);
        print("The notes are equals");
    }

    // Compare two notes with different timestamps
    @Test
    void isNotesEquals_differentTimestamps_true() {
        // Arrange
        Note note1 = new Note("Go to Alex", "Go to Alex and have fun", TIMESTAMP1);
        note1.setId(1);
        Note note2 = new Note("Go to Alex", "Go to Alex and have fun", TIMESTAMP2);
        note2.setId(1);

        // Act

        // Assert
        assertEquals(note1, note2);
        print("The notes are equals");
    }

    private void print(String message){
        System.out.println(message);
    }
}
