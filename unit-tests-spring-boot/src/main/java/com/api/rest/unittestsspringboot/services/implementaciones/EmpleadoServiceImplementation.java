package com.api.rest.unittestsspringboot.services.implementaciones;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.rest.unittestsspringboot.exception.ResourceNotFountException;
import com.api.rest.unittestsspringboot.models.EmpleadoModel;
import com.api.rest.unittestsspringboot.repositories.EmpleadoReposotory;
import com.api.rest.unittestsspringboot.services.Interfaces.EmpleadoServiceInterface;

@Service
public class EmpleadoServiceImplementation implements EmpleadoServiceInterface{

    @Autowired
    EmpleadoReposotory empleadoReposotory;

    @Override
    public EmpleadoModel saveEmpleado(EmpleadoModel empleado) {
        Optional<EmpleadoModel> empleadoSaved = this.empleadoReposotory.findByEmail(empleado.getEmail());
        if(empleadoSaved.isPresent()){
            throw new ResourceNotFountException("El empleado con este email ya existe: " + empleado.getEmail());
        }
        return empleadoReposotory.save(empleado);
    }

    @Override
    public List<EmpleadoModel> getAllEmpleado() {
        return this.empleadoReposotory.findAll();
    }

    @Override
    public Optional<EmpleadoModel> getEmpleadoById(Long id) {
        return this.empleadoReposotory.findById(id); 
    }

    @Override
    public EmpleadoModel updateEmpleado(EmpleadoModel empleado) {
        return this.empleadoReposotory.save(empleado);        
    }

    @Override
    public boolean deleteEmpleado(Long id){
        try{
            this.empleadoReposotory.delete(null);
            return true;
        }catch(RuntimeException err){
            return false;
        }
    }
    
}
