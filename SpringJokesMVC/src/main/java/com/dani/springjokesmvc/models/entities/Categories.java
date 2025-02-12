// default package
// Generated 28 dic 2024, 23:36:21 by Hibernate Tools 4.3.6.Final
package com.dani.springjokesmvc.models.entities;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

/**
 * Categories generated by hbm2java
 */
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "categories")
public class Categories implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String category;
	private Set<Jokes> jokeses = new HashSet<Jokes>(0);

	public Categories() {
	}

	public Categories(int id) {
		this.id = id;
	}
	
	public Categories(String category) {
		this.category = category;
	}
	
	public Categories(int id, String category) {
		this.id = id;
		this.category = category;
	}

	public Categories(int id, String category, Set<Jokes> jokeses) {
		this.id = id;
		this.category = category;
		this.jokeses = jokeses;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq")
	@SequenceGenerator(name = "category_seq", sequenceName = "seq_categories", allocationSize = 1)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "category", nullable = false)
	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "categories", cascade = CascadeType.ALL)
	public Set<Jokes> getJokeses() {
		return this.jokeses;
	}

	public void setJokeses(Set<Jokes> jokeses) {
		this.jokeses = jokeses;
	}

	@Override
	public String toString() {
		return id + " | " + category;
	}
}
