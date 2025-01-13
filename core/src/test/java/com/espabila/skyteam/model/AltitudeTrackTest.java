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
        assertEquals(1, altitudeTrack.getCurrentRound());
        assertFalse(altitudeTrack.isPilotTurn());

        // Test multiple rounds
        for (int i = 2; i <= 6; i++) {
            altitudeTrack.newRound();
            assertEquals(i, altitudeTrack.getCurrentRound());
            assertEquals(i % 2 == 0, altitudeTrack.isPilotTurn());
        }

        // Test that round doesn't increase beyond 6
        altitudeTrack.newRound();
        assertEquals(6, altitudeTrack.getCurrentRound());
    }

    @Test
    void testCheckLastRound() {
        for (int i = 0; i < 5; i++) {
            altitudeTrack.newRound();
            assertFalse(altitudeTrack.isLastRound());
        }

        altitudeTrack.newRound();  // This should be the 6th round
        altitudeTrack.checkLastRound();
        assertTrue(altitudeTrack.isLastRound());
        assertTrue(altitudeTrack.isPilotTurn());  // Should switch back to pilot on last round
    }

    @Test
    void testRerollAvailableWhenCurrentRoundIsZero() {
        Reroll reroll = new Reroll();
        reroll.setRerollAvailable(false);

        altitudeTrack.rerollAvailable(reroll);

        assertTrue(reroll.getRerollAvailable());
    }

    @Test
    void testRerollAvailableWhenCurrentRoundIsFour() {
        Reroll reroll = new Reroll();
        reroll.setRerollAvailable(false);

        altitudeTrack.setCurrentRound(4);

        altitudeTrack.rerollAvailable(reroll);

        assertTrue(reroll.getRerollAvailable());
    }

    @Test
    void testRerollAvailableWhenCurrentRoundIsNotZeroOrFour() {
        Reroll reroll = new Reroll();
        reroll.setRerollAvailable(false);

        altitudeTrack.setCurrentRound(2);

        altitudeTrack.rerollAvailable(reroll);

        assertFalse(reroll.getRerollAvailable());
    }

    // create test when reroll is available but not used in the current round
}
