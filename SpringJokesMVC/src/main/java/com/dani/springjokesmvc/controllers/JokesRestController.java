package com.dani.springjokesmvc.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dani.springjokesmvc.models.dto.CategoriesDTO;
import com.dani.springjokesmvc.models.dto.FlagDTO;
import com.dani.springjokesmvc.models.dto.LanguageDTO;
import com.dani.springjokesmvc.models.dto.TypesDTO;
import com.dani.springjokesmvc.models.entities.Categories;
import com.dani.springjokesmvc.models.entities.Flags;
import com.dani.springjokesmvc.models.entities.Jokes;
import com.dani.springjokesmvc.models.entities.Language;
import com.dani.springjokesmvc.models.entities.Types;
import com.dani.springjokesmvc.models.services.ICategoriesService;
import com.dani.springjokesmvc.models.services.IFlagsService;
import com.dani.springjokesmvc.models.services.IJokesService;
import com.dani.springjokesmvc.models.services.ILanguageService;
import com.dani.springjokesmvc.models.services.ITypesService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api")
public class JokesRestController {
	
	@Autowired
	private IJokesService jokesService;
	
	@Autowired
	private IFlagsService flagsService;
	
	@Autowired
	private ITypesService typesService;
	
	@Autowired
	private ILanguageService languageService;
	
	@Autowired
	private ICategoriesService categoriesService;
	
	@GetMapping({"/flags", "/flags/"})
	public List<Flags> indexFlags() {
	    return flagsService.findAll();
	}
	
	@GetMapping({"/flagsDTO", "/flagsDTO/"})
	public List<FlagDTO> showFlagsDTO() {
        return flagsService.findAll().stream()
        		.map(flags -> new FlagDTO(flags.getId(), flags.getFlag()))
        		.collect(Collectors.toList());
    }

