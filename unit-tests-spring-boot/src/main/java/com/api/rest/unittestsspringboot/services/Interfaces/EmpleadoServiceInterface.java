package com.api.rest.unittestsspringboot.services.Interfaces;

import java.util.List;
import java.util.Optional;

import com.api.rest.unittestsspringboot.models.EmpleadoModel;

// La clase que implemente esta interfaz debera agregar una implementacion para cada metodo
public interface EmpleadoServiceInterface {
    EmpleadoModel saveEmpleado (EmpleadoModel empleado);
    List<EmpleadoModel> getAllEmpleado();
    Optional<EmpleadoModel> getEmpleadoById(Long id);
    EmpleadoModel updateEmpleado(EmpleadoModel empleado);
    boolean deleteEmpleado(Long id);
}
