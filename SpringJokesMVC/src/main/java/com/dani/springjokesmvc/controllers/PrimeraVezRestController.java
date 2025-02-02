package com.dani.springjokesmvc.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.dani.springjokesmvc.models.entities.Jokes;
import com.dani.springjokesmvc.models.entities.PrimeraVez;
import com.dani.springjokesmvc.models.entities.Telefono;
import com.dani.springjokesmvc.models.services.IJokesService;
import com.dani.springjokesmvc.models.services.IPrimeraVezService;
import com.dani.springjokesmvc.models.services.ITelefonoService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"*"})
public class PrimeraVezRestController {

    @Autowired
    private IPrimeraVezService primeraVezService;
    
    @Autowired
    private IJokesService jokesService;
    
    @Autowired
    private ITelefonoService telefonoService;

    @PostMapping({"/primera_vez", "/primera_vez/"})
    @Transactional
    public ResponseEntity<?> createPrimeraVez(@Valid @RequestBody PrimeraVez primeraVez, BindingResult result) {
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .toList();
            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (primeraVez.getJoke() == null || primeraVez.getJoke().getId() == 0) {
            response.put("mensaje", "Debe asociar un chiste válido.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        
		if (primeraVez.getTelefonos() == null || primeraVez.getTelefonos().isEmpty()) {
			response.put("mensaje", "Debe proporcionar al menos un teléfono.");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		
		boolean hasInvalidPhone = primeraVez.getTelefonos().stream()
			    .anyMatch(t -> t.getNumero() == null || t.getNumero().trim().isEmpty());

		if (hasInvalidPhone) {
		    response.put("mensaje", "Todos los teléfonos deben tener un número válido.");
		    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

        Jokes joke = jokesService.findById(primeraVez.getJoke().getId());
        if (joke == null) {
            response.put("mensaje", "El chiste con ID " + primeraVez.getJoke().getId() + " no existe.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        if (primeraVez.getTelefonos() == null || primeraVez.getTelefonos().isEmpty()) {
            response.put("mensaje", "Debe proporcionar al menos un teléfono.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            for (Telefono telefono : primeraVez.getTelefonos()) {
                telefono.setPrimeraVez(primeraVez);
            }

            PrimeraVez savedPrimeraVez = primeraVezService.save(primeraVez);

            response.put("mensaje", "Primera vez creada con éxito!");
            response.put("primeraVez", savedPrimeraVez);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPrimeraVez);
        } catch (Exception e) {
            response.put("mensaje", "❌ Error al realizar la inserción en la base de datos.");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("/primera_vez")
    public List<PrimeraVez> listAll() {
        return primeraVezService.findAll();
    }

    @GetMapping("/primera_vez/{id}")
    public ResponseEntity<?> showPrimeraVez(@PathVariable int id) {
    	PrimeraVez primeraVez = null;
    	Map<String, Object> response = new HashMap<>();
    	try {
    		primeraVez = primeraVezService.findById(id);
    	}
    	catch (Exception e) {
    		response.put("mensaje", "❌ Error al realizar la consulta en la base de datos.");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    	}
        if (primeraVez == null) {
			response.put("mensaje", "La primera vez con ID " + id + " no existe.");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(primeraVez);
    }
    
    @PutMapping({"/primera_vez/{id}", "/primera_vez/{id}/"})
    @Transactional
    public ResponseEntity<?> update(@Valid @RequestBody PrimeraVez primeraVez, @PathVariable int id, BindingResult result) {
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .toList();
            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        PrimeraVez primeraVezToUpdate = primeraVezService.findById(id);
        if (primeraVezToUpdate == null) {
            response.put("mensaje", "Error: PrimeraVez con ID " + id + " no existe.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        if (primeraVez.getJoke() == null || primeraVez.getJoke().getId() == 0) {
            response.put("mensaje", "Debe asociar un chiste válido.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        
		if (primeraVez.getTelefonos() == null || primeraVez.getTelefonos().isEmpty()) {
			response.put("mensaje", "Debe proporcionar al menos un teléfono.");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		
		boolean hasInvalidPhone = primeraVez.getTelefonos().stream()
			    .anyMatch(t -> t.getNumero() == null || t.getNumero().trim().isEmpty());

			if (hasInvalidPhone) {
			    response.put("mensaje", "Todos los teléfonos deben tener un número válido.");
			    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
        
        Jokes joke = jokesService.findById(primeraVez.getJoke().getId());
        if (joke == null) {
            response.put("mensaje", "El chiste con ID " + primeraVez.getJoke().getId() + " no existe.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            primeraVezToUpdate.setPrograma(primeraVez.getPrograma());
            primeraVezToUpdate.setFechaEmision(primeraVez.getFechaEmision());
            primeraVezToUpdate.setJoke(joke);

            List<Telefono> nuevosTelefonos = primeraVez.getTelefonos();

            primeraVezToUpdate.getTelefonos().removeIf(tel -> 
                nuevosTelefonos.stream().noneMatch(nt -> nt.getId() != null && nt.getId().equals(tel.getId()))
            );

            for (Telefono telefono : nuevosTelefonos) {
                telefono.setPrimeraVez(primeraVezToUpdate);
                if (telefono.getId() == null) {
                    telefonoService.save(telefono);
                }
            }

            primeraVezToUpdate.getTelefonos().clear();
            primeraVezToUpdate.getTelefonos().addAll(nuevosTelefonos);

            PrimeraVez updatedPrimeraVez = primeraVezService.save(primeraVezToUpdate);

            response.put("mensaje", "✅ PrimeraVez ha sido actualizada con éxito!");
            response.put("primeraVez", updatedPrimeraVez);
            return ResponseEntity.status(HttpStatus.CREATED).body(updatedPrimeraVez);
        } catch (Exception e) {
            response.put("mensaje", "❌ Error al actualizar en la base de datos.");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





    @DeleteMapping("/primera_vez/{id}")
    public ResponseEntity<?> deletePrimeraVez(@PathVariable int id) {
    	PrimeraVez primeraVez = primeraVezService.findById(id);
    	Map<String, Object> response = new HashMap<>();
    	
    	if (primeraVez == null) {
			response.put("mensaje", "Error: PrimeraVez con ID " + id + " no existe.");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
    	
    	try {
    		primeraVezService.delete(id);
		}
    	catch (Exception e) {
			response.put("mensaje", "Error al eliminar la primera vez de la base de datos.");
			response.put("error", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    	}
        
        response.put("mensaje", "¡Primera vez eliminada con éxito!");
        return ResponseEntity.ok(response);
    }

    @GetMapping({"primera_vez/with_jokes", "/primera_vez/with_jokes/"})
    public ResponseEntity<?> getJokesWithPrimeraVezAndTelefonos() {
        List<Object[]> results = primeraVezService.findJokesWithPrimeraVezAndTelefonos();
        List<Map<String, Object>> responseList = new ArrayList<>();

        for (Object[] row : results) {
            Jokes joke = (Jokes) row[0];
            PrimeraVez primeraVez = (PrimeraVez) row[1];
            @SuppressWarnings("unchecked")
			List<Telefono> telefono = (List<Telefono>) row[2];

            Map<String, Object> jokeData = new HashMap<>();
            jokeData.put("id", joke.getId());
            jokeData.put("text1", joke.getText1());
            jokeData.put("text2", joke.getText2());

            if (primeraVez != null) {
                Map<String, Object> primeraVezData = new HashMap<>();
                primeraVezData.put("id", primeraVez.getId());
                primeraVezData.put("programa", primeraVez.getPrograma());
                primeraVezData.put("fecha_emision", primeraVez.getFechaEmision());

                primeraVezData.put("telefonos", telefono);

                jokeData.put("primeraVez", primeraVezData);
            } else {
                jokeData.put("primeraVez", null);
            }

            responseList.add(jokeData);
        }

        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/jokes/without_tv")
	public List<Jokes> jokesWithoutPrimeraVez() {
		return jokesService.findJokesWithoutPrimeraVez();
	}
}
