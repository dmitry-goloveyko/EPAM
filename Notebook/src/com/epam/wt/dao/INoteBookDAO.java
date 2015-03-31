package com.epam.wt.dao;

import com.epam.wt.dao.implementation.ImplJDBCClassNotFoundException;
import com.epam.wt.dao.implementation.ImplSQLException;
import com.epam.wt.dao.implementation.ImplSourceRecordNotFoundException;
import com.epam.wt.dao.implementation.ImplSourceNotFoundException;
import com.epam.wt.dao.implementation.ImplIOException;
import com.epam.wt.dao.implementation.ImplDOMParserConfigurationException;
import com.epam.wt.entity.Note;

public interface INoteBookDAO {
	void addNote(Note note) throws ImplIOException,
			ImplSourceNotFoundException, ImplDOMParserConfigurationException,
			ImplSQLException, ImplJDBCClassNotFoundException;

	Note getNoteAtIndex(int index) throws ImplSourceNotFoundException,
			ImplIOException, ImplSourceRecordNotFoundException,
			ImplDOMParserConfigurationException, ImplSQLException,
			ImplJDBCClassNotFoundException;

	void removeNoteAtIndex(int index) throws ImplSourceNotFoundException,
			ImplIOException, ImplSourceRecordNotFoundException,
			ImplDOMParserConfigurationException, ImplSQLException,
			ImplJDBCClassNotFoundException;

	void clearNotebook() throws ImplIOException, ImplSourceNotFoundException,
			ImplDOMParserConfigurationException, ImplSQLException,
			ImplJDBCClassNotFoundException;
}
