package com.project.laundrybiz.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/types")
public class TypesController {

    @Autowired
    private TypesService typesService;

    // Create or update a type
    @PostMapping
    public ResponseEntity<Types> addOrUpdateType(@RequestBody Types type) {
        return ResponseEntity.ok(typesService.saveType(type));
    }

    // Get all types
    @GetMapping
    public ResponseEntity<List<Types>> getAllTypes() {
        return ResponseEntity.ok(typesService.getAllTypes());
    }

    // Get a single type by ID
    @GetMapping("/{id}")
    public ResponseEntity<Types> getTypeById(@PathVariable Long id) {
        Optional<Types> type = typesService.getTypeById(id);
        return type.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a type by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteType(@PathVariable Long id) {
        typesService.deleteType(id);
        return ResponseEntity.noContent().build();
    }

    // Search types by name
    @GetMapping("/search")
    public ResponseEntity<List<Types>> searchTypesByName(@RequestParam String name) {
        return ResponseEntity.ok(typesService.findByName(name));
    }
}
