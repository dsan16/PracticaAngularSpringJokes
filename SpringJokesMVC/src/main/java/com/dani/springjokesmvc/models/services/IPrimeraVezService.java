package com.dani.springjokesmvc.models.services;

import java.util.List;

import com.dani.springjokesmvc.models.entities.PrimeraVez;
import com.dani.springjokesmvc.models.entities.Telefono;

public interface IPrimeraVezService {
    List<PrimeraVez> findAll();
    PrimeraVez findById(int id);
    PrimeraVez save(PrimeraVez primeraVez, List<Telefono> telefonos);
    PrimeraVez save(PrimeraVez primeraVez);
    void delete(int id);
    List<Object[]> findJokesWithPrimeraVezAndTelefonos();
    List<Object> findFlagsDetails(int flagId);
}
