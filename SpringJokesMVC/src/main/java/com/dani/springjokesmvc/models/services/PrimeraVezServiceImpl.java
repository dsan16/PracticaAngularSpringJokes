package com.dani.springjokesmvc.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dani.springjokesmvc.models.dao.IPrimeraVezDAO;
import com.dani.springjokesmvc.models.dao.ITelefonoDAO;
import com.dani.springjokesmvc.models.entities.PrimeraVez;
import com.dani.springjokesmvc.models.entities.Telefono;

@Service
public class PrimeraVezServiceImpl implements IPrimeraVezService {

    @Autowired
    private IPrimeraVezDAO primeraVezDao;

    @Autowired
    private ITelefonoDAO telefonoDao;

    @Override
    @Transactional(readOnly = true)
    public List<PrimeraVez> findAll() {
        return (List<PrimeraVez>) primeraVezDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public PrimeraVez findById(int id) {
        return primeraVezDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public PrimeraVez save(PrimeraVez primeraVez, List<Telefono> telefonos) {
        PrimeraVez saved = primeraVezDao.save(primeraVez);
        for (Telefono telefono : telefonos) {
            telefono.setPrimeraVez(saved);
            telefonoDao.save(telefono);
        }
        return saved;
    }
    
    @Override
    public PrimeraVez save(PrimeraVez primeraVez) {
        primeraVez = primeraVezDao.save(primeraVez);

        for (Telefono telefono : primeraVez.getTelefonos()) {
            telefono.setPrimeraVez(primeraVez);
            telefonoDao.save(telefono);
        }

        return primeraVez;
    }

    @Override
    @Transactional
    public void delete(int id) {
    	PrimeraVez primeraVez = findById(id);
        telefonoDao.deleteAll(primeraVez.getTelefonos());
        primeraVezDao.delete(primeraVez);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> findJokesWithPrimeraVezAndTelefonos() {
        return primeraVezDao.findJokesWithPrimeraVezAndTelefonos();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object> findFlagsDetails(int flagId) {
        return primeraVezDao.findFlagsDetails(flagId);
    }
}
