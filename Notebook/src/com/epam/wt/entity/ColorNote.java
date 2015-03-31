package com.epam.wt.entity;

import org.joda.time.DateTime;

public class ColorNote extends Note {
	
	private String color;
	
	public ColorNote(String text, String color) {
		super(text);
		this.color = color;
	}
	
	public ColorNote(String text, String color, DateTime date) {
		super(text, date);
		this.color = color;
	}
	
	//hashCode(), equals(), toString()
	@Override
	public String toString() {
		return "ColorNote [color=" + color + " " + super.toString()
				+ "]";
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ColorNote other = (ColorNote) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		return true;
	}
	
	
	//setters-getters
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
