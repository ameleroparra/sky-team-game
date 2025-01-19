package com.espabila.skyteam.model;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class AltitudeTrackTest {

    private AltitudeTrack altitudeTrack;

    @BeforeEach
    void setUp() {
        altitudeTrack = new AltitudeTrack();
    }

    @Test
    void testNewRound() {
        altitudeTrack.newRound();
        assertEquals(1, altitudeTrack.getCurrentRound(), "Should increase round number by one");
        assertFalse(altitudeTrack.isPilotTurn(), "Should change turn on a new round");

        for (int i = 2; i <= 6; i++) {
            altitudeTrack.newRound();
            assertEquals(i, altitudeTrack.getCurrentRound());
            assertEquals(i % 2 == 0, altitudeTrack.isPilotTurn());
        }

        altitudeTrack.newRound();
        assertEquals(6, altitudeTrack.getCurrentRound());
    }

    @Test
    void testLastRound() {
        for (int i = 0; i < 6; i++) {
            altitudeTrack.newRound();
            assertFalse(altitudeTrack.isLastRound());
        }

        altitudeTrack.checkLastRound();
        assertTrue(altitudeTrack.isLastRound());
        assertTrue(altitudeTrack.isPilotTurn());  // Should switch back to pilot on last round
    }

    @Test
    void testRerollAvailableWhenCurrentRoundIsZero() {
        Reroll reroll = new Reroll();
        reroll.setRerollAvailable(false);

        altitudeTrack.rerollAvailable(reroll);

        assertTrue(reroll.isRerollAvailable());
    }

    @Test
    void testRerollAvailableWhenCurrentRoundIsFour() {
        Reroll reroll = new Reroll();
        reroll.setRerollAvailable(false);

        altitudeTrack.setCurrentRound(4);

        altitudeTrack.rerollAvailable(reroll);

        assertTrue(reroll.isRerollAvailable());
    }

    @Test
    void testRerollUnavailableWhenCurrentRoundIsNotZeroOrFour() {
        Reroll reroll = new Reroll();
        reroll.setRerollAvailable(false);

        altitudeTrack.setCurrentRound(2);

        altitudeTrack.rerollAvailable(reroll);

        assertFalse(reroll.isRerollAvailable());
    }
}
