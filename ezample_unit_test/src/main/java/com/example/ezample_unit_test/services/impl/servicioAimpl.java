package com.example.ezample_unit_test.services.impl;

import com.example.ezample_unit_test.services.ServicioA;

public class servicioAimpl implements ServicioA{

    @Override
    public int sumar(int a, int b) {
        return a + b + 1 ;
    }
    
}
