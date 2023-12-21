package com.api.rest.unittestsspringboot.controllers;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.*;

import java.util.Arrays;
import java.util.List;

import com.api.rest.unittestsspringboot.models.EmpleadoModel;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // indica el orden de los metodos
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // indica que usaremos un puerto aleatorio

public class EmpleadoControllerTestRestTemplate {
    
    @Autowired
    private TestRestTemplate testRestTemplate;


    // para porder levantar este test primero se debe estar ejecutando el proyecto
    @Test
    @Order(1) // sera el primer metodo en ejecutarse
    void testGuardarEmpleado(){
            EmpleadoModel empleado = EmpleadoModel.builder()
                .id(1l)
                .nombre("Mariana")
                .apellido("Perez")
                .email("mariana@gmail.com")
                .build();

        ResponseEntity<EmpleadoModel> respuesta = testRestTemplate.postForEntity("http://localhost:8080/api/empleados", empleado, EmpleadoModel.class);
        // assertequals es una funcion de confirmacion que resibe dos parametros y compara entre ellos para retornar falso o verdadero
        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode()); // que el codigo de estado sea creado 
        assertEquals(APPLICATION_JSON, respuesta.getHeaders().getContentType());// assert equals resibe dos valores y comprara si son iguales en este caso el cotntenttype sea igual a json


        // lo espero que me retorne
        // then 
        EmpleadoModel empleadoCreado = respuesta.getBody();
        assertNotNull(empleadoCreado); // espero que no sea nulo el empleado creado
        assertEquals(1l, empleadoCreado.getId());
        assertEquals("Mariana", empleadoCreado.getNombre());
        assertEquals("Perez", empleadoCreado.getApellido());
        assertEquals("mariana@gmail.com", empleadoCreado.getEmail());
    }

    @Test
    @Order(2)
    void testListarEmpleados(){
        ResponseEntity<EmpleadoModel[]> respuesta = testRestTemplate.getForEntity("http://localhost:8080/api/empleados", EmpleadoModel[].class);
        // en la linea anterior estoy indicadndo que a la hora de hacer una peticion get a la ruta me va a devolver un arreglo de empleados 

        List<EmpleadoModel> empleados = Arrays.asList(respuesta.getBody());  // obtengo el cuerpo de la respuesta y lo agrego a una lista de empleados
    
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(APPLICATION_JSON, respuesta.getHeaders().getContentType());

        assertEquals(1, empleados.size()); // el tamaño de la lista de empleado sera = 1
        assertEquals(1l, empleados.get(0).getId()); // que el id del empleado creado sea igual a 1l

        assertEquals("Mariana", empleados.get(0).getNombre());
        assertEquals("Perez", empleados.get(0).getApellido());
        assertEquals("mariana@gmail.com", empleados.get(0).getEmail());
    }
}
