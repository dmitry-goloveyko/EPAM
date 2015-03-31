package com.epam.wt.entity;

import org.joda.time.DateTime;


public class AlertNote extends Note {

	private DateTime alertDate;
	
	
	public AlertNote(String text, DateTime alertDate) {
		super(text);
		this.alertDate = alertDate;
	}
	
	public AlertNote(String text, DateTime alertDate, DateTime date) {
		super(text, date);
		this.alertDate = alertDate;
	}
	
	//hashCode(), equals(), toString()
	@Override
	public String toString() {
		return "AlertNote [alertDate=" + alertDate + " "
				+ super.toString() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((alertDate == null) ? 0 : alertDate.hashCode());
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
		AlertNote other = (AlertNote) obj;
		if (alertDate == null) {
			if (other.alertDate != null)
				return false;
		} else if (!alertDate.equals(other.alertDate))
			return false;
		return true;
	}

	//setters-getters
	
	public DateTime getAlertDate() {
		return alertDate;
	}

	public void setAlertDate(DateTime alertDate) {
		this.alertDate = alertDate;
	}

}
