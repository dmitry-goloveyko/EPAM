package com.epam.wt.xml.dom;

import java.io.File;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.epam.wt.entity.AlertNote;
import com.epam.wt.entity.ColorNote;
import com.epam.wt.entity.Note;

public class DOMUtils {
	public static void transformFile(Document doc, File file) {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = transformerFactory.newTransformer();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}
		
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(file);
		
		try {
			transformer.transform(source, result);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
	public static void appendNotebookRootElement(Document doc) {
		Element rootElement = doc.createElement("notebook");
		doc.appendChild(rootElement);
	}
	
	public static Element makeNewNoteElement(Document doc, Note note) {
		Element newNote = doc.createElement("note");
		String noteType = note.getClass().getSimpleName();
		newNote.setAttribute("type", noteType);
		
		Element text = doc.createElement("text");
		text.appendChild(doc.createTextNode(note.getText().toString()));
		newNote.appendChild(text);
		
		Element date = doc.createElement("date");
		date.appendChild(doc.createTextNode(note.getDate().toString()));
		newNote.appendChild(date); 
		
		switch (noteType) {
		case "AlertNote":
			AlertNote alertNote = (AlertNote) note;
			Element alertDate = doc.createElement("alertDate");
			alertDate.appendChild(doc.createTextNode(alertNote.getAlertDate().toString()));
			newNote.appendChild(alertDate);
			break;
		case "ColorNote":
			ColorNote colorNote = (ColorNote) note;
			Element color = doc.createElement("color");
			color.appendChild(doc.createTextNode(colorNote.getColor().toString()));
			newNote.appendChild(color);
			break;
		}
		
		return newNote;
	}
	
}
