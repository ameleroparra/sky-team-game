package com.espabila.skyteam.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class EnginesTest {
    private Engines engines;
    private Pilot pilot;
    private CoPilot copilot;
    @Mock
    private Pilot mockPilot;
    private CoPilot mockCopilot;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        engines = new Engines();
        pilot = new Pilot();
        copilot = new CoPilot();
    }

    @Test
    void testPlaceDiceOnEmptyPilotSlotMock() {
        int diceValue = 3;

        engines.placeDice(mockPilot, diceValue);

        assertEquals(diceValue, engines.getPilotSlot());
    }

    // Integration test
    @Test
    void testPlaceDiceOnEmptyPilotSlot() {
        int diceValue = 3;

        engines.placeDice(pilot, diceValue);

        assertEquals(diceValue, engines.getPilotSlot());
    }

    @Test
    void testPlaceDiceOnEmptyCopilotSlotMock() {
        int diceValue = 1;

        engines.placeDice(mockCopilot, diceValue);

        assertEquals(diceValue, engines.getCopilotSlot());
    }

    @Test
    void testPlaceDiceOnEmptyCopilotSlot() {
        int diceValue = 1;

        engines.placeDice(copilot, diceValue);

        assertEquals(diceValue, engines.getCopilotSlot());
    }


    @Test
    void testPlaceDiceOnOccupiedPilotSlotMock() {
        engines.setPilotSlot(3); //

        engines.placeDice(mockPilot, 2);

        assertEquals(3, engines.getPilotSlot());
    }

    // Integration test
    @Test
    void testPlaceDiceOnOccupiedPilotSlot() {
        engines.setPilotSlot(3);

        engines.placeDice(pilot, 2);

        assertEquals(3, engines.getPilotSlot());
    }

    @Test
    void testPlaceDiceOnOccupiedCopilotSlotMock() {
        engines.setPilotSlot(3);

        engines.placeDice(mockCopilot, 2);

        assertEquals(3, engines.getPilotSlot());
    }

    // Integration test
    @Test
    void testPlaceDiceOnOccupiedCopilotSlot() {
        engines.setCopilotSlot(5);

        engines.placeDice(copilot, 2);

        assertEquals(5, engines.getCopilotSlot());
    }

    @Test
    void testAdvancePilotMarker() {
        double initialMarker = engines.getPilotMarker();
        engines.advancePilotMarker();
        assertEquals(initialMarker + 1, engines.getPilotMarker());
    }

    @Test
    void testDoNotAdvancePilotMarkerWhenAtFinalPilotMarker() {
        engines.setPilotMarker(engines.getFinalPilotMarker());

        engines.advancePilotMarker();

        assertEquals(engines.getFinalPilotMarker(), engines.getPilotMarker(), "Pilot marker should be at finalPilotMarker");
    }

    @Test
    void testAdvanceCopilotMarker() {
        double initialMarker = engines.getCopilotMarker();

        engines.advanceCopilotMarker();

        assertEquals(initialMarker + 1, engines.getCopilotMarker());
    }

    @Test
    void testDoNotAdvanceCoPilotMarkerWhenAtFinalCoPilotMarker() {
        engines.setCopilotMarker(engines.getFinalCopilotMarker());

        engines.advanceCopilotMarker();

        assertEquals(engines.getFinalCopilotMarker(), engines.getCopilotMarker());
    }

    @Test
    void testCountDiceSum() {
        engines.setPilotSlot(2);
        engines.setCopilotSlot(2);
        engines.countDiceSum();
        assertEquals(0, engines.getApproachTrackMove());

        engines.setPilotSlot(3);
        engines.setCopilotSlot(3);
        engines.countDiceSum();
        assertEquals(1, engines.getApproachTrackMove());

        engines.setPilotSlot(5);
        engines.setCopilotSlot(5);
        engines.countDiceSum();
        assertEquals(2, engines.getApproachTrackMove());
    }

    @Test
    void testReturnFalseWhenPlacingDiceOnOccupiedPilotSlot(){
        engines.setPilotSlot(3);
        engines.placeDice(pilot, 2);

        assertEquals(3, engines.getPilotSlot());
    }

    @Test
    void testReturnFalseWhenPlacingDiceOnOccupiedCopilotSlot(){
        engines.setCopilotSlot(5);
        engines.placeDice(copilot, 1);

        assertEquals(5, engines.getCopilotSlot());
    }

    @Test
    void testResetEnginesSlotsWhenBothPilotAndCopilotSlotsAreOccupied() {
        engines.setPilotSlot(3);
        engines.setCopilotSlot(5);

        engines.resetEnginesSlots();

        assertEquals(0, engines.getPilotSlot());
        assertEquals(0, engines.getCopilotSlot());
    }

    @Test
    void testResetEngines() {
        engines.setPilotMarker(5.0);
        engines.setCopilotMarker(9.0);
        engines.setCopilotMarker(2);
        engines.setPilotSlot(3);
        engines.setPilotSlot(1);

        engines.resetEngines();

        assertEquals(4.5, engines.getPilotMarker());
        assertEquals(8.5, engines.getCopilotMarker());

        assertEquals(0, engines.getPilotSlot());
        assertEquals(0, engines.getCopilotSlot());
        assertEquals(0, engines.getApproachTrackMove());
    }

    @Test
    void testAreDicePlaced() {
        engines.setPilotSlot(3);
        engines.setCopilotSlot(5);

        boolean areDicePlaced = engines.areDicesPlaced();

        assertTrue(areDicePlaced);
    }

    @Test
    void testAreDicePlacedWhenBothEmpty() {
        engines.setPilotSlot(0);
        engines.setCopilotSlot(0);

        boolean areDicesPlaced = engines.areDicesPlaced();

        assertFalse(areDicesPlaced);
    }
}
