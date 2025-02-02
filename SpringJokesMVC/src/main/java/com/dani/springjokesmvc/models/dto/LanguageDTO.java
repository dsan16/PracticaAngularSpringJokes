package com.dani.springjokesmvc.models.dto;

import java.io.Serializable;

public class LanguageDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;

	public LanguageDTO() {
	}

	public LanguageDTO(int id, String language) {
		this.id = id;
		this.name = language;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLanguage() {
		return name;
	}

	public void setLanguage(String language) {
		this.name = language;
	}

}
