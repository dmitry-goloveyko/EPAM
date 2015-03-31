package com.epam.wt.dao.implementation.user;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.joda.time.DateTime;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.epam.wt.dao.INoteBookDAO;
import com.epam.wt.dao.implementation.ImplDOMParserConfigurationException;
import com.epam.wt.dao.implementation.ImplSourceNotFoundException;
import com.epam.wt.dao.implementation.ImplSourceRecordNotFoundException;
import com.epam.wt.dao.implementation.ImplIOException;
import com.epam.wt.entity.AlertNote;
import com.epam.wt.entity.ColorNote;
import com.epam.wt.entity.Note;
import com.epam.wt.xml.dom.DOMUtils;

public class NoteBookDOMImpl implements INoteBookDAO {
	private static NoteBookDOMImpl instance = new NoteBookDOMImpl();
	
	public static INoteBookDAO getInstance() {
		return instance;
	}
	
	private final ResourceBundle resource = ResourceBundle.getBundle("resources.Notebook");
	private final String filePath = System.getProperty("user.dir") + "\\src\\resources\\";
	private final String fileName = resource.getString("NoteBookFileDOM");

	@Override
	public void addNote(Note note) throws ImplIOException, ImplDOMParserConfigurationException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new ImplDOMParserConfigurationException(e.getMessage());
		}
		
		Document doc = null;
		
		//if file doesn't exist -> making new file + appending root element notebook
		File file = new File(filePath + fileName);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			doc = dBuilder.newDocument();
			
			DOMUtils.appendNotebookRootElement(doc);
			
			DOMUtils.transformFile(doc, file);
		}
		
		try {
			doc = dBuilder.parse(file);
		} catch (SAXException e) {
			throw new ImplDOMParserConfigurationException(e.getMessage());
		} catch (IOException e) {
			throw new ImplIOException(e.getMessage());
		}
		
		
		Element rootElement = doc.getDocumentElement();
		rootElement.normalize();
		
		Element newNote = DOMUtils.makeNewNoteElement(doc, note);
		
		rootElement.appendChild(newNote);
		
		DOMUtils.transformFile(doc, file);
	}

	@Override
	public Note getNoteAtIndex(int index) throws ImplSourceNotFoundException,
			ImplIOException, ImplSourceRecordNotFoundException, ImplDOMParserConfigurationException {
		
		File file = new File(filePath + fileName);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Document doc = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(file);
		} catch (ParserConfigurationException e) {
			throw new ImplDOMParserConfigurationException(e.getMessage());
		} catch (SAXException e) {
			throw new ImplDOMParserConfigurationException(e.getMessage());
		} catch (IOException e) {
			throw new ImplIOException(e.getMessage());
		}
		
		Element rootElement = doc.getDocumentElement();
		rootElement.normalize();
		
		NodeList list = doc.getElementsByTagName("note");
		
		Element note = (Element) list.item(index);
		String text = note.getElementsByTagName("text").item(0).getTextContent();
		DateTime date = new DateTime(note.getElementsByTagName("date").item(0).getTextContent());
		String noteType = note.getAttribute("type");
		
		switch (noteType) {
		case "AlertNote":
			DateTime alertDate = new DateTime(note.getElementsByTagName("alertDate").item(0).getTextContent());
			return new AlertNote(text,  date, alertDate);
		case "ColorNote":
			String color = note.getElementsByTagName("color").item(0).getTextContent();
			return new ColorNote(text, color, date);
		default:
			break;
		}
		
		return new Note(text, date);
	}

	@Override
	public void removeNoteAtIndex(int index) throws ImplSourceNotFoundException,
			ImplIOException, ImplSourceRecordNotFoundException, ImplDOMParserConfigurationException {
		
		File file = new File(filePath + fileName);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Document doc = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(file);
		} catch (ParserConfigurationException e) {
			throw new ImplDOMParserConfigurationException(e.getMessage());
		} catch (SAXException e) {
			throw new ImplDOMParserConfigurationException(e.getMessage());
		} catch (IOException e) {
			throw new ImplIOException(e.getMessage());
		}
		
		Element rootElement = doc.getDocumentElement();
		rootElement.normalize();
		
		NodeList list = doc.getElementsByTagName("note");
		
		Element noteNode = (Element) list.item(index);
		
		noteNode.getParentNode().removeChild(noteNode);
		
		DOMUtils.transformFile(doc, file);
	}

	@Override
	public void clearNotebook() throws ImplIOException, ImplDOMParserConfigurationException {
		File file = new File(filePath + fileName);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Document doc = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(file);
		} catch (ParserConfigurationException e) {
			throw new ImplDOMParserConfigurationException(e.getMessage());
		} catch (SAXException e) {
			throw new ImplDOMParserConfigurationException(e.getMessage());
		} catch (IOException e) {
			throw new ImplIOException(e.getMessage());
		}
		
		Element rootElement = doc.getDocumentElement();
		rootElement.normalize();
		
		NodeList list = doc.getElementsByTagName("note");
		List<Element> notesToRemove = new ArrayList<Element>();
		for (int i = 0; i < list.getLength(); i++) {
			notesToRemove.add((Element) list.item(i));
		}
		for (Element element : notesToRemove) {
			element.getParentNode().removeChild(element);
		}
		
		DOMUtils.transformFile(doc, file);
	}

}
