package com.epam.wt.xml.sax;

import org.joda.time.DateTime;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.epam.wt.entity.AlertNote;
import com.epam.wt.entity.ColorNote;
import com.epam.wt.entity.Note;

public class SAXDeleteNoteHandler extends DefaultHandler {
	private String currentElement;
	private String currentNoteType;
	private Note note;
	private String text;
	private DateTime date;
	private DateTime alertDate;
	private String color;
	private final SAXUtils saxUtils = SAXUtils.getInstance();
	private int currentNoteIndex = 0;
	private final int noteToDeleteIndex;
	
	public SAXDeleteNoteHandler(int index) {
		noteToDeleteIndex = index;
	}
	
	public void startDocument() throws SAXException {
		saxUtils.writeNotebookStartTag();
    }

    public void endDocument() throws SAXException {
    	saxUtils.writeNotebookEndTag();
    }

    public void startElement(String uri, String localName,
        String qName, Attributes attributes)
    throws SAXException {
    	currentElement = qName;
    	if(qName.equals("note")) {
    		currentNoteType = attributes.getValue("type");
    	} else if (qName.equals("notebook")) {
			saxUtils.writeNotebookStartTag();
		} 
    }

    public void endElement(String uri, String localName, String qName)
    throws SAXException {
    	
    	if(qName.equals("note")) {
    		switch (currentNoteType) {
			case "Note":
				note = new Note(text, date);
				break;
			case "AlertNote":
				note = new AlertNote(text, alertDate, date);
				break;
			case "ColorNote":
				note = new ColorNote(text, color, date);
				break;

			default:
				break;
			}
    		
    		if(note != null) {
    			if(currentNoteIndex != noteToDeleteIndex) {
    				saxUtils.writeNoteTag(note);
    			} else {
    				saxUtils.setNoteDeleted(true);
				}
    		}
    		
    		currentNoteIndex++;
    	}
    }

    public void characters(char ch[], int start, int length)
    throws SAXException {
    	String value = new String(ch, start, length).trim();
        if(value.length() == 0) return;
        
        if(currentElement.equals("text")) {
        	text = value;
        } else if(currentElement.equals("date")) {
        	date = new DateTime(value);
        } else if(currentElement.equals("alertDate")) {
        	alertDate = new DateTime(value);
        } else if(currentElement.equals("color")) {
        	color = value;
        }
    }
}
