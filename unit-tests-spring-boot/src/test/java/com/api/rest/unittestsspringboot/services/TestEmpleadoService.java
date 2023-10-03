package com.api.rest.unittestsspringboot.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

// esta anotacion nos indica que vamos a trabajar con mockito y para usar unas extenciones que usan JUnit 5
@ExtendWith(MockitoExtension.class)
public class TestEmpleadoService {

    // Mock indica que vamos a crear un objeto simulado del repositorio EmpleadoRepository
    @Mock
    EmpleadoReposotory empleadoReposotory;

    @InjectMocks // indicamos que el mock creado en la linea anterior sera autilizado en esta clase
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
    public void TestGuardarEMpleado() {
        // Configuración de comportamiento simulado para el repositorio de empleados
        // given
        // Dado este repositorio y dado que no existe un empleado con el mismo correo
        // electrónico
        given(empleadoReposotory.findByEmail(this.empleado.getEmail()))
                .willReturn(Optional.empty()); // va a retornar vacio
        // y si al buscarlo retorna vacio lo va a guardar
        given(empleadoReposotory.save(empleado)).willReturn(this.empleado);

        // wen
        // cuando yo llame al metodo saveEmpleado del servicio el cual llamara al
        // repositorio simulado
        EmpleadoModel empleadoGuardado = empleadoServiceImplementation.saveEmpleado(empleado);

        // then
        // Entonces, espero que el empleado guardado retornado no sea nulo
        assertThat(empleadoGuardado).isNotNull();
    }

    @DisplayName("Test para Guardar un Empleado con throwException")
    @Test
    public void TestGuardarEMpleadoConThrowException() {
        // given == para usarlo debemos importar una dependencia de mockito
        // dato este empleado consultado
        given(empleadoReposotory.findByEmail(this.empleado.getEmail()))
                .willReturn(Optional.of(empleado)); // retornara un empleado

        // wen
        // cuando yo intente guardar este mepleado que ya existe
        assertThrows(ResourceNotFountException.class, () -> {
            this.empleadoServiceImplementation.saveEmpleado(empleado);
        });
        // then
        // entonces espero que nunca se llame al repositorio
        verify(empleadoReposotory, never()).save(any(EmpleadoModel.class));
    }
    /*
     * assertThrows(ResourceNotFoundException.class, () -> {...}):
     * Esta línea verifica que una excepción del tipo ResourceNotFoundException
     * sea lanzada cuando intentas guardar un empleado que ya existe en la base de
     * datos.
     */

    /*
     * verify(empleadoReposotory, never()).save(any(EmpleadoModel.class)):
     * Esta línea utiliza verify de Mockito para verificar que el método save del
     * repositorio
     * empleadoRepository nunca se llama (never()) durante la ejecución del método
     * saveEmpleado
     * del servicio. Esto es importante porque, en este caso, esperas que no se i
     * ntente guardar el empleado nuevamente si ya existe en la base de datos.
     */
    /*
     * NOTA:
     * Las anotaciones given de mockitose estan usando unicamente para simular una
     * dependencia es decir
     * al repositorio esto devido a que el servicio depende del repositorio y por
     * ende se aisla al repositorio
     * original del componente de servicio durante la prueba
     */

    @DisplayName("Test para listar a los empleados")
    @Test
    public void testListarEmpleados() {
        // given
        // dado este empleado que yo construyo y que luego guardo con la simulacion de
        // un repositorio
        EmpleadoModel empleado1 = EmpleadoModel.builder()
                .id(2l)
                .nombre("Mariana")
                .email("mariana@gmail.com")
                .build();
        given(this.empleadoReposotory.findAll()).willReturn(List.of(empleado, empleado1)); // simulacion que retornara
                                                                                           // dos empleados
        // when
        // cuando yo consulte estps dos empleados
        List<EmpleadoModel> empleados = this.empleadoServiceImplementation.getAllEmpleado();
        // then
        // espero que la lista no sea nula y sea igual a dos
        assertThat(empleados).isNotNull();
        assertThat(empleados.size()).isEqualTo(2);
    }

    @DisplayName("Test Para retornar una lista vacia")
    @Test
    void testListarColeccionEmpleadosVacia() {
        EmpleadoModel empleado1 = EmpleadoModel.builder()
                .id(2l)
                .nombre("Mariana")
                .email("mariana@gmail.com")
                .build();
        given(empleadoReposotory.findAll()).willReturn(Collections.emptyList()); // emptyList condiciona que debe retornar vacia
        //when
        List<EmpleadoModel> listaEmpleados = this.empleadoServiceImplementation.getAllEmpleado();

        //then 
        assertThat(listaEmpleados).isEmpty();
        assertThat(listaEmpleados.size()).isEqualTo(0);
    }

    @DisplayName("Obtener un empleado por id")
    @Test
    public void testObtenerEmpleadoPorId(){
        //given
            EmpleadoModel empleado1 = EmpleadoModel.builder()
                .id(2l)
                .nombre("Mariana")
                .email("mariana@gmail.com")
                .build();
                given(this.empleadoReposotory.findById(empleado1.getId())).willReturn(Optional.of(empleado1));

        //when
         EmpleadoModel empleadoConsultado = this.empleadoServiceImplementation.getEmpleadoById(2l).get();
        //then
        assertThat(empleadoConsultado).isNotNull();
    }

    @DisplayName("Test para Actualizar un empleado")
    @Test
    public void testActualizarEmpleado(){
        //given dado este empleado guardado
        given(this.empleadoReposotory.save(this.empleado)).willReturn(empleado);
        this.empleado.setEmail("orlando2@gmail.com"); // luego de retornado cambio sus valores
        this.empleado.setNombre("orlando"); // luego de retornado cambio sus valores
        this.empleado.setApellido("Perez");// luego de retornado cambio sus valores

        //when cuando yo llame al metodo update() del servisio
        EmpleadoModel empleadoActualizado = this.empleadoServiceImplementation.updateEmpleado(empleado);

        //then
        assertThat(empleadoActualizado.getEmail()).isEqualTo("orlando2@gmail.com");
        assertThat(empleadoActualizado.getNombre()).isEqualTo("orlando");

        // para no confundirnos en este metodo con la simulasion del repositorio tengamos en cuenta que el repositorio
        // de jpa usa el mismo metodo save para actualizar un empleado
    }

    @DisplayName("Test para eliminar un empleado")
    @Test
    public void testEliminarEmpleado(){

        //given dado este empleado id
        long empleadoId = 1l;
        willDoNothing().given(this.empleadoReposotory).deleteById(this.empleado.getId());// queires decir que no regresare nada
        //when cuando yo llame al metodo eliminar del servicio
        this.empleadoServiceImplementation.deleteEmpleado(this.empleado.getId());
        //then
        // espero que se ejecute al menos una vez el metodo delete deleteById del repositorio
        verify(this.empleadoReposotory, times(1)).deleteById(this.empleado.getId());
    }
}
