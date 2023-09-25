package com.example.ezample_unit_test.services.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.ezample_unit_test.services.IServicioA;
import com.example.ezample_unit_test.services.IServicioB;

public class servicioBImpl implements IServicioB {

    @Autowired IServicioA _servicioA; 

    // este metodo depende  de servicioA porque esta usando metodos de este servicio
    @Override
    public IServicioA getServicioA() { 
        return this._servicioA;
    }

    // este metodo depende  de servicioA porque esta usando metodos de este servicio
    @Override
    public void setServicioA(IServicioA servicioA) {
        this._servicioA = servicioA;
    }
    // este metodo depende  de servicioA porque esta usando metodos de este servicio
    @Override
    public int multiplicarSumar(int a, int b, int multiplicador) {
        return this._servicioA.sumar(a, b) * multiplicador;
    }

    @Override
    public int multiplicar(int a, int b) {
        return a * b;
    }
    
}
