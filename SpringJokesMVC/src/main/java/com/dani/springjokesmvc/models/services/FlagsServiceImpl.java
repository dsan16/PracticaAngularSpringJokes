package com.dani.springjokesmvc.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dani.springjokesmvc.models.dao.IFlagsDAO;
import com.dani.springjokesmvc.models.entities.Flags;

@Service
public class FlagsServiceImpl implements IFlagsService {

	@Autowired
	private IFlagsDAO flagsDao;
	
	@Override
	public List<Flags> findAll() {
		return (List<Flags>) flagsDao.findAll();
	}

	@Override
	public Flags save(Flags flags) {
		return flagsDao.save(flags);
	}

	@Override
	public Flags findById(int id) {
		return flagsDao.findById(id).orElse(null);
	}

	@Override
	public void delete(int id) {
		flagsDao.deleteById(id);
	}
}
