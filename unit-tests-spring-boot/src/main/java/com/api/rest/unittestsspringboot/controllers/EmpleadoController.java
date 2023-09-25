package com.api.rest.unittestsspringboot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.rest.unittestsspringboot.models.EmpleadoModel;
import com.api.rest.unittestsspringboot.services.implementaciones.EmpleadoServiceImplementation;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {
    @Autowired
    EmpleadoServiceImplementation empleadoServiceImplementation;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmpleadoModel saveEmpleado(@RequestBody EmpleadoModel empleado){
        return this.empleadoServiceImplementation.saveEmpleado(empleado);
    }

    @GetMapping
    public List<EmpleadoModel> getAll(){
        return this.empleadoServiceImplementation.getAllEmpleado();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoModel> getById(@PathVariable("id") Long empleadoId){
        return this.empleadoServiceImplementation.getEmpleadoById(empleadoId)
            .map(ResponseEntity::ok) // este mep me procesa el resultado.
            .orElse(ResponseEntity.notFound().build());
    }   

    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoModel> updateEmpleado(@PathVariable("id") Long empleadoId,  @RequestBody EmpleadoModel empleado){
        return this.empleadoServiceImplementation.getEmpleadoById(empleadoId)
            .map(empleadoGuardado ->{
                // actualizo los parametros del empleado obtenido de getEmpleadoById por los que llegan enel cuarpo de mi peticion
                empleadoGuardado.setNombre(empleado.getNombre());
                empleadoGuardado.setApellido(empleado.getApellido());
                empleadoGuardado.setEmail(empleado.getEmail());
                
                EmpleadoModel empleadoActualizado = this.empleadoServiceImplementation.updateEmpleado(empleadoGuardado);
                return new ResponseEntity<>(empleadoActualizado, HttpStatus.OK);
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmpleado(@PathVariable("id") Long empladoId){
         boolean response = this.empleadoServiceImplementation.deleteEmpleado(empladoId);
         if(response){
            return new ResponseEntity<String>("Empleado eliminado con exito", HttpStatus.OK);
         }else{
            return new ResponseEntity<String>("Error eliminando el empleado verifique que si existe.", HttpStatus.NOT_FOUND);
         }

    }
}
