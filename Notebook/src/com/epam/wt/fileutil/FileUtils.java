package com.epam.wt.fileutil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;

import com.epam.wt.entity.AlertNote;
import com.epam.wt.entity.ColorNote;
import com.epam.wt.entity.Note;

public class FileUtils {
	public static final String resourceDirectory = System.getProperty("user.dir") + "\\src\\resources\\";
	
	public static File createOrOpenFile(String fileName) {
		
		File file = new File(resourceDirectory + fileName);
		
		if(file.exists()) {
			return file;
		} else {

		}
		
		return null;
	}

	public static int countLines(String filename) throws IOException {
	    InputStream is = new BufferedInputStream(new FileInputStream(filename));
	    try {
	        byte[] c = new byte[1024];
	        int count = 0;
	        int readChars = 0;
	        boolean empty = true;
	        while ((readChars = is.read(c)) != -1) {
	            empty = false;
	            for (int i = 0; i < readChars; ++i) {
	                if (c[i] == '\n') {
	                    ++count;
	                }
	            }
	        }
	        return (count == 0 && !empty) ? 1 : count;
	    } finally {
	        is.close();
	    }
	}

	public static Note makeNoteFromFileLine(String line) {
		Pattern p = Pattern.compile("[a-zA-z]+");
		Matcher m = p.matcher(line);
		m.find();
		int start = m.start();
		int end = m.end();
		
		Note note = null;
		switch (line.substring(start, end)) {
		case "AlertNote":
			note = formAlertNote(line);
			break;
			
		case "ColorNote":
			note = formColorNote(line);
			break;
			
		default:
			note = formNote(line);
			break;
		}
		
		return note;
	}
	
	private static AlertNote formAlertNote(String line) {
		DateTime alertDateTime = getAlertDate(line);
		DateTime date = getDate(line);
		String text = getText(line);
		
		AlertNote note = new AlertNote(text, alertDateTime, date);
		
		return note;
	}

	private static ColorNote formColorNote(String line) {
		String color = getColor(line);
		DateTime date = getDate(line);
		String text = getText(line);
		
		ColorNote note = new ColorNote(text, color, date);
		
		return note;
	}
	
	private static Note formNote(String line) {
		DateTime date = getDate(line);
		String text = getText(line);
		
		Note note = new Note(text, date);
		
		return note;
	}
	
	private static String getValue(String key, String line) {
		key += "=";
		
		Pattern p = Pattern.compile(key + "[^\\s,\\]]+");
		Matcher m = p.matcher(line);
		m.find();
		int start = m.start() + key.length();
		int end = m.end();
		
		String value = line.substring(start, end);
		
		return value;
	}
	
	private static DateTime getAlertDate(String line) {
		String alertDateString = "alertDate=";
		
		Pattern p = Pattern.compile(alertDateString + "\\S+");
		Matcher m = p.matcher(line);
		m.find();
		int start = m.start() + alertDateString.length();
		int end = m.end();
		
		String alertDateValue = line.substring(start, end);
		
		DateTime alertDate = new DateTime(alertDateValue);
		
		return alertDate;
	}
	
	private static DateTime getDate(String line) {
		String dateValue = getValue("date", line);
		DateTime date = new DateTime(dateValue);
		
		return date;
	}
	
	private static String getText(String line) {
		String text = getValue("text", line);
		
		return text;
	}
	
	private static String getColor(String line) {
		String color = getValue("color", line);
		
		return color;
	}

	
}
