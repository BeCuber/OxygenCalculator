package com.example.calculadorao2.model;

public class Kpa {

    public static final double CONVERSION_FACTOR_TO_BAR = 0.01;
    public static final double CONVERSION_FACTOR_TO_PSI = 0.145038;

    public static double toBar(double value){
        return value * CONVERSION_FACTOR_TO_BAR;
    }

    public static double toPsi(double value){
        return value * CONVERSION_FACTOR_TO_PSI;
    }



}
