package com.example.exzample_unit_test;

import org.junit.Test;
import org.mockito.Mockito;
import org.junit.Assert;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.ezample_unit_test.services.IServicioA;
import com.example.ezample_unit_test.services.IServicioB;
import com.example.ezample_unit_test.services.impl.servicioAimpl;
import com.example.ezample_unit_test.services.impl.servicioBImpl;

//prueba unitaria para servicio B
@SpringBootTest
public class TestServicioB {

    @Test
    public void TestMultiplicar() {
        IServicioB servicioB = new servicioBImpl(); // uzando el polimorfismo
        Assert.assertEquals(servicioB.multiplicar(2, 3), 6);
    }

    @Test
    public void multiplicarSumar() {
        IServicioB servicioB = new servicioBImpl();
        IServicioA servicioA = new servicioAimpl();

        servicioB.setServicioA(servicioA);
        
        Assert.assertEquals(servicioB.multiplicarSumar(2, 3, 5), 25);
        /*
         * Pasa que la clase servicioBimpl depnde de la clase servicioAimpl
         * al estar usando uno de sus mtodos para sumar los dos primeros numeros
         * entonces al modificar este metodo
         * public int sumar(int a, int b) {
         * return a + b + 1 ;
         * }
         * nos dara herror por la dependencia de esta.
         * es aqui cuando usams moquito
         */
    }

    // moquito nos permite simular objetos y clases en java
    @Test
    public void multiplicarSumarWithMockito() {
        IServicioA servicioA = Mockito.mock(IServicioA.class); // indicamos que vamos a simular este servicioA

        // Configuramos un mock para el método "sumar" de "servicioA" para que devuelva
        // 5 cuando se le pase 2 y 3 como argumentos sin importar como ests el servicio original.
        Mockito.when(servicioA.sumar(2, 3)).thenReturn(5);

        // Creamos una instancia de "ServicioB"
        IServicioB servicioB = new servicioBImpl();

        // Configuramos "servicioB" con el mock de "servicioA" que creamos anteriormente.
        servicioB.setServicioA(servicioA);

        // Comprobamos que el resultado de llamar a "multiplicarSumar" en "servicioB"
        // con los argumentos 2, 3 y 5 es igual a 25.
        Assert.assertEquals(servicioB.multiplicarSumar(2, 3, 5), 25);

    }
}
