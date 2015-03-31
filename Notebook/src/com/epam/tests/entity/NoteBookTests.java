package com.epam.tests.entity;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.epam.wt.entity.NoteBook;

public class NoteBookTests {
	NoteBook noteBook;

	@Before
	public void setUp() throws Exception {
		noteBook = new NoteBook();
	}

	@After
	public void tearDown() throws Exception {
		noteBook = null;
	}

	@Test
	public void testAddNote() {
		noteBook.addNote("Hello!");
		assertEquals(noteBook.getNoteAtIndex(0).getText().toString(), "Hello!");
	}
	
	@Test
	public void testRemoveNote() {
		noteBook.addNote("Hello!");
		noteBook.addNote("Hello!!!");
		noteBook.addNote("Hello!!!!!");
		
		assertEquals(noteBook.getNoteAtIndex(0).getText().toString(), "Hello!");
		assertEquals(noteBook.getNoteAtIndex(1).getText().toString(), "Hello!!!");
		assertEquals(noteBook.getNoteAtIndex(2).getText().toString(), "Hello!!!!!");
		
		noteBook.removeNoteAtIndex(1);
		
		assertEquals(noteBook.getNoteAtIndex(0).getText().toString(), "Hello!");
		assertEquals(noteBook.getNoteAtIndex(1).getText().toString(), "Hello!!!!!");
	}
}
