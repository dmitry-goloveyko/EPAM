package com.epam.wt.xml.sax;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.wt.entity.AlertNote;
import com.epam.wt.entity.ColorNote;
import com.epam.wt.entity.Note;

public class SAXUtilsWithError {
	private static SAXUtilsWithError instance = new SAXUtilsWithError();
	
	public static SAXUtilsWithError getInstance() {
		return instance;
	}
	
	private final ResourceBundle resource = ResourceBundle.getBundle("resources.Notebook");
	private final String filePath = System.getProperty("user.dir") + "\\src\\resources\\";
	private final String fileName = resource.getString("NoteBookFileSAX");
	
	private File tmp;
	private OutputStream outputStream;
	private OutputStreamWriter outputWriter;
	private XMLStreamWriter out;
	
	private SAXUtilsWithError() {
		tmp = new File(filePath + "tmp.xml");
		try {
			outputStream = new FileOutputStream(tmp);
			outputWriter = new OutputStreamWriter(outputStream, "utf-8");
			out = XMLOutputFactory.newInstance().createXMLStreamWriter(outputWriter);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			e.printStackTrace();
		}
		
	}
	
	public void writeNotebookStartTag() {
		try {
			out.writeStartDocument();
			out.writeStartElement("notebook");
		} catch (FactoryConfigurationError e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		
	}
	
	public void writeNotebookEndTag() {
		try {
			out.writeEndElement();
			out.writeEndDocument();
			out.flush();
			
			out.close();
			outputWriter.close();
			outputStream.close();
			substituteFile();
			
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void substituteFile() {
		File oldFile = new File(filePath + fileName);
		if (oldFile.exists()) {
			boolean delete = oldFile.delete();
			if(delete) {
				if (tmp.renameTo(oldFile)) {
					System.out.println("OK!!!");
				}
			}
		}
	}
	
	public void writeNoteTag(Note note) {
		String className = note.getClass().getSimpleName();
		switch (className) {
		case "Note":
			writeNote(note);
			break;
		case "AlertNote":
			writeAlertNote((AlertNote)note);
			break;
		case "ColorNote":
			writeColorNote((ColorNote)note);
			break;
		default:
			break;
		}
	}
	
	private void writeNote(Note note) {
		try {
			out.writeStartElement("note");
			out.writeAttribute("type", "note");
			
			out.writeStartElement("text");
			out.writeCharacters(note.getText().toString());
			out.writeEndElement();
			
			out.writeStartElement("date");
			out.writeCharacters(note.getDate().toString());
			out.writeEndElement();
			
			out.writeEndElement();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
	}

	private void writeAlertNote(AlertNote note) {
		try {
			out.writeStartElement("note");
			out.writeAttribute("type", "note");
			
			out.writeStartElement("text");
			out.writeCharacters(note.getText().toString());
			out.writeEndElement();
			
			out.writeStartElement("date");
			out.writeCharacters(note.getDate().toString());
			out.writeEndElement();
			
			out.writeStartElement("alertDate");
			out.writeCharacters(note.getAlertDate().toString());
			out.writeEndElement();
			
			out.writeEndElement();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
	}

	private void writeColorNote(ColorNote note) {
		try {
			out.writeStartElement("note");
			out.writeAttribute("type", "note");
			
			out.writeStartElement("text");
			out.writeCharacters(note.getText().toString());
			out.writeEndElement();
			
			out.writeStartElement("date");
			out.writeCharacters(note.getDate().toString());
			out.writeEndElement();
			
			out.writeStartElement("alertDate");
			out.writeCharacters(note.getColor().toString());
			out.writeEndElement();
			
			out.writeEndElement();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
	}
}
