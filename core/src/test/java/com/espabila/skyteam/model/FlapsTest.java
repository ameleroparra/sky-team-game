package com.espabila.skyteam.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlapsTest {

    Flaps flaps;
    CoPilot coPilot;

    @BeforeEach
    void setUp() {
        flaps = new Flaps();
        coPilot = new CoPilot();
    }

    @Test
    void testActivateFirstFlapWhenCorrectValue() {
        int flapIndex = 0;
        int validValue = 1;

        boolean result = flaps.activateFlap(coPilot, flapIndex, validValue);

        assertTrue(result);
        assertTrue(flaps.isActivated(flapIndex));
        assertFalse(flaps.allActivated());
    }

    @Test
    void testDoNotActivateFirstFlapIfValueDoesNotMatchRequiredValues() {;
        int flapIndex = 0;
        int invalidValue = 3;

        boolean result = flaps.activateFlap(coPilot, flapIndex, invalidValue);

        assertFalse(result);
        assertFalse(flaps.isActivated(flapIndex));
        assertFalse(flaps.allActivated());
    }

    @Test
    void testDoNotActivateSecondFlapIfFirstFlapIsNotActivated() {
        int secondFlapIndex = 1;
        int validValue = 2;

        boolean result = flaps.activateFlap(coPilot, secondFlapIndex, validValue);

        assertFalse(result);
        assertFalse(flaps.isActivated(secondFlapIndex));
        assertFalse(flaps.allActivated());
    }

    @Test
    void testActivateAllFlapsSequentially() {
        assertTrue(flaps.activateFlap(coPilot, 0, 1));
        assertTrue(flaps.activateFlap(coPilot, 1, 2));
        assertTrue(flaps.activateFlap(coPilot, 2, 4));
        assertTrue(flaps.activateFlap(coPilot, 3, 6));

        assertTrue(flaps.allActivated());
    }

}
