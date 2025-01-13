package com.espabila.skyteam.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AxisTest {

    Axis axis = new Axis();
    Player pilot = new Pilot();
    Player copilot = new CoPilot();

    @BeforeEach
    public void setUp() {
        axis = new Axis();
    }

    @Test
    public void testPlaceDiceForPilotWhenPilotSlotIsEmpty() {
        int diceValue = 4;

        axis.placeDice(pilot, diceValue);

        assertEquals(diceValue, axis.getPilotSlot());
    }

    @Test
    public void testPlaceDiceForCopilotWhenCopilotSlotIsEmpty() {
        int diceValue = 3;

        axis.placeDice(copilot, diceValue);

        assertEquals(diceValue, axis.getCopilotSlot());
    }

    @Test
    public void testNotPlaceDiceWhenPilotSlotIsAlreadyFilled() {
        int firstDiceValue = 4;
        int secondDiceValue = 5;

        axis.placeDice(pilot, firstDiceValue);
        axis.placeDice(pilot, secondDiceValue);

        assertEquals(firstDiceValue, axis.getPilotSlot());
    }

    @Test
    public void testNotPlaceDiceWhenCopilotSlotIsAlreadyFilled() {
        int firstDiceValue = 4;
        int secondDiceValue = 5;

        axis.placeDice(copilot, firstDiceValue);
        axis.placeDice(copilot, secondDiceValue);

        assertEquals(firstDiceValue, axis.getCopilotSlot());
    }

    @Test
    public void testReturnFalseWhenAreDicesPlacedCalledAndOnlyPilotSlotFilled() {
        int diceValue = 4;

        axis.placeDice(pilot, diceValue);

        assertFalse(axis.areDicesPlaced());
    }

    @Test
    public void testReturnFalseWhenAreDicesPlacedCalledAndOnlyCoPilotSlotFilled() {
        int diceValue = 4;

        axis.placeDice(copilot, diceValue);

        assertFalse(axis.areDicesPlaced());
    }

    @Test
    public void testReturnTrueWhenAreDicesPlacedCalledAndBothSlotsFilled() {
        int pilotDiceValue = 4;
        int copilotDiceValue = 3;

        axis.placeDice(pilot, pilotDiceValue);
        axis.placeDice(copilot, copilotDiceValue);

        assertTrue(axis.areDicesPlaced());
    }

    @Test
    public void testCorrectlyCalculateCurrentIndexWhenPilotSlotIsGreaterThanCopilotSlot() {
        int pilotDiceValue = 5;
        int copilotDiceValue = 3;

        axis.placeDice(pilot, pilotDiceValue);
        axis.placeDice(copilot, copilotDiceValue);
        axis.currentIndexCalculation();

        assertEquals(0, axis.getCurrentIndexPosition());
    }

    @Test
    public void testCorrectlyCalculateCurrentIndexWhenCopilotSlotIsGreaterThanPilotSlot() {
        int pilotDiceValue = 2;
        int copilotDiceValue = 4;

        axis.placeDice(pilot, pilotDiceValue);
        axis.placeDice(copilot, copilotDiceValue);
        axis.currentIndexCalculation();

        assertEquals(4, axis.getCurrentIndexPosition());
    }

    @Test
    public void testReturnCorrectValueForGetAbsResultAfterCurrentIndexCalculation() {
        int pilotDiceValue = 1;
        int copilotDiceValue = 6;

        axis.placeDice(pilot, pilotDiceValue);
        axis.placeDice(copilot, copilotDiceValue);
        axis.currentIndexCalculation();

        assertEquals(5, axis.getAbsResult());
    }

    @Test
    public void testNotChangeCurrentIndexPositionWhenPilotSlotEqualsCopilotSlot() {
        int diceValue = 4;

        axis.placeDice(pilot, diceValue);
        axis.placeDice(copilot, diceValue);
        int initialPosition = axis.getCurrentIndexPosition();

        axis.currentIndexCalculation();

        assertEquals(initialPosition, axis.getCurrentIndexPosition());
        assertFalse(axis.getGameOver());
    }

    @Test
    public void testNotPlaceDiceForPilotInCopilotSlot() {
        int diceValue = 4;

        axis.placeDice(pilot, diceValue);

        assertEquals(diceValue, axis.getPilotSlot());
        assertEquals(0, axis.getCopilotSlot());
    }

    @Test
    public void testNotPlaceDiceForCopilotInPilotSlot() {
        int diceValue = 4;

        axis.placeDice(copilot, diceValue);

        assertEquals(diceValue, axis.getCopilotSlot());
        assertEquals(0, axis.getPilotSlot());

    }

    @Test
    public void testRemoveDiceFromPilotListAfterPlacement() {
        List<Integer> diceList = new ArrayList<>(Arrays.asList(2, 1, 4, 6));

        int diceValue = 4;
        pilot.rollDices();
        pilot.setDiceList(diceList);

        axis.placeDice(pilot, diceValue);

        assertFalse(pilot.getDiceList().contains(diceValue));
        assertEquals(3, pilot.getDiceList().size());
        assertEquals(diceValue, axis.getPilotSlot());
    }



    @Test
    public void testMaintainCurrentIndexPositionAtTwoWhenNoDicesArePlaced() {
        assertEquals(2, axis.getCurrentIndexPosition());

        axis.currentIndexCalculation();

        assertEquals(2, axis.getCurrentIndexPosition());
        assertEquals(0, axis.getPilotSlot());
        assertEquals(0, axis.getCopilotSlot());
        assertFalse(axis.getGameOver());
    }

    @Test
    public void testResetBothPilotAndCopilotSlotsToZeroWhenResetSlotsCalled() {
        int pilotDiceValue = 5;
        int copilotDiceValue = 3;

        axis.placeDice(pilot, pilotDiceValue);
        axis.placeDice(copilot, copilotDiceValue);

        axis.resetAxisSlots();

        assertEquals(0, axis.getPilotSlot());
        assertEquals(0, axis.getCopilotSlot());
    }

    @Test
    public void testCorrectlyUpdateCurrentIndexPositionWhenAtMinimumAndMovesTowardsPositive() {
        axis.setCurrentIndexPosition(-2);

        int pilotDiceValue = 1;
        int copilotDiceValue = 5;

        axis.placeDice(pilot, pilotDiceValue);
        axis.placeDice(copilot, copilotDiceValue);
        axis.currentIndexCalculation();

        assertEquals(2, axis.getCurrentIndexPosition());
        assertFalse(axis.getGameOver());
    }
}
