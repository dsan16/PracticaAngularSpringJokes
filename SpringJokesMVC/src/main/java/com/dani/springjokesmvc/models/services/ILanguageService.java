package com.dani.springjokesmvc.models.services;

import java.util.List;

import com.dani.springjokesmvc.models.entities.Language;

public interface ILanguageService {
	public List<Language> findAll();
	public Language save(Language language);
	public Language findById(int id);
	public void delete(int id);
}
