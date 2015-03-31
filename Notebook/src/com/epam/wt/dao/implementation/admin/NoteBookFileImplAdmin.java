package com.epam.wt.dao.implementation.admin;

import com.epam.wt.dao.INoteBookDAOAdmin;

public class NoteBookFileImplAdmin implements INoteBookDAOAdmin {
	private static final NoteBookFileImplAdmin instance = new NoteBookFileImplAdmin();
	
	public static NoteBookFileImplAdmin getInstance() {
		return instance;
	}

}
