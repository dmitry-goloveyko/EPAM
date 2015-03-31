package com.epam.tests.command;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.epam.wt.command.CommandName;
import com.epam.wt.entity.Note;
import com.epam.wt.entity.NoteBook;
import com.epam.wt.entity.NoteBookAdapter;
import com.epam.wt.logic.Manager;

public class CommandTests {
	Controller manager = new Controller();

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
		NoteBookAdapter.getInstance().setNoteBook(new NoteBook());
	}

	

	@Test
	public void testAdd() {
		manager.doRequest(CommandName.ADD, "Hello!");
		
		assertEquals("Hello!", NoteBookAdapter.getInstance().getNoteBook().getNoteList().get(0).getText().toString());
	}
	
	@Test
	public void testFind() {
		manager.doRequest(CommandName.ADD, "Hello!");
		manager.doRequest(CommandName.ADD, "Hello!!");
		manager.doRequest(CommandName.ADD, "Hello!!!");
		
		Note n1 = (Note) manager.doRequest(CommandName.FIND, 0).getResponse();
		Note n2 = (Note) manager.doRequest(CommandName.FIND, 1).getResponse();
		Note n3 = (Note) manager.doRequest(CommandName.FIND, 2).getResponse();
		assertEquals("Hello!", n1.getText().toString());
		assertEquals("Hello!!", n2.getText().toString());
		assertEquals("Hello!!!", n3.getText().toString());
	}
	
	@Test
	public void testDelete() {
		manager.doRequest(CommandName.ADD, "Hello!");
		manager.doRequest(CommandName.ADD, "Hello!!");
		manager.doRequest(CommandName.ADD, "Hello!!!");
		manager.doRequest(CommandName.DELETE, 1);
		
		Note n1 = (Note) manager.doRequest(CommandName.FIND, 0).getResponse();
		Note n2 = (Note) manager.doRequest(CommandName.FIND, 1).getResponse();
		
		assertEquals("Hello!", n1.getText().toString());
		assertEquals("Hello!!!", n2.getText().toString());
	}
	
	@Test
	public void testClear() {
		manager.doRequest(CommandName.ADD, "Hello!");
		manager.doRequest(CommandName.ADD, "Hello!!");
		manager.doRequest(CommandName.ADD, "Hello!!!");
		manager.doRequest(CommandName.CLEAR);

		assertTrue(NoteBookAdapter.getInstance().getNoteBook().getNoteList().isEmpty());
	}
}
