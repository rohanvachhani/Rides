package com.rohan.rides;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.rohan.rides.utils.Utils;

import org.junit.Test;

public class UtilsTest {
    @Test
    public void testCalculateCarbonEmissionsWithPositiveKilometrage() {
        double kilometrage = 10000.0;
        double expectedCarbonEmissions = 12500.0;
        double carbonEmissions = Utils.calculateCarbonEmissions(kilometrage);
        assertEquals(expectedCarbonEmissions, carbonEmissions, 0.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateCarbonEmissionsWithNegativeKilometrage() {
        double kilometrage = -10000.0;
        Utils.calculateCarbonEmissions(kilometrage);
    }

    @Test
    public void testCalculateCarbonEmissionsWithZeroKilometrage() {
        double kilometrage = 0.0;
        double expectedCarbonEmissions = 0.0;
        double carbonEmissions = Utils.calculateCarbonEmissions(kilometrage);
        assertEquals(expectedCarbonEmissions, carbonEmissions, 0.0);
    }

    @Test
    public void testCalculateCarbonEmissionsWithKilometrageLessThan5000() {
        double kilometrage = 3000.0;
        double expectedCarbonEmissions = 3000.0;
        double carbonEmissions = Utils.calculateCarbonEmissions(kilometrage);
        assertEquals(expectedCarbonEmissions, carbonEmissions, 0.0);
    }

    @Test
    public void testCalculateCarbonEmissionsWithKilometrageEqualTo5000() {
        double kilometrage = 5000.0;
        double expectedCarbonEmissions = 5000.0;
        double carbonEmissions = Utils.calculateCarbonEmissions(kilometrage);
        assertEquals(expectedCarbonEmissions, carbonEmissions, 0.0);
    }

    @Test
    public void testValueInRange() {
        assertTrue(Utils.isValueInRange(1));
        assertTrue(Utils.isValueInRange(50));
        assertTrue(Utils.isValueInRange(100));
    }

    @Test
    public void testValueBelowRange() {
        assertFalse(Utils.isValueInRange(0));
    }
    @Test
    public void testValueAboveRange() {
        assertFalse(Utils.isValueInRange(101));
        assertFalse(Utils.isValueInRange(1000));
        assertFalse(Utils.isValueInRange(Integer.MAX_VALUE));
    }
}