	@GetMapping({"/flags/{id}", "/flags/{id}/"})
	public ResponseEntity<?> showFlag(@PathVariable int id) {
	    Flags flag = null;
	    Map<String, Object> response = new HashMap<>();
	    try {
	        flag = flagsService.findById(id);
	    } catch (Exception e) {
	        response.put("mensaje", "Error al realizar la consulta en la base de datos");
	        response.put("error", e.getMessage());
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	    if (flag == null) {
	        response.put("mensaje", "La bandera ID: ".concat(id + " no existe en la base de datos!"));
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    }
	    return ResponseEntity.ok(flag);
	}

	@PostMapping({"/flags", "/flags/"})
	@Transactional
	public ResponseEntity<?> saveFlags(@Valid @RequestBody Flags flags, BindingResult result) {
	    Flags savedFlag = null;
	    Map<String, Object> response = new HashMap<>();
	    
	    if (result.hasErrors()) {
	        List<String> errors = result.getFieldErrors().stream()
	                .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
	                .toList();
	        response.put("errors", errors);
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }
	    
		if (flags.getFlag() == null || flags.getFlag().isBlank()) {
			response.put("mensaje", "El campo 'flag' no puede estar vacío!");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	    
	    try {
	        savedFlag = flagsService.save(flags);
	    } catch (Exception e) {
	        response.put("mensaje", "Error al realizar la inserción en la base de datos");
	        response.put("error", e.getMessage());
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	    
	    response.put("mensaje", "La bandera ha sido creada con éxito!");
	    response.put("flag", savedFlag);
	    return ResponseEntity.status(HttpStatus.CREATED).body(savedFlag);
	}

	@PutMapping({"/flags/{id}", "/flags/{id}/"})
	@Transactional
	public ResponseEntity<?> updateFlags(@Valid @RequestBody Flags flags, @PathVariable int id, BindingResult result) {
	    Flags flagsToUpdate = flagsService.findById(id);
	    Flags updatedFlag = null;
	    Map<String, Object> response = new HashMap<>();
	    
	    if (result.hasErrors()) {
	        List<String> errors = result.getFieldErrors().stream()
	                .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage()).toList();
	        response.put("errors", errors);
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }
	    
	    if (flagsToUpdate == null) {
	        response.put("mensaje",
	                "Error: no se pudo editar, la bandera ID: ".concat(id + " no existe en la base de datos!"));
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    }
	    
		if (flags.getFlag() == null || flags.getFlag().isBlank()) {
			response.put("mensaje", "El campo 'flag' no puede estar vacío!");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	    
	    try {
	        flagsToUpdate.setFlag(flags.getFlag());
	        flagsToUpdate.setJokeses(flags.getJokeses());
	        
	        updatedFlag = flagsService.save(flagsToUpdate);
	    } catch (Exception e) {
	        response.put("mensaje", "Error al realizar la actualización en la base de datos");
	        response.put("error", e.getMessage());
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	    
	    response.put("mensaje", "La bandera ha sido actualizada con éxito!");
	    response.put("flag", updatedFlag);
	    return ResponseEntity.status(HttpStatus.CREATED).body(updatedFlag);
	}

	@DeleteMapping({"/flags/{id}", "/flags/{id}/"})
	@Transactional
	public ResponseEntity<?> deleteFlags(@PathVariable int id) {
	    Flags flag = flagsService.findById(id);
	    Map<String, Object> response = new HashMap<>();
	    
	    if (flag == null) {
	        response.put("mensaje",
	                "Error: no se pudo borrar, la bandera ID: ".concat(id + " no existe en la base de datos!"));
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    }
	    
	    try {
	    	
	        flagsService.delete(id);
	    } catch (Exception e) {
	        response.put("mensaje", "Error al realizar la eliminación en la base de datos");
	        response.put("error", e.getMessage());
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	    
	    response.put("mensaje", "La bandera ha sido eliminada con éxito!");
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}

	
	@GetMapping({"/jokes", "/jokes/"})
	public List<Jokes> index() {
	    return jokesService.findAll();
	}
	
	@GetMapping({"/jokes/{id}", "/jokes/{id}/"})
	public ResponseEntity<?> show(@PathVariable int id) {
		Jokes joke = null;
		Map<String, Object> response = new HashMap<>();
		try {
			joke = jokesService.findById(id);
		} catch (Exception e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (joke == null) {
			response.put("mensaje", "El chiste ID: ".concat(id + " no existe en la base de datos!"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return ResponseEntity.ok(joke);
	}


	@PostMapping({"/jokes", "/jokes/"})
	@Transactional
	public ResponseEntity<?> save(@Valid @RequestBody Jokes jokes, BindingResult result) {
	    Jokes savedJoke = null;
	    Map<String, Object> response = new HashMap<>();
	    
	    if(result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .toList();
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
	    
	    if (jokes.getCategories() == null || jokes.getCategories().getId() == 0) {
	        response.put("mensaje", "El campo 'categories' no puede estar vacío o nulo.");
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }
	    
	    if (jokes.getLanguage() == null || jokes.getLanguage().getId() == 0) {
	        response.put("mensaje", "El campo 'language' no puede estar vacío o nulo.");
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }

	    if (jokes.getTypes() == null || jokes.getTypes().getId() == 0) {
	        response.put("mensaje", "El campo 'types' no puede estar vacío o nulo.");
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }

	    if (jokes.getText1() == null || jokes.getText1().isBlank()) {
	        response.put("mensaje", "El campo 'text1' no puede estar vacío o nulo.");
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }
	    
		if (jokes.getText2() != null && !jokes.getText2().isBlank() && jokes.getTypes().getId() == 1) {
			response.put("mensaje", "El campo 'text2' no puede tener valor si el tipo es 'chiste'");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
	    
		try {
			savedJoke = jokesService.save(jokes);
		} catch (Exception e) {
			response.put("mensaje", "Error al realizar la inserción en la base de datos");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El chiste ha sido creado con éxito!");
		response.put("joke", savedJoke);
	    return ResponseEntity.status(HttpStatus.CREATED).body(savedJoke);
	}

	@PutMapping({"/jokes/{id}", "/jokes/{id}/"})
	@Transactional
	public ResponseEntity<?> update(@Valid @RequestBody Jokes jokes, @PathVariable int id, BindingResult result) {
		Jokes jokesToUpdate = jokesService.findById(id);
		Jokes updatedJoke = null;
		Map<String, Object> response = new HashMap<>();
		
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage()).toList();
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (jokesToUpdate == null) {
			response.put("mensaje",
					"Error: no se pudo editar, el chiste ID: ".concat(id + " no existe en la base de datos!"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		if (jokes.getCategories() == null || jokes.getCategories().getId() == 0) {
	        response.put("mensaje", "El campo 'categories' no puede estar vacío o nulo.");
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }
	    
	    if (jokes.getLanguage() == null || jokes.getLanguage().getId() == 0) {
	        response.put("mensaje", "El campo 'language' no puede estar vacío o nulo.");
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }

	    if (jokes.getTypes() == null || jokes.getTypes().getId() == 0) {
	        response.put("mensaje", "El campo 'types' no puede estar vacío o nulo.");
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }

	    if (jokes.getText1() == null || jokes.getText1().isBlank()) {
	        response.put("mensaje", "El campo 'text1' no puede estar vacío o nulo.");
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }
	    
		if (jokes.getText2() != null && !jokes.getText2().isBlank() && jokes.getTypes().getId() == 1) {
			response.put("mensaje", "El campo 'text2' no puede tener valor si el tipo es 'chiste'");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			jokesToUpdate.setCategories(jokes.getCategories());
			jokesToUpdate.setFlagses(jokes.getFlagses());
			jokesToUpdate.setText1(jokes.getText1());
			jokesToUpdate.setText2(jokes.getText2());
			jokesToUpdate.setTypes(jokes.getTypes());
			jokesToUpdate.setLanguage(jokes.getLanguage());
			
			updatedJoke = jokesService.save(jokesToUpdate);
		}
		catch (Exception e) {
			response.put("mensaje", "Error al realizar la actualización en la base de datos");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El chiste ha sido actualizado con éxito!");
		response.put("joke", updatedJoke);
		return ResponseEntity.status(HttpStatus.CREATED).body(updatedJoke);
	}
	
	@DeleteMapping({"/jokes/{id}", "/jokes/{id}/"})
	@Transactional
	public ResponseEntity<?> delete(@PathVariable int id) {
		Jokes joke = jokesService.findById(id);
		Map<String, Object> response = new HashMap<>();
		
		if (joke == null) {
			response.put("mensaje",
					"Error: no se pudo borrar, el chiste ID: ".concat(id + " no existe en la base de datos!"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			jokesService.delete(id);
		} catch (Exception e) {
			response.put("mensaje", "Error al realizar la eliminación en la base de datos");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El chiste ha sido eliminado con éxito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

    // Categories
    @GetMapping({"/categories", "/categories/"})
    public List<Categories> indexCategories() {
        return categoriesService.findAll();
    }

    @GetMapping({"/categoriesDTO", "/categoriesDTO/"})
	public List<CategoriesDTO> showCategoriesDTO() {
        return categoriesService.findAll().stream()
        		.map(cats -> new CategoriesDTO(cats.getId(), cats.getCategory()))
        		.collect(Collectors.toList());
    }

    
    @GetMapping({"/categories/{id}", "/categories/{id}/"})
    public ResponseEntity<?> showCategories(@PathVariable int id) {
        Categories category = null;
        Map<String, Object> response = new HashMap<>();
        try {
            category = categoriesService.findById(id);
        } catch (Exception e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (category == null) {
            response.put("mensaje", "La categoría ID: ".concat(id + " no existe en la base de datos!"));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(category);
    }

    @PostMapping({"/categories", "/categories/"})
    @Transactional
    public ResponseEntity<?> saveCategory(@Valid @RequestBody Categories category, BindingResult result) {
        Categories savedCategory = null;
        Map<String, Object> response = new HashMap<>();
        
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .toList();
            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        
		if (category.getCategory() == null || category.getCategory().isBlank()) {
			response.put("mensaje", "El campo 'category' no puede estar vacío!");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
        
        try {
            savedCategory = categoriesService.save(category);
        } catch (Exception e) {
            response.put("mensaje", "Error al realizar la inserción en la base de datos");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        response.put("mensaje", "La categoría ha sido creada con éxito!");
        response.put("category", savedCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }
    
    @PutMapping({"/categories/{id}", "/categories/{id}/"})
    @Transactional
    public ResponseEntity<?> updateCategory(@Valid @RequestBody Categories category, @PathVariable int id, BindingResult result) {
        Categories categoryToUpdate = categoriesService.findById(id);
        Categories updatedCategory = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage()).toList();
            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (categoryToUpdate == null) {
            response.put("mensaje",
                    "Error: no se pudo editar, la categoría ID: ".concat(id + " no existe en la base de datos!"));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        
        if (category.getCategory() == null || category.getCategory().isBlank()) {
        	response.put("mensaje", "El campo 'category' no puede estar vacío!");
        	return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            categoryToUpdate.setCategory(category.getCategory());

            if (category.getJokeses() != null) {
                categoryToUpdate.setJokeses(category.getJokeses());
            }
            updatedCategory = categoriesService.save(categoryToUpdate);
        } catch (Exception e) {
            response.put("mensaje", "Error al realizar la actualización en la base de datos");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "La categoría ha sido actualizada con éxito!");
        response.put("category", updatedCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedCategory);
    }


    @DeleteMapping({"/categories/{id}", "/categories/{id}/"})
    @Transactional
    public ResponseEntity<?> deleteCategory(@PathVariable int id) {
        Categories category = categoriesService.findById(id);
        Map<String, Object> response = new HashMap<>();
        
        if (category == null) {
            response.put("mensaje",
                    "Error: no se pudo borrar, la categoría ID: ".concat(id + " no existe en la base de datos!"));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        
        try {
            categoriesService.delete(id);
        } catch (Exception e) {
            response.put("mensaje", "Error al realizar la eliminación en la base de datos");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        response.put("mensaje", "La categoría ha sido eliminada con éxito!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Types
    @GetMapping({"/types", "/types/"})
    public List<Types> indexTypes() {
        return typesService.findAll();
    }
    
    @GetMapping({"/typesDTO", "/typesDTO/"})
	public List<TypesDTO> showTypesDTO() {
        return typesService.findAll().stream()
        		.map(types -> new TypesDTO(types.getId(), types.getType()))
        		.collect(Collectors.toList());
    }

    @PutMapping({"/types/{id}", "/types/{id}/"})
    @Transactional
    public ResponseEntity<?> updateType(@Valid @RequestBody Types type, @PathVariable int id, BindingResult result) {
        Types typeToUpdate = typesService.findById(id);
        Types updatedType = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage()).toList();
            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (typeToUpdate == null) {
            response.put("mensaje",
                    "Error: no se pudo editar, el tipo ID: ".concat(id + " no existe en la base de datos!"));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        
		if (type.getType() == null || type.getType().isBlank()) {
			response.put("mensaje", "El campo 'type' no puede estar vacío!");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

        try {
            typeToUpdate.setType(type.getType());

            if (type.getJokeses() != null) {
                typeToUpdate.setJokeses(type.getJokeses());
            }

            updatedType = typesService.save(typeToUpdate);
        } catch (Exception e) {
            response.put("mensaje", "Error al realizar la actualización en la base de datos");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El tipo ha sido actualizado con éxito!");
        response.put("type", updatedType);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedType);
    }

    
    @GetMapping({"/types/{id}", "/types/{id}/"})
    public ResponseEntity<?> showTypes(@PathVariable int id) {
        Types type = null;
        Map<String, Object> response = new HashMap<>();
        try {
            type = typesService.findById(id);
        } catch (Exception e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (type == null) {
            response.put("mensaje", "El tipo ID: ".concat(id + " no existe en la base de datos!"));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(type);
    }

    @PostMapping({"/types", "/types/"})
    @Transactional
    public ResponseEntity<?> saveType(@Valid @RequestBody Types type, BindingResult result) {
        Types savedType = null;
        Map<String, Object> response = new HashMap<>();
        
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .toList();
            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        
        if (type.getType() == null || type.getType().isBlank()) {
        	response.put("mensaje", "El campo 'type' no puede estar vacío!");
        	return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        
        try {
            savedType = typesService.save(type);
        } catch (Exception e) {
            response.put("mensaje", "Error al realizar la inserción en la base de datos");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        response.put("mensaje", "El tipo ha sido creado con éxito!");
        response.put("type", savedType);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedType);
    }

    @DeleteMapping({"/types/{id}", "/types/{id}/"})
    @Transactional
    public ResponseEntity<?> deleteType(@PathVariable int id) {
        Types type = typesService.findById(id);
        Map<String, Object> response = new HashMap<>();
        
        if (type == null) {
            response.put("mensaje",
                    "Error: no se pudo borrar, el tipo ID: ".concat(id + " no existe en la base de datos!"));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        
        try {
            typesService.delete(id);
        } catch (Exception e) {
            response.put("mensaje", "Error al realizar la eliminación en la base de datos");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        response.put("mensaje", "El tipo ha sido eliminado con éxito!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Languages
    @GetMapping({"/languages", "/languages/"})
    public List<Language> indexLanguages() {
        return languageService.findAll();
    }
    
    @GetMapping({"/languagesDTO", "/languagesDTO/"})
	public List<LanguageDTO> showLanguagesDTO() {
		return languageService.findAll().stream()
				.map(language -> new LanguageDTO(language.getId(), language.getLanguage()))
				.collect(Collectors.toList());
	}

    @GetMapping({"/languages/{id}", "/languages/{id}/"})
    public ResponseEntity<?> showLanguages(@PathVariable int id) {
        Language language = null;
        Map<String, Object> response = new HashMap<>();
        try {
            language = languageService.findById(id);
        } catch (Exception e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (language == null) {
            response.put("mensaje", "El idioma ID: ".concat(id + " no existe en la base de datos!"));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(language);
    }

    @PutMapping({"/languages/{id}", "/languages/{id}/"})
    @Transactional
    public ResponseEntity<?> updateLanguage(@Valid @RequestBody Language language, @PathVariable int id, BindingResult result) {
        Language languageToUpdate = languageService.findById(id);
        Language updatedLanguage = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage()).toList();
            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (languageToUpdate == null) {
            response.put("mensaje",
                    "Error: no se pudo editar, el idioma ID: ".concat(id + " no existe en la base de datos!"));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

		if (language.getLanguage() == null || language.getLanguage().isBlank() || language.getCode() == null || language.getCode().isBlank()) {
			response.put("mensaje", "No puede haber ningún campo vacío!");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(language.getCode().length() != 2) {
			response.put("mensaje", "El campo 'code' debe tener dos caracteres!");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
        
        try {
            languageToUpdate.setCode(language.getCode());
            languageToUpdate.setLanguage(language.getLanguage());
            
            updatedLanguage = languageService.save(languageToUpdate);
        } catch (Exception e) {
            response.put("mensaje", "Error al realizar la actualización en la base de datos");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El idioma ha sido actualizado con éxito!");
        response.put("language", updatedLanguage);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedLanguage);
    }

    
    @PostMapping({"/languages", "/languages/"})
    @Transactional
    public ResponseEntity<?> saveLanguage(@Valid @RequestBody Language language, BindingResult result) {
        Language savedLanguage = null;
        Map<String, Object> response = new HashMap<>();
        
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .toList();
            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        
        if (language.getLanguage() == null || language.getLanguage().isBlank() || language.getCode() == null || language.getCode().isBlank()) {
			response.put("mensaje", "No puede haber ningún campo vacío!");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(language.getCode().length() != 2) {
			response.put("mensaje", "El campo 'code' debe tener dos caracteres!");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
        
        try {
            savedLanguage = languageService.save(language);
        } catch (Exception e) {
            response.put("mensaje", "Error al realizar la inserción en la base de datos");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        response.put("mensaje", "El idioma ha sido creado con éxito!");
        response.put("language", savedLanguage);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLanguage);
    }

    @DeleteMapping({"/languages/{id}", "/languages/{id}/"})
    @Transactional
    public ResponseEntity<?> deleteLanguage(@PathVariable int id) {
        Language language = languageService.findById(id);
        Map<String, Object> response = new HashMap<>();
        
        if (language == null) {
            response.put("mensaje",
                    "Error: no se pudo borrar, el idioma ID: ".concat(id + " no existe en la base de datos!"));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        
        try {
            languageService.delete(id);
        } catch (Exception e) {
            response.put("mensaje", "Error al realizar la eliminación en la base de datos");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        response.put("mensaje", "El idioma ha sido eliminado con éxito!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
