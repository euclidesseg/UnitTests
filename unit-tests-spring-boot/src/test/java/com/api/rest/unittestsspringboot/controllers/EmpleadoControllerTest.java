package com.api.rest.unittestsspringboot.controllers;

import com.api.rest.unittestsspringboot.services.implementaciones.EmpleadoServiceImplementation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
// @WebMvcTest especifica que se le va a hacer test a un controlador
// tambien autoconfigura mokmvc para poder realizar peticiones http a nuestro controlador
public class EmpleadoControllerTest {
    @Autowired
    private MockMvc mockMvc; // para probar peticiones http

    /* para crear un objeto simulado del servicio ya que el controlador depende del servicio
    *  recordemos que enel test del servicio creamos el mock para el repositorio por su dependencia*/
    @MockBean
    private EmpleadoServiceImplementation empleadoServiceImplementation;

    /*  es una clase proporcionada por la biblioteca Jackson en Java que se utiliza
     *  para convertir objetos Java en formato JSON y viceversa.
     *  */
    @Autowired
    ObjectMapper objectMapper;



}
