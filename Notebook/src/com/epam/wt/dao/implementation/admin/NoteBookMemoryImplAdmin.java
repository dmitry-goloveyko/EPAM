package com.epam.wt.dao.implementation.admin;

import com.epam.wt.dao.INoteBookDAOAdmin;
import com.epam.wt.entity.Note;
import com.epam.wt.entity.NoteBook;
import com.epam.wt.entity.NoteBookAdapter;

public class NoteBookMemoryImplAdmin implements INoteBookDAOAdmin {
	private static final NoteBookMemoryImplAdmin instance = new NoteBookMemoryImplAdmin();
	
	public static NoteBookMemoryImplAdmin getInstance() {
		return instance;
	}
	
	private NoteBookAdapter adapter = NoteBookAdapter.getInstance();
	private NoteBook noteBook = adapter.getNoteBook();
	
	public void addNote(String text) {
		noteBook.getNoteList().add(new Note(text));
	}
	
	public Note getNoteAtIndex(int index) {
		return noteBook.getNoteList().get(index);
	}
	
	public void removeNoteAtIndex(int index) throws IndexOutOfBoundsException {
		noteBook.getNoteList().remove(index);
	}
	
	public void clearNotebook() {
		noteBook.getNoteList().clear();
	}
}
