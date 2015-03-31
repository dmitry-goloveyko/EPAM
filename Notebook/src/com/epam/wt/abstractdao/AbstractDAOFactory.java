package com.epam.wt.abstractdao;

import java.util.ResourceBundle;

import com.epam.wt.dao.NoteBookDOMFactory;
import com.epam.wt.dao.NoteBookFileDaoFactory;
import com.epam.wt.dao.NoteBookMemoryDaoFactory;
import com.epam.wt.dao.INoteBookDAO;
import com.epam.wt.dao.INoteBookDAOAdmin;
import com.epam.wt.dao.NoteBookSAXDaoFactory;
import com.epam.wt.dao.NoteBookJDBCFactory;



public abstract class AbstractDAOFactory {
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("resources.Notebook");
	
    private static final String typeDao = RESOURCE_BUNDLE.getString("DataSource");
    
    public abstract INoteBookDAO getNoteBookDao();
    public abstract INoteBookDAOAdmin getNoteBookDaoAdmin();
    
    public static AbstractDAOFactory getDAOFactory(){
        switch(typeDao){
        case "memory":
        	return NoteBookMemoryDaoFactory.getInstance();
        	
        case "file":
        	return NoteBookFileDaoFactory.getInstance();
        	
        case "SAX XML":
        	return NoteBookSAXDaoFactory.getInstance();
        	
        case "DOM XML":
        	return NoteBookDOMFactory.getInstance();
        	
        case "JDBC":
        	return NoteBookJDBCFactory.getInstance();
        	
        default:
        	return null;
        }
    }
    

}
