package com.epam.wt.xml.sax;

import org.joda.time.DateTime;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.epam.wt.entity.AlertNote;
import com.epam.wt.entity.ColorNote;
import com.epam.wt.entity.Note;

public class SAXGetNoteHandler extends DefaultHandler {
	private String currentElement;
	private String currentNoteType;
	private Note note;
	private String text;
	private DateTime date;
	private DateTime alertDate;
	private String color;
	private final SAXUtils saxUtils= SAXUtils.getInstance();
	private final int noteToShowIndex;
	private int currentNoteIndex = 0;
	
	public SAXGetNoteHandler(int index) {
		noteToShowIndex = index;
	}
	
	public void startDocument() throws SAXException {
    }

    public void endDocument() throws SAXException {
    }

    public void startElement(String uri, String localName,
        String qName, Attributes attributes)
    throws SAXException {
    	currentElement = qName;
    	if(qName.equals("note")) {
    		currentNoteType = attributes.getValue("type");
    	}
    }

    public void endElement(String uri, String localName, String qName)
    throws SAXException {
    	
    	if(qName.equals("note")) {
    		if(currentNoteIndex == noteToShowIndex) {
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
    				saxUtils.setNoteToShow(note);
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
