package com.rohan.rides.utils;

public class Utils {

    // Method to calculate the estimated carbon emissions
    public static double calculateCarbonEmissions(double kilometrage) {
        if (kilometrage < 0) {
            throw new IllegalArgumentException("Kilometrage cannot be negative");
        }

        double carbonEmissions;
        if (kilometrage > 5000) {
            carbonEmissions = 5000 + (kilometrage - 5000) * 1.5;
        } else {
            carbonEmissions = kilometrage;
        }

        return carbonEmissions;
    }

    public static boolean isValueInRange(int value) {
        return value >= 1 && value <= 100;
    }
}
