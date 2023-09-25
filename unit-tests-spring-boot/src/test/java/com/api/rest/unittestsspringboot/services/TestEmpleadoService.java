package com.api.rest.unittestsspringboot.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


import com.api.rest.unittestsspringboot.exception.ResourceNotFountException;
import com.api.rest.unittestsspringboot.models.EmpleadoModel;
import com.api.rest.unittestsspringboot.repositories.EmpleadoReposotory;
import com.api.rest.unittestsspringboot.services.implementaciones.EmpleadoServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

// esta anotacion nos indica que vamos a trabajar con mockito y para usar unas extenciones que usan JUnit 5
@ExtendWith(MockitoExtension.class)
public class TestEmpleadoService {

    @Mock /*Mock indica que vamos a crear un simulacro del repositorio EmpleadoRepository*/
    EmpleadoReposotory empleadoReposotory;

    @InjectMocks //injectamos  esto para injectar un simulacro
    private EmpleadoServiceImplementation empleadoServiceImplementation;

    private EmpleadoModel empleado;
    @BeforeEach
    private void setup() {
        empleado = EmpleadoModel.builder()
                .nombre("Euclides")
                .apellido("Perez")
                .email("Euclides2696@gmail.com")
                .build();
    }


    @DisplayName("Test para Guardar un Empleado")
    @Test
    public void TestGuardarEMpleado(){
        // Configuración de comportamiento simulado para el repositorio de empleados
        // given
        // Dado este repositorio y dado que no existe un empleado con el mismo correo electrónico
        given(empleadoReposotory.findByEmail(this.empleado.getEmail()))
                .willReturn(Optional.empty()); // va a retornar vacio
        // y si al buscarlo retorna vacio lo va a guardar
        given(empleadoReposotory.save(empleado)).willReturn(this.empleado);


        //wen
        // cuando yo llame al metodo saveEmpleado del servicio el cual llamara al repositorio simulado
        EmpleadoModel empleadoGuardado = empleadoServiceImplementation.saveEmpleado(empleado);



        //then
        // Entonces, espero que el empleado guardado retornado no sea nulo
        assertThat(empleadoGuardado).isNotNull();
    }





    @DisplayName("Test para Guardar un Empleado con throwException")
    @Test
    public void TestGuardarEMpleadoConThrowException(){
        //given  ==  para usarlo debemos importar una dependencia de mockito
        //dato este empleado consultado
        given(empleadoReposotory.findByEmail(this.empleado.getEmail()))
                .willReturn(Optional.of(empleado));

        //wen
        // cuando yo intente guardar este mepleado que ya existe
        assertThrows(ResourceNotFountException.class,() ->{
            this.empleadoServiceImplementation.saveEmpleado(empleado);
        });
        //then
        // entonces espero que nunca se llame al repositorio
        verify(empleadoReposotory, never()).save(any(EmpleadoModel.class));
    }
    /*assertThrows(ResourceNotFoundException.class, () -> {...}):
    Esta línea verifica que una excepción del tipo ResourceNotFoundException
    sea lanzada cuando intentas guardar un empleado que ya existe en la base de datos. */

    /* verify(empleadoReposotory, never()).save(any(EmpleadoModel.class)):
     * Esta línea utiliza verify de Mockito para verificar que el método save del repositorio
     *  empleadoRepository nunca se llama (never()) durante la ejecución del método saveEmpleado
     *  del servicio. Esto es importante porque, en este caso, esperas que no se i
     * ntente guardar el empleado nuevamente si ya existe en la base de datos.
     */
    /*
    * NOTA:
    * Las anotaciones given de mockitose estan usando unicamente para simular una dependencia es decir
    * al repositorio esto devido a que el servicio depende del repositorio y por ende se aisla al repositorio
    * original del componente de servicio durante la prueba  */
}
