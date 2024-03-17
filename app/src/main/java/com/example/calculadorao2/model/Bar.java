package com.example.calculadorao2.model;

public class Bar {
    public static final double CONVERSION_FACTOR_TO_PSI = 14.5038;
    public static final double CONVERSION_FACTOR_TO_KPA = 100;

    public static double toPsi(double value){
        return value * CONVERSION_FACTOR_TO_PSI;
    }

    public static double toKpa(double value){
        return value * CONVERSION_FACTOR_TO_KPA;
    }



}
