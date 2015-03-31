package com.epam.wt.jdbcutil;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;

import com.epam.wt.entity.AlertNote;
import com.epam.wt.entity.ColorNote;
import com.epam.wt.entity.Note;

public class JDBCUtils {
	public static String makeInsertNoteQuery(Note note) {
		switch (note.getClass().getSimpleName()) {
		case "AlertNote":
			AlertNote alertNote = (AlertNote) note;
			return "INSERT INTO notebook (text, date, alertDate) VALUES ('" + 
					alertNote.getText() + "', '" + alertNote.getDate() + 
					"', '" + alertNote.getAlertDate() + "' )";
		case "ColorNote":
			ColorNote colorNote = (ColorNote) note;
			return "INSERT INTO notebook (text, date, color) VALUES ('" + 
					colorNote.getText() + "', '" + colorNote.getDate() + 
					"', '" + colorNote.getColor() + "' )";
		case "Note":
			return "INSERT INTO notebook (text, date) VALUES ('" + 
					note.getText() + "', '" + note.getDate() + "' )";
		}
		
		return null;
	}
	
	public static Note formNoteFromResultSetLine(ResultSet rs) {
		Note note = null;
		try {
			if(rs.getString("alertDate") != null) {
				String text = rs.getString(1);
				DateTime date = new DateTime(rs.getString(3));
				DateTime alertDate = new DateTime(rs.getString(2));
				note = new AlertNote(text, alertDate, date);
			}
			
			if(rs.getString("color") != null) {
				String text = rs.getString(1);
				DateTime date = new DateTime(rs.getString(3));
				String color = rs.getString(4);
				note = new ColorNote(text, color, date);
			} else {
				String text = rs.getString(1);
				DateTime date = new DateTime(rs.getString(3));
				note = new Note(text, date);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return note;
	}
	
	
}
