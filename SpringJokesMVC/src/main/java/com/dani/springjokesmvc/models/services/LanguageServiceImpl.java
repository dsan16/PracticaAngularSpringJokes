package com.dani.springjokesmvc.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dani.springjokesmvc.models.dao.ILanguageDAO;
import com.dani.springjokesmvc.models.entities.Language;

@Service
public class LanguageServiceImpl implements ILanguageService {
	@Autowired
	private ILanguageDAO languageRepository;
	
	@Override
	public List<Language> findAll() {
		return (List<Language>) languageRepository.findAll();
	}
	
	@Override
	public Language save(Language language) {
		return languageRepository.save(language);
	}
	
	@Override
	public Language findById(int id) {
		return languageRepository.findById(id).orElse(null);
    }
	
	@Override
	public void delete(int id) {
        languageRepository.deleteById(id);
	}

}
