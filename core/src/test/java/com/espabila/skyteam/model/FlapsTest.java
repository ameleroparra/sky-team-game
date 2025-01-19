package com.espabila.skyteam.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlapsTest {
    Flaps flaps;
    CoPilot coPilot;

    @Mock
    private CoPilot mockCopilot;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        flaps = new Flaps();
        coPilot = new CoPilot();
    }


    @Test
    void testActivateFirstFlapWhenCorrectValueMock() {
        int flapIndex = 0;
        int validValue = 1;

        boolean result = flaps.activateFlap(mockCopilot, flapIndex, validValue);

        assertTrue(result);
        assertTrue(flaps.isActivated(flapIndex));
        assertFalse(flaps.allActivated());
    }

    // Integration test
    @Test
    void testActivateFirstFlapWhenCorrectValue() {
        int flapIndex = 0;
        int validValue = 1;

        boolean result = flaps.activateFlap(coPilot, flapIndex, validValue);

        assertTrue(result);
        assertTrue(flaps.isActivated(flapIndex));
    }

    @Test
    void testDoNotActivateFirstFlapWhenIncorrectValueMock() {
        int flapIndex = 0;
        int invalidValue = 4;

        boolean result = flaps.activateFlap(mockCopilot, flapIndex, invalidValue);

        assertFalse(result);
        assertFalse(flaps.isActivated(flapIndex));
    }

    // Integration test
    @Test
    void testDoNotActivateFirstFlapWhenIncorrectValue() {
        int flapIndex = 0;
        int invalidValue = 4;

        boolean result = flaps.activateFlap(coPilot, flapIndex, invalidValue);

        assertFalse(result);
        assertFalse(flaps.isActivated(flapIndex));
    }

    @Test
    void testDoNotActivateSecondFlapIfFirstFlapIsNotActivatedMock() {
        int secondFlapIndex = 1;
        int validValue = 2;

        boolean result = flaps.activateFlap(mockCopilot, secondFlapIndex, validValue);

        assertFalse(result);
        assertFalse(flaps.isActivated(secondFlapIndex));
    }

    // Integration test
    @Test
    void testDoNotActivateSecondFlapIfFirstFlapIsNotActivated() {
        int secondFlapIndex = 1;
        int validValue = 2;

        boolean result = flaps.activateFlap(coPilot, secondFlapIndex, validValue);

        assertFalse(result);
        assertFalse(flaps.isActivated(secondFlapIndex));
    }

    @Test
    void testActivateSecondFlapWhenCorrectValueMock() {
        int flapIndex = 1;
        int validValue = 2;

        flaps.activateFlap(mockCopilot, 0, 1);
        boolean result = flaps.activateFlap(mockCopilot, flapIndex, validValue);

        assertTrue(result);
        assertTrue(flaps.isActivated(flapIndex));
    }

    // Integration test
    @Test
    void testActivateSecondFlapWhenCorrectValue() {
        int flapIndex = 1;
        int validValue = 2;

        flaps.activateFlap(coPilot, 0, 1);
        boolean result = flaps.activateFlap(coPilot, flapIndex, validValue);

        assertTrue(result);
        assertTrue(flaps.isActivated(flapIndex));
    }

    @Test
    void testDoNotActivateSecondFlapWhenIncorrectValueMock() {
        int flapIndex = 1;
        int invalidValue = 6;

        flaps.activateFlap(mockCopilot, 0, 1);
        boolean result = flaps.activateFlap(mockCopilot, flapIndex, invalidValue);

        assertFalse(result);
        assertFalse(flaps.isActivated(flapIndex));
    }

    // Integration test
    @Test
    void testDoNotActivateSecondFlapWhenIncorrectValue() {
        int flapIndex = 1;
        int invalidValue = 6;

        flaps.activateFlap(coPilot, 0, 1);
        boolean result = flaps.activateFlap(coPilot, flapIndex, invalidValue);

        assertFalse(result);
        assertFalse(flaps.isActivated(flapIndex));
    }

    @Test
    void testActivateThirdFlapWhenCorrectValueMock() {
        int flapIndex = 2;
        int validValue = 4;

        flaps.activateFlap(mockCopilot, 0, 1);
        flaps.activateFlap(mockCopilot, 1, 2);
        boolean result = flaps.activateFlap(mockCopilot, flapIndex, validValue);

        assertTrue(result);
        assertTrue(flaps.isActivated(flapIndex));
    }

    // Integration test
    @Test
    void testActivateThirdFlapWhenCorrectValue() {
        int flapIndex = 2;
        int validValue = 4;

        flaps.activateFlap(coPilot, 0, 1);
        flaps.activateFlap(coPilot, 1, 2);
        boolean result = flaps.activateFlap(coPilot, flapIndex, validValue);

        assertTrue(result);
        assertTrue(flaps.isActivated(flapIndex));
    }

    @Test
    void testDoNotActivateThirdFlapWhenIncorrectValueMock() {
        int flapIndex = 2;
        int invalidValue = 6;

        flaps.activateFlap(mockCopilot, 0, 1);
        flaps.activateFlap(mockCopilot, 1, 2);
        boolean result = flaps.activateFlap(mockCopilot, flapIndex, invalidValue);

        assertFalse(result);
        assertFalse(flaps.isActivated(flapIndex));
    }

    // Integration test
    @Test
    void testDoNotActivateThirdFlapWhenIncorrectValue() {
        int flapIndex = 2;
        int invalidValue = 6;

        flaps.activateFlap(coPilot, 0, 1);
        flaps.activateFlap(coPilot, 1, 2);
        boolean result = flaps.activateFlap(coPilot, flapIndex, invalidValue);

        assertFalse(result);
        assertFalse(flaps.isActivated(flapIndex));
    }

    @Test
    void testActivateFourthFlapWhenCorrectValueMock() {
        int flapIndex = 3;
        int validValue = 6;

        flaps.activateFlap(mockCopilot, 0, 1);
        flaps.activateFlap(mockCopilot, 1, 2);
        flaps.activateFlap(mockCopilot, 2, 4);
        boolean result = flaps.activateFlap(mockCopilot, flapIndex, validValue);

        assertTrue(result);
        assertTrue(flaps.isActivated(flapIndex));
    }

    // Integration test
    @Test
    void testActivateFourthFlapWhenCorrectValue() {
        int flapIndex = 3;
        int validValue = 6;

        flaps.activateFlap(coPilot, 0, 1);
        flaps.activateFlap(coPilot, 1, 2);
        flaps.activateFlap(coPilot, 2, 4);
        boolean result = flaps.activateFlap(coPilot, flapIndex, validValue);

        assertTrue(result);
        assertTrue(flaps.isActivated(flapIndex));
    }

    @Test
    void testDoNotActivateFourthFlapWhenIncorrectValueMock() {
        int flapIndex = 3;
        int invalidValue = 1;

        flaps.activateFlap(mockCopilot, 0, 1);
        flaps.activateFlap(mockCopilot, 1, 2);
        flaps.activateFlap(mockCopilot, 2, 4);
        boolean result = flaps.activateFlap(mockCopilot, flapIndex, invalidValue);

        assertFalse(result);
        assertFalse(flaps.isActivated(flapIndex));
    }

    // Integration test
    @Test
    void testDoNotActivateFourthFlapWhenIncorrectValue() {
        int flapIndex = 3;
        int invalidValue = 1;

        flaps.activateFlap(coPilot, 0, 1);
        flaps.activateFlap(coPilot, 1, 2);
        flaps.activateFlap(coPilot, 2, 4);
        boolean result = flaps.activateFlap(coPilot, flapIndex, invalidValue);

        assertFalse(result);
        assertFalse(flaps.isActivated(flapIndex));
    }

    @Test
    void testActivateAllFlapsSequentially() {
        assertTrue(flaps.activateFlap(coPilot, 0, 1));
        assertTrue(flaps.activateFlap(coPilot, 1, 2));
        assertTrue(flaps.activateFlap(coPilot, 2, 4));
        assertTrue(flaps.activateFlap(coPilot, 3, 6));

        assertTrue(flaps.allActivated());
    }
    @Test
    void testActivateAlreadyActivatedFlapMock() {
        int flapIndex = 0;
        int value = 1;

        flaps.activateFlap(mockCopilot, flapIndex, value);

        boolean result = flaps.activateFlap(mockCopilot, flapIndex, value);

        assertFalse(result);
    }

    // Integration test
    @Test
    void testActivateAlreadyActivatedFlap() {
        int flapIndex = 0;
        int value = 1;

        flaps.activateFlap(coPilot, flapIndex, value);

        boolean result = flaps.activateFlap(coPilot, flapIndex, value);

        assertFalse(result);
    }

    @Test
    void testResetFlapsWhenAllFlapsActivated() {
        flaps.activateFlap(coPilot, 0, 1);
        flaps.activateFlap(coPilot, 1, 2);
        flaps.activateFlap(coPilot, 2, 4);
        flaps.activateFlap(coPilot, 3, 5);

        assertTrue(flaps.allActivated());

        flaps.resetFlaps();

        for (int i = 0; i < flaps.getActivated().length; i++) {
            assertFalse(flaps.isActivated(i));
        }
    }

    @Test
    void testResetFlapsWhenSomeFlapsActivated() {
        flaps.activateFlap(coPilot, 0, 1);
        flaps.activateFlap(coPilot, 1, 2);

        assertTrue(flaps.isActivated(0));
        assertTrue(flaps.isActivated(1));
        assertFalse(flaps.isActivated(2));
        assertFalse(flaps.isActivated(3));

        flaps.resetFlaps();

        for (int i = 0; i < flaps.getActivated().length; i++) {
            assertFalse(flaps.isActivated(i));
        }
    }
}
