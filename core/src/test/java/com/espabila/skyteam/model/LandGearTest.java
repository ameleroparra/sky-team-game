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
    void testActivateFirstLandGearWhenCorrectValue() {
        int gearIndex = 0;
        int correctValue = 1;

        boolean result = landGear.activateLandGear(pilot, gearIndex, correctValue);

        assertTrue(result);
        assertTrue(landGear.isActivated(gearIndex));
        assertFalse(landGear.allActivated());
    }


    @Test
    void testDoNotActivateFirstLandGearWhenIncorrectValue() {
        int gearIndex = 0;
        int incorrectValue = 3;

        boolean result = landGear.activateLandGear(pilot, gearIndex, incorrectValue);

        assertFalse(result);
        assertFalse(landGear.isActivated(gearIndex));
        assertFalse(landGear.allActivated());
    }

    @Test
    void testActivateSecondLandGearWhenCorrectValue() {
        int gearIndex = 1;
        int correctValue = 3;

        boolean result = landGear.activateLandGear(pilot, gearIndex, correctValue);

        assertTrue(result);
        assertTrue(landGear.isActivated(gearIndex));
        assertFalse(landGear.allActivated());
    }


    @Test
    void testDoNotActivateSecondLandGearWhenIncorrectValue() {
        int gearIndex = 1;
        int incorrectValue = 1;

        boolean result = landGear.activateLandGear(pilot, gearIndex, incorrectValue);

        assertFalse(result);
        assertFalse(landGear.isActivated(gearIndex));
        assertFalse(landGear.allActivated());
    }

    @Test
    void testActivateThirdLandGearWhenCorrectValue() {
        int gearIndex = 2;
        int correctValue = 5;

        boolean result = landGear.activateLandGear(pilot, gearIndex, correctValue);

        assertTrue(result);
        assertTrue(landGear.isActivated(gearIndex));
        assertFalse(landGear.allActivated());
    }


    @Test
    void testDoNotActivateThirdLandGearWhenIncorrectValue() {
        int gearIndex = 2;
        int incorrectValue = 1;

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

    @Test
    void testResetLandGearWhenAllGearsActivated() {
        landGear.activateLandGear(pilot, 0, 1);
        landGear.activateLandGear(pilot, 1, 3);
        landGear.activateLandGear(pilot, 2, 5);

        assertTrue(landGear.allActivated());

        landGear.resetLandGear();

        for (int i = 0; i < landGear.getActivated().length; i++) {
            assertFalse(landGear.isActivated(i));
        }
    }
}
