package com.accenture.geoquiz.model;

/**
 *	Class to store notifications from the quizservice to controller
 */
public class Status {
	private String notification;
	private int id;
	private boolean error;
	
	public Status() {
		notification = "";
		error = false;
		id = 0;
	}
	public String getNotification() {
		return notification;
	}
	public void setNotification(String notification) {
		this.notification = notification;
	}
	public boolean isError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
}
