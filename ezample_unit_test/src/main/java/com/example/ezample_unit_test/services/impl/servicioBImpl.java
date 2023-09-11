package com.example.ezample_unit_test.services.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.ezample_unit_test.services.ServicioA;
import com.example.ezample_unit_test.services.ServicioB;

public class servicioBImpl implements ServicioB {

    @Autowired ServicioA _servicioA; 

    // este metodo deoebde  de servicioA porque esta usando metodos de este servicio
    @Override
    public ServicioA getServicioA() { 
        return this._servicioA;
    }

    // este metodo deoebde  de servicioA porque esta usando metodos de este servicio
    @Override
    public void setServicioA(ServicioA servicioA) {
        this._servicioA = servicioA;
    }
    // este metodo deoebde  de servicioA porque esta usando metodos de este servicio
    @Override
    public int multiplicarSumar(int a, int b, int multiplicador) {
        return this._servicioA.sumar(a, b) * multiplicador;
    }

    @Override
    public int multiplicar(int a, int b) {
        return a * b;
    }
    
}
