package com.example.calculadorao2.model;

public class Psi {

    public static final double CONVERSION_FACTOR_TO_BAR = 0.0689476;
    public static final double CONVERSION_FACTOR_TO_KPA = 6.89476;

    public static double toBar(double value){
        return value * CONVERSION_FACTOR_TO_BAR;
    }

    public static double toKpa(double value){
        return value * CONVERSION_FACTOR_TO_KPA;
    }



}
