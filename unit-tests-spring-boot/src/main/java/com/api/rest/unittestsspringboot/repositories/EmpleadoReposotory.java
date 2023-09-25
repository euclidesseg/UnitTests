package com.api.rest.unittestsspringboot.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.rest.unittestsspringboot.models.EmpleadoModel;


public interface EmpleadoReposotory extends JpaRepository<EmpleadoModel, Long> {

    Optional <EmpleadoModel> findByEmail(String emailString);
}
