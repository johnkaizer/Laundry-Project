package com.project.laundrybiz.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypesService {

    @Autowired
    private TypesRepository typesRepository;

    // Create or update a type
    public Types saveType(Types type) {
        return typesRepository.save(type);
    }

    // Fetch all types
    public List<Types> getAllTypes() {
        return typesRepository.findAll();
    }

    // Fetch a single type by ID
    public Optional<Types> getTypeById(Long id) {
        return typesRepository.findById(id);
    }

    // Delete a type by ID
    public void deleteType(Long id) {
        typesRepository.deleteById(id);
    }

    // Search types by name
    public List<Types> findByName(String name) {
        return typesRepository.findByNameContainingIgnoreCase(name);
    }
}
