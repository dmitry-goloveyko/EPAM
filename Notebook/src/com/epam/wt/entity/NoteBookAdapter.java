package com.epam.wt.entity;

public class NoteBookAdapter {
	private static final NoteBookAdapter instance = new NoteBookAdapter();
    private NoteBook noteBook = new NoteBook();
    
    

	public static NoteBookAdapter getInstance(){
    	return instance;
    }
    
    public NoteBook getNoteBook(){
    	return noteBook;
    }

    public void setNoteBook(NoteBook noteBook) {
		this.noteBook = noteBook;
	}
}
