package com.epam.wt.dao;

import com.epam.wt.abstractdao.AbstractDAOFactory;
import com.epam.wt.dao.implementation.user.NoteBookMemoryImpl;
import com.epam.wt.dao.implementation.admin.NoteBookMemoryImplAdmin;

/**
 * @author Dzmitry_Halaveika
 *
 */
public class NoteBookMemoryDaoFactory extends AbstractDAOFactory {
	private static NoteBookMemoryDaoFactory instance = new NoteBookMemoryDaoFactory();
	
	public static NoteBookMemoryDaoFactory getInstance() {
		return instance;
	}

	public INoteBookDAO getNoteBookDao() {
        return NoteBookMemoryImpl.getInstance();
	}

	@Override
	public INoteBookDAOAdmin getNoteBookDaoAdmin() {
		return NoteBookMemoryImplAdmin.getInstance();
	}

}
