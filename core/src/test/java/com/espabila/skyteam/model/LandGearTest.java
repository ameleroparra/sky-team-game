package com.espabila.skyteam.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LandGearTest {

    LandGear landGear;
    Pilot pilot;

    @BeforeEach
    void setUp() {
        landGear = new LandGear();
        pilot = new Pilot();
    }

    @Test
    void testActivateEngineWhenCorrectValueProvided() {
        int gearIndex = 0;
        int correctValue = 1;

        boolean result = landGear.activateLandGear(pilot, gearIndex, correctValue);

        assertTrue(result);
        assertTrue(landGear.isActivated(gearIndex));
        assertFalse(landGear.allActivated());
    }

    @Test
    void testDoNotActivateSecondLandGearIfFirstLandGearIsNotActivated() {
        int secondFlapIndex = 1;
        int validValue = 2;

        boolean result = landGear.activateLandGear(pilot, secondFlapIndex, validValue);

        assertFalse(result);
        assertFalse(landGear.allActivated());
    }

    @Test
    void testDoNotActivateEngineWhenIncorrectValueProvided() {
        int gearIndex = 0;
        int incorrectValue = 3;

        boolean result = landGear.activateLandGear(pilot, gearIndex, incorrectValue);

        assertFalse(result);
        assertFalse(landGear.isActivated(gearIndex));
        assertFalse(landGear.allActivated());
    }

    @Test
    void testIndividualGearActivationStatus() {
        assertFalse(landGear.isActivated(0));
        assertFalse(landGear.isActivated(1));
        assertFalse(landGear.isActivated(2));

        landGear.activateLandGear(pilot, 0, 1);
        assertTrue(landGear.isActivated(0));
        assertFalse(landGear.isActivated(1));
        assertFalse(landGear.isActivated(2));

        landGear.activateLandGear(pilot, 1, 3);
        assertTrue(landGear.isActivated(0));
        assertTrue(landGear.isActivated(1));
        assertFalse(landGear.isActivated(2));

        landGear.activateLandGear(pilot, 2, 5);
        assertTrue(landGear.isActivated(0));
        assertTrue(landGear.isActivated(1));
        assertTrue(landGear.isActivated(2));

        assertTrue(landGear.allActivated());
    }
}
