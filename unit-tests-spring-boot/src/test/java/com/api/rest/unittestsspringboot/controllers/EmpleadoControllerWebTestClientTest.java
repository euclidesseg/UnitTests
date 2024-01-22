package com.api.rest.unittestsspringboot.controllers;
import static org.springframework.boot.test.context.SpringBootTest.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.api.rest.unittestsspringboot.models.EmpleadoModel;

import static org.hamcrest.Matchers.*;

// para trabajar con peticiones no bloqueantes trabajamos o se recomienda trabajar con web testclient
// para aclarar que las peticiones no bloqueantes son todas las peticiones asincronas es decir que no esperan a que una 
// tarea se finalice para empezar otra
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // indica el orden de los metodos
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // indica que usaremos un puerto aleatorio

public class EmpleadoControllerWebTestClientTest {
    
    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Order(1)
    void testGuardarEmpleadoModel(){
        //given dado este empleado
        EmpleadoModel empleado = EmpleadoModel.builder()
                .id(1l)
                .nombre("Adrian")
                .apellido("Ramirez")
                .email("aab@gmail.com")
                .build();

        //when
        webTestClient.post().uri("http://localhost:8080/api/empleados") //erb testclient hace un post 
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(empleado)
                .exchange() //envia el request

        //then
                .expectStatus().isCreated() // el codigo de estado sea creado
                .expectHeader().contentType(MediaType.APPLICATION_JSON) //: Establece la expectativa de que el tipo de contenido (ContentType) del encabezado de la respuesta sea "application/json". En otras palabras, se espera que el servicio web devuelva la respuesta en formato JSON.
                .expectBody()
                .jsonPath("$.id").isEqualTo(empleado.getId())
                .jsonPath("$.nombre").isEqualTo(empleado.getNombre())
                .jsonPath("$.apellido").isEqualTo(empleado.getApellido())
                .jsonPath("$.email").isEqualTo(empleado.getEmail());
    }

     @Test
     @Order(2)
     void testObtenerEmpleadoPorId(){
         webTestClient.get().uri("http://localhost:8080/api/empleados/1").exchange()
                 .expectStatus().isOk() // que el estado sea encontrado 
                 .expectHeader().contentType(MediaType.APPLICATION_JSON) // la espectativa es que el formato sea en formato json
                 .expectBody() //puedes encadenar métodos y afirmaciones adicionales para verificar el contenido específico del cuerpo de la respuesta. En tu ejemplo, se están utilizando varias llamadas a .jsonPath() para verificar que ciertos campos del cuerpo de la respuesta tengan valores específicos.
                 .jsonPath("$.id").isEqualTo(1)
                 .jsonPath("$.nombre").isEqualTo("Adrian")
                 .jsonPath("$.apellido").isEqualTo("Ramirez")
                 .jsonPath("$.email").isEqualTo("aab@gmail.com");;
     }
      @Test
      @Order(3)
      void testListarEmpleadoModels(){
          webTestClient.get().uri("http://localhost:8080/api/empleados").exchange()
                  .expectStatus().isOk() // que el estado sea creado
                  .expectHeader().contentType(MediaType.APPLICATION_JSON)
                  .expectBody()
                  .jsonPath("$[0].nombre").isEqualTo("Adrian") // ya que tiene solo un registro indicamos que el primer registro el nombre debe ser adrian
                  .jsonPath("$[0].apellido").isEqualTo("Ramirez")
                  .jsonPath("$[0].email").isEqualTo("aab@gmail.com")
                  .jsonPath("$").isArray()
                  .jsonPath("$").value(hasSize(1));
      }
    @Test
    @Order(4)
    void testObtenerListadoDeEmpleados(){
        webTestClient.get().uri("http://localhost:8080/api/empleados").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON) // tipo de contenido
                .expectBodyList(EmpleadoModel.class) // que el tipo de lista se de empleadoModel 
                .consumeWith(response -> { // me debuelve un consumer una funcion de flecha con la respuesta y dentro de esta respuesta viene la lista 
                    List<EmpleadoModel> empleado = response.getResponseBody();
                    assertEquals(1,empleado.size());
                    assertNotNull(empleado);
                });
    }

    @Test
    @Order(5)
    void testActualizarEmpleadoModel(){
        EmpleadoModel EmpleadoActualizado = EmpleadoModel.builder()
                .nombre("Pepe")
                .apellido("Castillo")
                .email("ckk2@gmail.com")
                .build();

        webTestClient.put().uri("http://localhost:8080/api/empleados/1") // indica que al empleado con id 1 le dare los nuevos valores de empleado actualizado
                .contentType(MediaType.APPLICATION_JSON)// cpntenido que vamo a enviar
                .bodyValue(EmpleadoActualizado)
                .exchange() //emvia el request

        //then
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    @Order(6)
    void testEliminarEmpleadoModel(){
        webTestClient.get().uri("http://localhost:8080/api/empleados").exchange()
        .expectStatus().isOk() // Se espera que la solicitud sea exitosa
        .expectHeader().contentType(MediaType.APPLICATION_JSON) // Se espera que el formato sea JSON
        .expectBodyList(EmpleadoModel.class) // Se espera que el cuerpo de la respuesta sea una lista de EmpleadoModel
        .hasSize(1); // Se espera que la lista tenga un tamaño de 1


        webTestClient.delete().uri("http://localhost:8080/api/empleados/1")
        .exchange()
        .expectStatus().isOk(); // Se espera que la eliminación sea exitosa (código de estado 200 OK)

        webTestClient.get().uri("http://localhost:8080/api/empleados").exchange()
        .expectStatus().isOk() // Se espera que la solicitud sea exitosa
        .expectHeader().contentType(MediaType.APPLICATION_JSON) // Se espera que el formato sea JSON
        .expectBodyList(EmpleadoModel.class) // Se espera que el cuerpo de la respuesta sea una lista de EmpleadoModel
        .hasSize(0); // Se espera que la lista ahora tenga un tamaño de 0 después de la eliminación


        webTestClient.get().uri("http://localhost:8080/api/empleados/1").exchange()
        .expectStatus().is4xxClientError(); // Se espera que al intentar obtener un empleado eliminado se reciba un error del cliente (código de estado 4xx)

    }
}