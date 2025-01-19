package com.espabila.skyteam.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BrakesTest {

    Brakes brakes;
    Pilot pilot;

    @BeforeEach
    void setUp() {
        brakes = new Brakes();
        pilot = new Pilot();
    }

    @Test
    void shouldReturnFalseWhenAttemptingToActivateSecondBrakeWithoutActivatingPreviousBrakes() {
        int brakeSlot = 1;
        int diceValue = 2;

        boolean result = brakes.activateBrakes(pilot, brakeSlot, diceValue);

        assertFalse(result);
    }

    @Test
    void shouldReturnTrueWhenAttemptingToActivateFirstBrakeWithoutActivatingPreviousBrakes() {
        int brakeSlot = 0;
        int diceValue = 2;

        boolean result = brakes.activateBrakes(pilot, brakeSlot, diceValue);

        assertTrue(result);
    }

    @Test
    void shouldReturnTrueWhenAttemptingToActivateSecondBrakeAfterActivatingPreviousBrakes() {
        brakes.activateBrakes(pilot, 0, 2);
        int brakeSlot = 1;
        int diceValue = 4;

        boolean result = brakes.activateBrakes(pilot, brakeSlot, diceValue);

        assertTrue(result);
    }

    @Test
    void shouldNotActivateBrakeWithWrongDiceValue() {
        int brakeSlot = 0;
        int diceValue = 4;

        boolean result = brakes.activateBrakes(pilot, brakeSlot, diceValue);

        assertFalse(result);
    }
}
