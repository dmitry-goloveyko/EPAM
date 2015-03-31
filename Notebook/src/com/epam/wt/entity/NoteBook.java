package com.epam.wt.entity;

import java.util.ArrayList;
import java.util.List;

public class NoteBook {
	private List<Note> noteBook = new ArrayList<Note>();
	
	//getters-setters

	public List<Note> getNoteList() {
		return noteBook;
	}
	
	public void setNoteList(List<Note> noteBook) {
		this.noteBook = noteBook;
	}

	//hashCode(), equals(), toString()
	@Override
	public String toString() {
		return "NoteBook [noteBook=" + noteBook.toString() + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((noteBook == null) ? 0 : noteBook.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NoteBook other = (NoteBook) obj;
		if (noteBook == null) {
			if (other.noteBook != null)
				return false;
		} else if (!noteBook.equals(other.noteBook))
			return false;
		return true;
	}

}
