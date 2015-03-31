package com.epam.tests.entity;

import static org.junit.Assert.*;

import org.junit.Test;

import com.epam.wt.entity.Note;

public class NoteTests {

	@Test
	public void testNoteConstructor() {
		Note note = new Note("Hello!");
		assertEquals(note.getText().toString(), "Hello!");
		System.out.println(note.getDate());
		
		Note note1 = new Note("");
		assertEquals(note1.getText().toString(), "");
		
		Note note2 = new Note("1234@#");
		assertEquals(note2.getText().toString(), "1234@#");
	}
}
