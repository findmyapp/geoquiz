package com.accenture.geoquiz.model;

import java.util.Date;

public class Event {
	
	private int id;
	private Date dato;
	private Place sted;
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setDato(Date dato) {
		this.dato = dato;
	}
	public Date getDato() {
		return dato;
	}
	public void setSted(Place sted) {
		this.sted = sted;
	}
	public Place getSted() {
		return sted;
	}
}
