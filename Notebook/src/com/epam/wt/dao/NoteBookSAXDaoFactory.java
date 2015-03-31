package com.epam.wt.dao;

import com.epam.wt.abstractdao.AbstractDAOFactory;
import com.epam.wt.dao.implementation.user.NoteBookSAXImpl;

public class NoteBookSAXDaoFactory extends AbstractDAOFactory {
	private static final NoteBookSAXDaoFactory instance = new NoteBookSAXDaoFactory();
	
	public static NoteBookSAXDaoFactory getInstance() {
		return instance;
	}

	@Override
	public INoteBookDAO getNoteBookDao() {
		return NoteBookSAXImpl.getInstance();
	}

	@Override
	public INoteBookDAOAdmin getNoteBookDaoAdmin() {
		// TODO Auto-generated method stub
		return null;
	}
}
