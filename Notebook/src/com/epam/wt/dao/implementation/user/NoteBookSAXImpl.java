package com.epam.wt.dao.implementation.user;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.epam.wt.dao.INoteBookDAO;
import com.epam.wt.dao.implementation.ImplIOException;
import com.epam.wt.dao.implementation.ImplSourceNotFoundException;
import com.epam.wt.dao.implementation.ImplSourceRecordNotFoundException;
import com.epam.wt.entity.Note;
import com.epam.wt.xml.sax.SAXAddNoteHandler;
import com.epam.wt.xml.sax.SAXClearNotebookHandler;
import com.epam.wt.xml.sax.SAXDeleteNoteHandler;
import com.epam.wt.xml.sax.SAXGetNoteHandler;
import com.epam.wt.xml.sax.SAXSourceRecordNotFound;
import com.epam.wt.xml.sax.SAXUtils;

public class NoteBookSAXImpl implements INoteBookDAO {
	private static NoteBookSAXImpl instance = new NoteBookSAXImpl();
	
	public static INoteBookDAO getInstance() {
		return instance;
	}
	
	

	@Override
	public void addNote(Note note) throws ImplSourceNotFoundException, ImplIOException{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		ResourceBundle resource = ResourceBundle.getBundle("resources.Notebook");
		String filePath = System.getProperty("user.dir") + "\\src\\resources\\" + resource.getString("NoteBookFileSAX");
		InputStream xmlInput = null;
		
		try {
			xmlInput = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			throw new ImplSourceNotFoundException(e.getMessage());
		}
		
		SAXParser saxParser;
		try {
			saxParser = factory.newSAXParser();
			DefaultHandler handler = new SAXAddNoteHandler(note);
			saxParser.parse(xmlInput, handler);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				xmlInput.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Note getNoteAtIndex(int index) throws ImplSourceNotFoundException,
				ImplSourceRecordNotFoundException {
		
		SAXParserFactory factory = SAXParserFactory.newInstance();
		ResourceBundle resource = ResourceBundle.getBundle("resources.Notebook");
		String filePath = System.getProperty("user.dir") + "\\src\\resources\\" + resource.getString("NoteBookFileSAX");
		InputStream xmlInput = null;
		
		try {
			xmlInput = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			throw new ImplSourceNotFoundException(e.getMessage());
		}
		
		Note note = null;
		SAXParser saxParser;
		try {
			saxParser = factory.newSAXParser();
			DefaultHandler handler = new SAXGetNoteHandler(index);
			saxParser.parse(xmlInput, handler);
			try {
				note = SAXUtils.getInstance().getNoteToShow();
			} catch (SAXSourceRecordNotFound e) {
				throw new ImplSourceRecordNotFoundException(e.getMessage());
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				xmlInput.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return note;
	}

	@Override
	public void removeNoteAtIndex(int index) throws ImplSourceNotFoundException, 
					ImplSourceRecordNotFoundException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		ResourceBundle resource = ResourceBundle.getBundle("resources.Notebook");
		String filePath = System.getProperty("user.dir") + "\\src\\resources\\" + resource.getString("NoteBookFileSAX");
		InputStream xmlInput = null;
		
		try {
			xmlInput = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			throw new ImplSourceNotFoundException(e.getMessage());
		}
		
		SAXParser saxParser;
		try {
			saxParser = factory.newSAXParser();
			DefaultHandler handler = new SAXDeleteNoteHandler(index);
			saxParser.parse(xmlInput, handler);
			
			if(!(SAXUtils.getInstance().isNoteDeleted())) {
				throw new ImplSourceRecordNotFoundException("Source record not found");
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				xmlInput.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void clearNotebook() throws ImplSourceNotFoundException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		ResourceBundle resource = ResourceBundle.getBundle("resources.Notebook");
		String filePath = System.getProperty("user.dir") + "\\src\\resources\\" + resource.getString("NoteBookFileSAX");
		InputStream xmlInput = null;
		
		try {
			xmlInput = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			throw new ImplSourceNotFoundException(e.getMessage());
		}
		
		SAXParser saxParser;
		try {
			saxParser = factory.newSAXParser();
			DefaultHandler handler = new SAXClearNotebookHandler();
			saxParser.parse(xmlInput, handler);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				xmlInput.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
