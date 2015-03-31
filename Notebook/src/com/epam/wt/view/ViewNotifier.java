package com.epam.wt.view;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.epam.wt.datatempstorage.Responce;
import com.epam.wt.entity.AlertNote;
import com.epam.wt.entity.ColorNote;
import com.epam.wt.entity.Note;

public class ViewNotifier {
	private static Scanner scanner = new Scanner(System.in);
	private static Locale locale;
	private static String localizationFileString = "resources.prop";
	private static ResourceBundle localizationFile = ResourceBundle.getBundle(localizationFileString);
	
	private static final ViewNotifier instance = new ViewNotifier();
	
	public static ViewNotifier getInstance() {
		return instance;
	}

	private ViewNotifier() {
		String propertiesFileString = "resources.Notebook";
		ResourceBundle propertiesFile = ResourceBundle.getBundle(propertiesFileString);
		String language = propertiesFile.getString("NoteBookMenuLanguage");
		String country = propertiesFile.getString("NoteBookMenuCountry");
		locale = new Locale(language, country);
		localizationFile = ResourceBundle.getBundle(localizationFileString, locale);
	}
	
	public static int showMenu(){
		System.out.println(localizationFile.getString("menuItem1"));
		System.out.println(localizationFile.getString("menuItem2"));
		System.out.println(localizationFile.getString("menuItem3"));
		System.out.println(localizationFile.getString("menuItem4"));
		System.out.println(localizationFile.getString("menuItem5"));
		System.out.println(localizationFile.getString("menuItem6"));
		int result = scanner.nextInt();		
		
		return result;
	}
	
	public static DateTime enterNoteAlertDate(){
		System.out.println(localizationFile.getString("enterAlertDate"));
		String alertDate = scanner.next();
		
		DateTime date = null;
		DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-mm-yyyy");
		date = dtf.parseDateTime(alertDate);
		
		return date;
	}
	
	public static String enterNoteColor() {
		System.out.println(localizationFile.getString("enterNoteColor"));
		String color = scanner.next();
		return color;
	}
	
	public static int enterIndexOfNote() {
		System.out.println(localizationFile.getString("enterNoteIndex"));
		int index = scanner.nextInt();
		return index;
	}
	
	public static String enterNoteText() {
		System.out.println(localizationFile.getString("enterNoteText"));
		String text = scanner.next();
		return text;
	}
	
	public static void noteAddedNotification(Responce responce) {
		if (responce == null) {
			System.out.println(localizationFile.getString("noteAddedNotification"));
		} else {
			Object res = responce.getResponse();
			if (res instanceof String) {
				System.out.println(localizationFile.getString((String)res)); 
			}		
		}
		
		System.out.println();
	}
	
	public static void noteDeletedNotification(Responce responce) {
		if (responce == null) {
			System.out.println(localizationFile.getString("noteDeletedNotification"));
		} else {
			Object res = responce.getResponse();
			if (res instanceof String) {
				System.out.println(localizationFile.getString((String)res)); 
			}		
		}

		System.out.println();
	}
	
	public static void noteBookClearedNotification(Responce responce) {
		if (responce == null) {
			System.out.println(localizationFile.getString("noteBookClearedNotification"));
		} else {
			Object res = responce.getResponse();
			if (res instanceof String) {
				System.out.println(localizationFile.getString((String)res)); 
			}		
		}
		
		System.out.println();
	}
	
	public static void noteInfoNotification(Responce responce) {
		Object res = responce.getResponse();
		
		if(res instanceof Note) {
			Note note = (Note) responce.getResponse();
			if(note instanceof AlertNote) {
				alertNoteFoundNotification((AlertNote) note);
				return;
			} else if(note instanceof ColorNote) {
				colorNoteFoundNotification((ColorNote) note);
				return;
			} else {
				noteFoundNotification(note);
			}
		} else {
			System.out.println(localizationFile.getString((String)res));
		}
		
		
		System.out.println();
	}
	
	private static void noteFoundNotification(Note note) {
		System.out.println(localizationFile.getString("noteText"));
		System.out.println(note.getText());
		System.out.println(localizationFile.getString("noteDate"));
		System.out.println(note.getDate().toLocalDate() + " " + note.getDate().toLocalTime());
	}
	
	private static void alertNoteFoundNotification(AlertNote note) {
		noteFoundNotification(note);
		System.out.println(localizationFile.getString("noteAlertDate"));
		System.out.println(note.getAlertDate().toLocalDate() + " " + note.getAlertDate().toLocalTime());
		System.out.println();
	}
	
	private static void colorNoteFoundNotification(ColorNote note) {
		noteFoundNotification(note);
		System.out.println(localizationFile.getString("noteColor"));
		System.out.println(note.getColor());
		System.out.println();
	}
}
