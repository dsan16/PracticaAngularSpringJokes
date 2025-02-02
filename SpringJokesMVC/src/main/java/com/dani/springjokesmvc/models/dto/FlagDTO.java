package com.dani.springjokesmvc.models.dto;

import java.io.Serializable;

public class FlagDTO implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
    private String name;
    
	public FlagDTO() {
	}
	
	public FlagDTO(int id, String flag) {
		this.id = id;
		this.name = flag;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getFlag() {
		return name;
	}
	
	public void setFlag(String flag) {
		this.name = flag;
	}
}

