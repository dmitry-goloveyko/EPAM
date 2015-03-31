package com.epam.wt.xml.sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXClearNotebookHandler extends DefaultHandler {
	private final SAXUtils saxUtils = SAXUtils.getInstance();
	
	public void startDocument() throws SAXException {
		saxUtils.writeNotebookStartTag();
    }

    public void endDocument() throws SAXException {
    	saxUtils.writeNotebookEndTag();
    }

    public void startElement(String uri, String localName,
        String qName, Attributes attributes)
    throws SAXException {
    }

    public void endElement(String uri, String localName, String qName)
    throws SAXException {
    	
    }

    public void characters(char ch[], int start, int length)
    throws SAXException {
    
    }
}
