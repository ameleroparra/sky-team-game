package com.espabila.skyteam.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EnginesTest {

    private Engines engines;

    @BeforeEach
    void setUp() {
        engines = new Engines();
    }

    @Test
    void testInitialValues() {
        assertEquals(0, engines.getApproachTrackMove());
    }

    @Test
    void testPlaceDice() {
        Player pilot = new Pilot();
        Player copilot = new CoPilot();

        engines.placeDice(pilot, 3);
        engines.placeDice(copilot, 4);

        assertTrue(engines.areDicesPlaced(0));
    }

    @Test
    void testAdvancePilotMarker() {
        double initialMarker = engines.getPilotMarker();
        engines.advancePilotMarker();
        assertEquals(initialMarker + 1, engines.getPilotMarker());
    }

    @Test
    void testAdvanceCopilotMarker() {
        double initialMarker = engines.getCopilotMarker();
        engines.advanceCopilotMarker();
        assertEquals(initialMarker + 1, engines.getCopilotMarker());
    }

    @Test
    void testCountDiceSum() {
        engines.countDiceSum(2, 2); // Sum: 4, should be less than pilotMarker (4.5)
        assertEquals(0, engines.getApproachTrackMove());

        engines.countDiceSum(3, 3); // Sum: 6, should be between pilotMarker (4.5) and copilotMarker (8.5)
        assertEquals(1, engines.getApproachTrackMove());

        engines.countDiceSum(5, 5); // Sum: 10, should be greater than copilotMarker (8.5)
        assertEquals(2, engines.getApproachTrackMove());
    }

    @Test
    void testMaxEngineMarkers() {
        for (int i = 0; i < 3; i++) {
            engines.advancePilotMarker();
            engines.advanceCopilotMarker();
        }
        assertEquals(6.5, engines.getPilotMarker());
        assertEquals(11.5, engines.getCopilotMarker());

        engines.advancePilotMarker(); // Should not increase further
        engines.advanceCopilotMarker(); // Should not increase further

        assertEquals(6.5, engines.getPilotMarker());
        assertEquals(12.5, engines.getCopilotMarker());
    }

    @Test
    void shouldReturnFalseWhenPlacingDiceOnOccupiedPilotSlot(){
        Pilot pilot = new Pilot();

        engines.setPilotSlot(3);
        engines.placeDice(pilot, 2);

        assertEquals(3, engines.getPilotSlot());
    }

    @Test
    void shouldReturnFalseWhenPlacingDiceOnOccupiedCopilotSlot(){
        CoPilot coPilot = new CoPilot();

        engines.setCopilotSlot(5);
        engines.placeDice(coPilot, 1);

        assertEquals(5, engines.getCopilotSlot());
    }
}
