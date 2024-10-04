package com.project.laundrybiz.services;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypesRepository extends JpaRepository<Types, Long> {
    // Custom query to find types by name (case-insensitive search)
    List<Types> findByNameContainingIgnoreCase(String name);
}
