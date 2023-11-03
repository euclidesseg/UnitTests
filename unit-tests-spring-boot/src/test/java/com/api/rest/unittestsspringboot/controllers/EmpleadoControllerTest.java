package com.api.rest.unittestsspringboot.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

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
  void testGuardarEmpleado() throws JsonProcessingException, Exception {
    // given
    EmpleadoModel empleado = EmpleadoModel.builder()
        .nombre("Euclides")
        .apellido("perez")
        .email("euclides@gmail.com")
        .build();

    // dado este empleado giardado
    given(empleadoServiceImplementation.saveEmpleado(any(EmpleadoModel.class))) // given para aislar el servicio
                                                                                // simulado del serviio original
        .willAnswer((invocation) -> invocation.getArgument(0));
    /*
     * El anterior método dice que cuando se llame al método saveEmpleado del
     * servicio con cualquier instancia de EmpleadoModel (EmpleadoModel.class),
     * debe devolver la misma instancia de EmpleadoModel. Esto simula el proceso de
     * guardar un empleado en la base de datos.
     */
    // when
    ResultActions response = mockMvc.perform(post("/api/empleados") // el post me lo permite usar esta importacion
                                                                    // MockMvcRequestBuilders.*;
        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(empleado)));// convierte al objeto en json

    // then
    response.andDo(print())// imprime la respuesta HTTP en la consola para fines de depuración.
        .andExpect(status().isCreated())// verifica que la respuesta HTTP tenga un código de estado 201, que
                                        // generalmente indica que la creación se realizó con éxito.
        .andExpect(jsonPath("$.nombre", is(empleado.getNombre())))
        .andExpect(jsonPath("$.apellido", is(empleado.getApellido())))
        .andExpect(jsonPath("$.email", is(empleado.getEmail())));

    /*
     * .andExpect
     * verifica que los atributos del objeto JSON devuelto en la respuesta coincidan
     * con los atributos del objeto empleado que se creó inicialmente.
     * En este caso, se verifica que los atributos nombre, apellido y email del JSON
     * de respuesta sean iguales a los del objeto empleado.
     */

  }
  // print // viene de la clase MockMvcResultHandlers
  // andExpect, andDo// pertenecen a Resultactions
  // jsonPath// status, iscreated // viene de la clase MockMvcResultMatchers


  @Test
  void testListarEmpleados()throws Exception{

    List<EmpleadoModel> listaEmpleados = new ArrayList<>();
    listaEmpleados.add(EmpleadoModel.builder().nombre("Euclides").apellido("perez").email("eperez@gmail.com").build());
    listaEmpleados.add(EmpleadoModel.builder().nombre("gumer").apellido("perez").email("gumer@gmail.com").build());
    listaEmpleados.add(EmpleadoModel.builder().nombre("elcy").apellido("perez").email("elcy@gmail.com").build());
    listaEmpleados.add(EmpleadoModel.builder().nombre("elber").apellido("perez").email("elber@gmail.com").build());
    listaEmpleados.add(EmpleadoModel.builder().nombre("darlis").apellido("fernandez").email("darlis@gmail.com").build());

    //data esta lista de empleado 
    given(empleadoServiceImplementation.getAllEmpleado()).willReturn(listaEmpleados);


    //when
    // cuando yo mande la peticion al metodo getAllEmpleados me debe retornar la lista de empleados creada
    ResultActions response = mockMvc.perform(get("/api/empleados"));
 
    //then
    //entonces verifico que el estado se ok(exitoso)
    response.andExpect(status().isOk())
    .andDo(print())//imprimo la respuesta
    .andExpect(jsonPath("$.size()", is(listaEmpleados.size())));  // verifico que la respuesta si sea del mismo tamaño de la lista
  }
}
