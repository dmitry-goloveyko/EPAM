package com.epam.wt.dao;

import com.epam.wt.abstractdao.AbstractDAOFactory;
import com.epam.wt.dao.INoteBookDAO;
import com.epam.wt.dao.INoteBookDAOAdmin;
import com.epam.wt.dao.implementation.user.NoteBookJDBCImpl;

public class NoteBookJDBCFactory extends AbstractDAOFactory {
	private static final NoteBookJDBCFactory instance = new NoteBookJDBCFactory();
	
	public static NoteBookJDBCFactory getInstance() {
		return instance;
	}
	
	@Override
	public INoteBookDAO getNoteBookDao() {
		return NoteBookJDBCImpl.getInstance();
	}

	@Override
	public INoteBookDAOAdmin getNoteBookDaoAdmin() {
		// TODO Auto-generated method stub
		return null;
	}

}
