package com.epam.wt.xml.sax;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.wt.entity.AlertNote;
import com.epam.wt.entity.ColorNote;
import com.epam.wt.entity.Note;

public class SAXUtils {
	private static SAXUtils instance = new SAXUtils();
	
	public static SAXUtils getInstance() {
		return instance;
	}
	
	private static final ResourceBundle resource = ResourceBundle.getBundle("resources.Notebook");
	private static final String filePath = System.getProperty("user.dir") + "\\src\\resources\\";
	private static final String fileName = resource.getString("NoteBookFileSAX");
	
	private static OutputStream outputStream;
	private static OutputStreamWriter outputWriter;
	private static XMLStreamWriter out;
	
	private static Note noteToShow = null;
	private static boolean noteDeleted = false;
	
	public boolean isNoteDeleted() {
		boolean result = noteDeleted;
		noteDeleted = false;
		return result;
	}

	public void setNoteDeleted(boolean noteDeleted) {
		SAXUtils.noteDeleted = noteDeleted;
	}

	public void substituteFile(String xml) {
		File oldFile = new File(filePath + fileName);
		if (oldFile.exists()) {
			PrintWriter writer = null;
			try {
				writer = new PrintWriter(oldFile, "UTF-8");
				writer.write(xml);
				writer.flush();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} finally {
				writer.close();
			}
			writer.flush();
		} 
	}
	
	public void writeNotebookStartTag() {
		try {
			outputStream = new ByteArrayOutputStream();
			outputWriter = new OutputStreamWriter(outputStream, "utf-8");
			out = XMLOutputFactory.newInstance().createXMLStreamWriter(outputWriter);
			out.writeStartDocument();
			out.writeStartElement("notebook");
		} catch (FactoryConfigurationError e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		
	}
	
	public void writeNotebookEndTag() {
		String xml = null;
		try {
			out.writeEndElement();
			out.writeEndDocument();
			out.flush();
			
			xml = outputStream.toString();
			
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
				outputWriter.close();
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (XMLStreamException e) {
				e.printStackTrace();
			}
		}
		
		substituteFile(xml);
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
			out.writeAttribute("type", "Note");
			
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
			out.writeAttribute("type", "AlertNote");
			
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
			out.writeAttribute("type", "ColorNote");
			
			out.writeStartElement("text");
			out.writeCharacters(note.getText().toString());
			out.writeEndElement();
			
			out.writeStartElement("date");
			out.writeCharacters(note.getDate().toString());
			out.writeEndElement();
			
			out.writeStartElement("color");
			out.writeCharacters(note.getColor().toString());
			out.writeEndElement();
			
			out.writeEndElement();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
	}
	
	public void setNoteToShow(Note note) {
		noteToShow = note;
	}
	
	public Note getNoteToShow() throws SAXSourceRecordNotFound {
		if(noteToShow == null) {
			throw new SAXSourceRecordNotFound("Note not found");
		}
		Note note = noteToShow;
		noteToShow = null;
		return note;
	}
}
