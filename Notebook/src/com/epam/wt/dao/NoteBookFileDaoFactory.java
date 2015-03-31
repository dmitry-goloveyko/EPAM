package com.epam.wt.dao;

import com.epam.wt.abstractdao.AbstractDAOFactory;
import com.epam.wt.dao.implementation.admin.NoteBookFileImplAdmin;
import com.epam.wt.dao.implementation.user.NoteBookFileImpl;
import com.epam.wt.dao.INoteBookDAO;


public class NoteBookFileDaoFactory extends AbstractDAOFactory {
	private static final NoteBookFileDaoFactory instance = new NoteBookFileDaoFactory();
	
	public static NoteBookFileDaoFactory getInstance() {
		return instance;
	}

	@Override
	public INoteBookDAO getNoteBookDao() {
		return NoteBookFileImpl.getInstance(); 
	}

	@Override
	public INoteBookDAOAdmin getNoteBookDaoAdmin() {
		return NoteBookFileImplAdmin.getInstance();
	}

}
