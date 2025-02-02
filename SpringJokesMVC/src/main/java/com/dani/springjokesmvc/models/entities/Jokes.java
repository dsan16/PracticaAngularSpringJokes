package com.dani.springjokesmvc.models.entities;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@Table(name = "jokes")
public class Jokes implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "joke_seq")
	@SequenceGenerator(name = "joke_seq", sequenceName = "seq_jokes", allocationSize = 1)
	@Column(name = "id", unique = true, nullable = false)
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	@JsonIgnoreProperties("jokeses")
	private Categories categories;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "language_id")
	@JsonIgnoreProperties("jokeses")
	private Language language;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type_id")
	@JsonIgnoreProperties("jokeses")
	private Types types;

	@Column(name = "text1", columnDefinition = "TEXT")
	private String text1;

	@Column(name = "text2")
	private String text2;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JoinTable(name = "jokes_flags", joinColumns = {
			@JoinColumn(name = "joke_id", referencedColumnName="id") }, inverseJoinColumns = {
					@JoinColumn(name = "flag_id", referencedColumnName="id") })
	@JsonIgnoreProperties("jokeses")
	private Set<Flags> flagses = new HashSet<>(0);
	
	@OneToOne(mappedBy = "joke", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnoreProperties("joke")
	private PrimeraVez primeraVez;

	public Jokes() {
	}

	public Jokes(int id) {
		this.id = id;
	}

	public Jokes(Categories categories, Language language, Types types, String text1, String text2,
			Set<Flags> flagses) {
		this.categories = categories;
		this.language = language;
		this.types = types;
		this.text1 = text1;
		this.text2 = text2;
		this.flagses = flagses;
	}
	
	public Jokes(Categories categories, Language language, Types types, String text1, String text2,
			Set<Flags> flagses, PrimeraVez primeraVez) {
		this.categories = categories;
		this.language = language;
		this.types = types;
		this.text1 = text1;
		this.text2 = text2;
		this.flagses = flagses;
		this.primeraVez = primeraVez;
	}
	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Categories getCategories() {
		return this.categories;
	}

	public void setCategories(Categories categories) {
		this.categories = categories;
	}

	public Language getLanguage() {
		return this.language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public Types getTypes() {
		return this.types;
	}

	public void setTypes(Types types) {
		this.types = types;
	}

	public String getText1() {
		return this.text1;
	}

	public void setText1(String text1) {
		this.text1 = text1;
	}

	public String getText2() {
		return this.text2;
	}

	public void setText2(String text2) {
		this.text2 = text2;
	}

	public Set<Flags> getFlagses() {
		return this.flagses;
	}

	public void setFlagses(Set<Flags> flagses) {
		this.flagses = flagses;
	}

	public PrimeraVez getPrimeraVez() {
		return primeraVez;
	}

	public void setPrimeraVez(PrimeraVez primeraVez) {
		this.primeraVez = primeraVez;
	}

	@Override
	public String toString() {
		return id + " | " + text1 + " " + text2;
	}
}
