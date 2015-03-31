package com.epam.wt.entity;


import org.joda.time.DateTime;

public class Note {
	
	private DateTime date;
	private StringBuilder text;
	
	public Note() { 
		this.date = new DateTime();
		this.text = null;
	}
	
	public Note(String string) {
		this.text = new StringBuilder(string);
		this.date = new DateTime();
	}
	
	public Note(String string, DateTime date) {
		this.text = new StringBuilder(string);
		this.date = date;
	}
	
	
	
	//hashCode(), equals(), toString()
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Note other = (Note) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Note [date=" + date + ", text=" + text + "]";
	}
	
	//getters-setters
	public StringBuilder getText() {
		return text;
	}
	public void setText(StringBuilder string) {
		this.text = string;
	}
	public DateTime getDate() {
		return date;
	}
	public void setDate(DateTime date) {
		this.date = date;
	}

}
