package com.espabila.skyteam.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BrakesTest {
    Brakes brakes;
    Pilot pilot;

    @Mock
    private Pilot mockPilot;

    @BeforeEach
    void setUp() {
        brakes = new Brakes();
        pilot = new Pilot();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testActivateFirstBrakeMock() {
        int brakeSlot = 0;
        int diceValue = 2;

        boolean result = brakes.activateBrakes(mockPilot, brakeSlot, diceValue);

        assertTrue(result);
        verify(mockPilot).removeDice(diceValue);
    }

    // Integration test
    @Test
    void testActivateFirstBrake() {
        int brakeSlot = 0;
        int diceValue = 2;

        boolean result = brakes.activateBrakes(pilot, brakeSlot, diceValue);

        assertTrue(result);
    }

    @Test
    void testDoNotActivateSecondBrakeWhenFirstIsNotActivatedMock() {
        int brakeSlot = 1;
        int diceValue = 2;

        boolean result = brakes.activateBrakes(mockPilot, brakeSlot, diceValue);

        assertFalse(result);
    }

    // Integration test
    @Test
    void testDoNotActivateSecondBrakeWhenFirstIsNotActivated() {
        int brakeSlot = 1;
        int diceValue = 2;

        boolean result = brakes.activateBrakes(pilot, brakeSlot, diceValue);

        assertFalse(result);
    }

    @Test
    void testActivateSecondBrakeAfterFirstIsActivatedMock() {
        brakes.activateBrakes(pilot, 0, 2);
        int brakeSlot = 1;
        int diceValue = 4;

        boolean result = brakes.activateBrakes(mockPilot, brakeSlot, diceValue);

        assertTrue(result);
        verify(mockPilot).removeDice(diceValue);
    }

    // Integration test
    @Test
    void testActivateSecondBrakeAfterFirstIsActivated() {
        brakes.activateBrakes(pilot, 0, 2);
        int brakeSlot = 1;
        int diceValue = 4;

        boolean result = brakes.activateBrakes(pilot, brakeSlot, diceValue);

        assertTrue(result);
    }

    @Test
    void testDoNotActivateBrakeWithWrongDiceValueMock() {
        int brakeSlot = 0;
        int diceValue = 4;

        boolean result = brakes.activateBrakes(mockPilot, brakeSlot, diceValue);

        assertFalse(result);
    }

    @Test
    void testDoNotActivateBrakeWithWrongDiceValue() {
        int brakeSlot = 0;
        int diceValue = 4;

        boolean result = brakes.activateBrakes(pilot, brakeSlot, diceValue);

        assertFalse(result);
    }

    @Test
    void testResetAllBrakes() {
        for (int i = 0; i < brakes.getActivated().length; i++) {
            brakes.getActivated()[i] = true;
        }

        brakes.resetBrakes();

        for (int i = 0; i < brakes.getActivated().length; i++) {
            assertFalse(brakes.isBrakeActivated(i));
        }
    }
}
