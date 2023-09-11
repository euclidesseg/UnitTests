package com.example.exzample_unit_test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import com.example.ezample_unit_test.services.ServicioA;
import com.example.ezample_unit_test.services.impl.servicioAimpl;


// esta seria la prueba unitaria para servicioA
@SpringJUnitWebConfig
public class TestServicioA {

    @Test
    public void testSumar(){
        int a = 2;
        int b = 2;
        // Utilizando polimorfismo para crear una instancia de ServicioA
        ServicioA servicioA = new servicioAimpl(); 

        assertEquals(servicioA.sumar(a, b), 5);
    }

    /* assertEquals 
     * Es un método de aserción (assertion) que se utiliza en pruebas unitarias 
     * para verificar si un valor esperado es igual al valor real.
     * 
     * En este caso, estás utilizando assertEquals para 
     * comprobar si el resultado de servicioA.sumar(a, b) es igual a 4. 
     * 
    */
}
