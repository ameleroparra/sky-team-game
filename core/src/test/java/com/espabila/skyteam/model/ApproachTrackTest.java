package com.espabila.skyteam.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ApproachTrackTest {

    private ApproachTrack approachTrack;
    private Engines engines;

    @BeforeEach
    void setUp() {
        approachTrack = new ApproachTrack();
        engines = new Engines();
    }

    @Test
    void testMoveForwardTwoSpacesIntoPlaneToken() {
        engines.setPilotSlot(5);
        engines.setCopilotSlot(5);
        engines.countDiceSum(); // Sum: 10, should result in approachTrackMove = 2
        approachTrack.moveForward(engines);
        engines.countDiceSum(); // Sum: 10, should result in approachTrackMove = 2
        approachTrack.moveForward(engines);
        assertTrue(approachTrack.getGameOver());
    }

    @Test
    void testNoMovementWhenLowDiceSum() {
        engines.setPilotSlot(2);
        engines.setCopilotSlot(2);
        engines.countDiceSum(); // Sum: 4, should result in approachTrackMove = 0
        approachTrack.moveForward(engines);
        assertEquals(0, approachTrack.getCurrentPosition());
        assertFalse(approachTrack.getGameOver());
    }

    @Test
    void testLastTrackIsFalseWhenCurrentPositionIsNotLast() {
        approachTrack.setCurrentPosition(3);
        approachTrack.isLastTrack();
        assertFalse(approachTrack.getLastTrack(), "lastTrack should remain false when currentPosition is not 6");

        approachTrack.setCurrentPosition(5);
        approachTrack.isLastTrack();
        assertFalse(approachTrack.getLastTrack(), "lastTrack should remain false when currentPosition is not 6");
    }

    @Test
    void testIsGameOver() {
        assertFalse(approachTrack.isGameOver(), "Game should not be over initially");

        approachTrack.setGameOver(true);
        assertTrue(approachTrack.isGameOver(), "Game should be over after setting gameOver to true");

        approachTrack.setGameOver(false);
        assertFalse(approachTrack.isGameOver(), "Game should not be over after resetting gameOver to false");
    }

    // Old test were removed because methods have changed
}
