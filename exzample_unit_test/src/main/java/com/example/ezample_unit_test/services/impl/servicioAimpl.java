package com.example.ezample_unit_test.services.impl;

import com.example.ezample_unit_test.services.IServicioA;

public class servicioAimpl implements IServicioA{

    @Override
    public int sumar(int a, int b) {
        return a + b + 1 ;
    }
    
}
