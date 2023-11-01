package com.api.rest.unittestsspringboot.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.ArgumentMatchers.any;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.api.rest.unittestsspringboot.models.EmpleadoModel;
import com.api.rest.unittestsspringboot.services.implementaciones.EmpleadoServiceImplementation;

@WebMvcTest
// @WebMvcTest especifica que se le va a hacer test a un controlador
// tambien autoconfigura mokmvc para poder realizar peticiones http a nuestro
// controlador
public class EmpleadoControllerTest {
  @Autowired
  private MockMvc mockMvc; // para probar peticiones http

  /*
   * para crear un objeto simulado del servicio ya que el controlador depende del
   * servicio
   * recordemos que en el test del servicio creamos el mock para el repositorio
   * por su dependencia
   */
  @MockBean
  private EmpleadoServiceImplementation empleadoServiceImplementation;

  /*
   * es una clase proporcionada por la biblioteca Jackson en Java que se utiliza
   * para convertir objetos Java en formato JSON y viceversa.
   */
  @Autowired
  private ObjectMapper objectMapper;

  @Test
    void testGuardarEmpleado() throws JsonProcessingException, Exception{
        //given 
            EmpleadoModel empleado = EmpleadoModel.builder()
            .nombre("Euclides")
            .apellido("perez")
            .email("euclides@gmail.com")
            .build();

             // dado este empleado giardado
           given(empleadoServiceImplementation.saveEmpleado(any(EmpleadoModel.class))) //given para aislar el servicio simulado del serviio original 
           .willAnswer((invocation) -> invocation.getArgument(0));

           //when
           ResultActions response = mockMvc.perform(post("/api/empleados") //el post me lo permite usar esta importacion MockMvcRequestBuilders.*;
              .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(empleado)));
              // then
    

              
              response.andDo(print())
                      .andExpect(status().isCreated())
                      .andExpect(jsonPath("$.nombre", is(empleado.getNombre())))
                      .andExpect(jsonPath("$.apellido", is(empleado.getApellido())))
                      .andExpect(jsonPath("$.email", is(empleado.getEmail())));
                      
            }

            // print // viene de la clase MockMvcResultHandlers
            //andExpect, andDo//  pertenecen a Resultactions
            //jsonPath// status, iscreated // viene de la clase MockMvcResultMatchers
}
