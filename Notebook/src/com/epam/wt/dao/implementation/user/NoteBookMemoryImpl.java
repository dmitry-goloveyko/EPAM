package com.epam.wt.dao.implementation.user;

import com.epam.wt.dao.INoteBookDAO;
import com.epam.wt.dao.implementation.ImplIOException;
import com.epam.wt.entity.Note;
import com.epam.wt.entity.NoteBook;
import com.epam.wt.entity.NoteBookAdapter;

public class NoteBookMemoryImpl implements INoteBookDAO {
	private static NoteBookMemoryImpl instance = new NoteBookMemoryImpl();
	
	public static INoteBookDAO getInstance() {
		return instance;
	}
	
	private NoteBookAdapter adapter = NoteBookAdapter.getInstance();
	private NoteBook noteBook = adapter.getNoteBook();
	
	@Override
	public void addNote(Note note) throws ImplIOException {
		noteBook.getNoteList().add(note);
	}
	
	public Note getNoteAtIndex(int index) {
		return noteBook.getNoteList().get(index);
	}
	
	public void removeNoteAtIndex(int index) {
		noteBook.getNoteList().remove(index);
	}
	
	public void clearNotebook() {
		noteBook.getNoteList().clear();
	}

}
