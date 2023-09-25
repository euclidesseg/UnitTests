package com.api.rest.unittestsspringboot.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.api.rest.unittestsspringboot.models.EmpleadoModel;

@DataJpaTest 
// nos sirve para probar componentes solo para la capa de persistencias
// unicamente a la capa de repositorios y buscara la clase de entidad de esta entidad
public class EmpleadoRepositoryTest {
    @Autowired
    EmpleadoReposotory empleadoReposotory;

    @BeforeEach // antes de cada metodo se va a ejecutar ese metodo
    void setUp(){
        System.out.println("Hola mundo");
    }

    private EmpleadoModel empleado;


    @BeforeEach
    private void setup() {
        empleado = EmpleadoModel.builder()
                .nombre("Euclides")
                .apellido("Perez")
                .email("Euclides2696@gmail.com")
                .build();
    }

    @DisplayName("Test para guardar empleado")
    @Test
    void testGuardarEmpleado(){
        //given  condicion previa o configuracion
         //dado este empleado 
        EmpleadoModel empleado1 = EmpleadoModel.builder() 
            //uzando del patron de diseños builder de lombuck para construir el objeto de manera facil
            .nombre("Euclides")
            .apellido("Perez")
            .email("euclides@gmail.com")
            .build();
       
        //When - accion o comportamiento que vamos a usar

        //cuando yo llame al metodo save del repositorio y guarde ese empleado
        EmpleadoModel empleadoGuardado = this.empleadoReposotory.save(empleado1);

        // then salida de la accion
        // se espera lo siguiente
        assertThat(empleadoGuardado).isNotNull(); // que no sea nulo 
        assertThat(empleadoGuardado.getId()).isGreaterThan(0); // que el id sea mayor a cero

    }

    @DisplayName("Test para listar empleados")
    @Test
    void testListarEmpleados(){
        // givin
         EmpleadoModel empleado1 = EmpleadoModel.builder() 
            //uzando del patron de diseños builder de lombuck para construir el objeto de manera facil
            .nombre("Lina")
            .apellido("Fernandez")
            .email("lina@gmail.com")
            .build();
            this.empleadoReposotory.save(this.empleado);
            this.empleadoReposotory.save(empleado1);
            //dados estos empleados guardados 
        // when
        List<EmpleadoModel> listaEmpleados = this.empleadoReposotory.findAll();
        // cuando liste los empleados 
        // then
        assertThat(listaEmpleados).isNotNull();
        assertThat(listaEmpleados.size()).isEqualTo(2);
        // espero obtener

    }

    @DisplayName("Test para obtener un empleado por id")
    @Test
    void testOptenerEmpleadosPorId(){
        // givin
        this.empleadoReposotory.save(this.empleado);
        // when
        Optional<EmpleadoModel> empleadoGetEd = this.empleadoReposotory.findById(this.empleado.getId());
        //then
        assertThat(empleadoGetEd).isNotNull();
    }

    @DisplayName("Test para actualizar un empleado")
    @Test
    void testActualizarEmpleado(){
        // givin
        //dado este empleado guardado
        this.empleadoReposotory.save(this.empleado);
        // when
        // cuando actualice este empleado guardado
        EmpleadoModel empleadoGuardado = this.empleadoReposotory.findById(this.empleado.getId()).get();
        empleadoGuardado.setEmail("c1@gmail.com");
        empleadoGuardado.setNombre("Euclides Perez");
        empleadoGuardado.setApellido("Tesla");
        EmpleadoModel empleadoActualizado = this.empleadoReposotory.save(empleadoGuardado);
        // then
        // espero optener los siguiente del empleado
        assertThat(empleadoActualizado.getEmail()).isNotNull().isEqualTo("c1@gmail.com");
        assertThat(empleadoActualizado.getNombre()).isNotNull().isEqualTo("Euclides Perez");
    }

    @DisplayName("Test para eliminar un empleado")
    @Test
    void testEliminarEmpleado(){
        // givin
        //dado este empleado guardado
        this.empleadoReposotory.save(this.empleado);
        // when
        // cuando yo elimine empleado
        this.empleadoReposotory.deleteById(this.empleado.getId());
        Optional<EmpleadoModel> empleadoOptional = this.empleadoReposotory.findById(this.empleado.getId());
        // then
        // espero optener un empleado basio
        assertThat(empleadoOptional).isEmpty();
    }
}
