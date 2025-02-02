package com.dani.springjokesmvc.models.dto;

import java.io.Serializable;
import java.util.Set;

public class JokesDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String category;
	private String language;
	private String type;
	private String text1;
	private String text2;
	
	private Set<FlagDTO> flags;  // Campo que contendrá los FlagDTO

	// Getter y setter para flags
	public Set<FlagDTO> getFlags() {
	    return flags;
	}

	public void setFlags(Set<FlagDTO> flags) {
	    this.flags = flags;
	}
	
	public JokesDTO() {
	}
	
	public JokesDTO(int id, String category, String language, String type, String text1, String text2) {
		this.id = id;
		this.category = category;
		this.language = language;
		this.type = type;
		this.text1 = text1;
		this.text2 = text2;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getLanguage() {
		return language;
	}
	
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getText1() {
		return text1;
	}
	
	public void setText1(String text1) {
		this.text1 = text1;
	}
	
	public String getText2() {
		return text2;
	}
	
	public void setText2(String text2) {
		this.text2 = text2;
	}
}
