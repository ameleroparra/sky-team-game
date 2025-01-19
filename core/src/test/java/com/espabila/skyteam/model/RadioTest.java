package com.espabila.skyteam.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RadioTest {

    Radio radio;
    Player pilot;
    Player copilot;
    ApproachTrack approachTrack;

    @BeforeEach
    public void setUp() {
        radio = new Radio();
        pilot = new Pilot();
        copilot = new CoPilot();
        approachTrack = new ApproachTrack();
    }

    @Test
    public void testPlaceDiceForPilotWhenPilotSlotIsEmpty() {
        int diceValue = 4;

        radio.placeDice(pilot, diceValue);

        assertEquals(diceValue, radio.getPilotSlot());
    }

    @Test
    public void testDoNotPlaceDiceForPilotWhenPilotSlotIsAlreadyFilled() {
        int diceValue = 5;

        radio.setPilotSlot(1);
        radio.placeDice(pilot, diceValue);

        assertEquals(1, radio.getPilotSlot());
    }

    @Test
    public void testPlaceDiceForCopilotInFirstSlotWhenBothCopilotSlotsAreEmpty() {
        int diceValue = 3;

        radio.placeDice(copilot, diceValue);

        assertEquals(diceValue, radio.getCopilotFirstSlot());
        assertEquals(0, radio.getCopilotSecondSlot());
    }

    @Test
    public void testPlaceDiceForCopilotInSecondSlotWhenFirstSlotFilledButSecondEmpty() {
        int firstDiceValue = 3;
        int secondDiceValue = 5;

        radio.placeDice(copilot, firstDiceValue);
        radio.placeDice(copilot, secondDiceValue);

        assertEquals(firstDiceValue, radio.getCopilotFirstSlot());
        assertEquals(secondDiceValue, radio.getCopilotSecondSlot());
    }

    @Test
    public void testDoNotPlaceDiceForCopilotWhenBothCopilotSlotsAreFilled() {
        int diceValue = 3;

        radio.setCopilotFirstSlot(1);
        radio.setCopilotSecondSlot(2);
        radio.placeDice(copilot, diceValue);

        assertEquals(1, radio.getCopilotFirstSlot());
        assertEquals(2, radio.getCopilotSecondSlot());
    }

    @Test
    public void testRemoveDiceFromPilotListAfterPlacement() {
        List<Integer> diceList = new ArrayList<>(Arrays.asList(2, 1, 4, 6));

        int diceValue = 4;
        pilot.rollDice();
        pilot.setDiceList(diceList);

        radio.placeDice(pilot, diceValue);

        assertFalse(pilot.getDiceList().contains(diceValue));
        assertEquals(3, pilot.getDiceList().size());
        assertEquals(diceValue, radio.getPilotSlot());
    }

    @Test
    public void testRemovePlaneToken() {
        int diceValue = 1;
        int[] initialPlaneTokens = {0, 0, 1, 2, 1, 3, 2};

        approachTrack.setCurrentPosition(2);
        approachTrack.setPlaneTokens(initialPlaneTokens);

        radio.removePlaneToken(diceValue, approachTrack);

        int[] expectedPlaneTokens = {0, 0, 0, 2, 1, 3, 2};
        assertArrayEquals(expectedPlaneTokens, approachTrack.getPlaneTokens());
    }

    @Test
    public void testDoNotRemovePlaneTokenWhenNoTokensAtCalculatedPosition() {
        int diceValue = 2;
        int[] initialPlaneTokens = {0, 0, 0, 2, 1, 3, 2};

        approachTrack.setCurrentPosition(1);
        approachTrack.setPlaneTokens(initialPlaneTokens);

        radio.removePlaneToken(diceValue, approachTrack);

        int[] expectedPlaneTokens = {0, 0, 0, 2, 1, 3, 2};
        assertArrayEquals(expectedPlaneTokens, approachTrack.getPlaneTokens());
    }

    @Test
    public void testRemovePlaneTokenWhenCurrentPositionIsAtStartOfTrack() {
        int diceValue = 1;
        int[] initialPlaneTokens = {1, 2, 1, 2, 1, 3, 2};

        approachTrack.setCurrentPosition(0);
        approachTrack.setPlaneTokens(initialPlaneTokens);

        radio.removePlaneToken(diceValue, approachTrack);

        int[] expectedPlaneTokens = {0, 2, 1, 2, 1, 3, 2};
        assertArrayEquals(expectedPlaneTokens, approachTrack.getPlaneTokens());
    }

    @Test
    public void testHandleEdgeCaseWhenCurrentPositionIsAtEndOfTrack() {
        int diceValue = 3;
        int[] initialPlaneTokens = {0, 0, 0, 0, 0, 3};

        approachTrack.setCurrentPosition(6);
        approachTrack.setPlaneTokens(initialPlaneTokens);

        radio.removePlaneToken(diceValue, approachTrack);

        int[] expectedPlaneTokens = {0, 0, 0, 0, 0, 2};
        assertArrayEquals(expectedPlaneTokens, approachTrack.getPlaneTokens());
    }

    @Test
    public void testDoNotPlaceDiceForPilotInCopilotSlot() {
        int diceValue = 4;

        radio.placeDice(pilot, diceValue);

        assertEquals(diceValue, radio.getPilotSlot());
        assertEquals(0, radio.getCopilotFirstSlot());
        assertEquals(0, radio.getCopilotSecondSlot());
    }

    @Test
    public void testDoNotPlaceDiceForCopilotInPilotSlot() {
        int diceValue = 4;

        radio.placeDice(copilot, diceValue);

        assertEquals(0, radio.getPilotSlot());
        assertEquals(diceValue, radio.getCopilotFirstSlot());
        assertEquals(0, radio.getCopilotSecondSlot());
    }

}
