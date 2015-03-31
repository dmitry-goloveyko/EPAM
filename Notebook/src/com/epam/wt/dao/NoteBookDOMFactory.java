package com.epam.wt.dao;

import com.epam.wt.abstractdao.AbstractDAOFactory;
import com.epam.wt.dao.implementation.user.NoteBookDOMImpl;

public class NoteBookDOMFactory extends AbstractDAOFactory {
	private static final NoteBookDOMFactory instance = new NoteBookDOMFactory();
	
	public static NoteBookDOMFactory getInstance() {
		return instance;
	}
	
	@Override
	public INoteBookDAO getNoteBookDao() {
		return NoteBookDOMImpl.getInstance();
	}

	@Override
	public INoteBookDAOAdmin getNoteBookDaoAdmin() {
		return null;
	}

}
