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
    void testMoveForwardOneSpaceWhenNoPlanes() {
        approachTrack.setCurrentPosition(0);
        engines.setPilotMarker(4.5);
        engines.setCopilotMarker(8.5);
        engines.countDiceSum(3, 3); // the sum is between two markers, therefore it should move forward by 1 space

        // Clear the path
        int[] planeTokens = approachTrack.setPlaneTokens(int[0, 0, 0, 0, 0, 0]);
        planeTokens[1] = 0;
        planeTokens[2] = 0;
        approachTrack.setPlaneTokens(planeTokens);

        approachTrack.moveForward(engines);

        int finalPosition = approachTrack.getCurrentPosition();

        assertEquals(2, engines.getApproachTrackMove(), "Engines should set approachTrackMove to 2");
        assertEquals(approachTrack.getCurrentPosition() + 2, finalPosition, "ApproachTrack should move forward by 2 spaces when path is clear");
        assertFalse(approachTrack.getGameOver(), "Game should not be over when path is clear");
    }

    @Test
    void testMoveForwardTwoSpacesWhenNoPlanes() {
        approachTrack.setCurrentPosition(0);
        engines.countDiceSum(5, 5); // Sum: 10, should result in approachTrackMove = 2

        // Clear the path
        int[] planeTokens = approachTrack.getPlaneTokens();
        planeTokens[1] = 0;
        planeTokens[2] = 0;
        approachTrack.setPlaneTokens(planeTokens);

        approachTrack.moveForward(engines);

        int finalPosition = approachTrack.getCurrentPosition();

        assertEquals(2, engines.getApproachTrackMove(), "Engines should set approachTrackMove to 2");
        assertEquals(approachTrack.getCurrentPosition() + 2, finalPosition, "ApproachTrack should move forward by 2 spaces when path is clear");
        assertFalse(approachTrack.getGameOver(), "Game should not be over when path is clear");
    }

    @Test
    void testMoveForwardTwoSpacesBlocked() {
        int initialPosition = approachTrack.getCurrentPosition();
        engines.countDiceSum(5, 5); // Sum: 10, should result in approachTrackMove = 2

        approachTrack.moveForward(engines);

        int finalPosition = approachTrack.getCurrentPosition();

        assertEquals(2, engines.getApproachTrackMove(), "Engines should set approachTrackMove to 2");
        assertEquals(initialPosition, finalPosition, "ApproachTrack should not move when there's a plane token in the way");
        assertTrue(approachTrack.getGameOver(), "Game should be over when hitting a plane token");
    }

    @Test
    void testMoveForwardIntoPlaneToken() {
        approachTrack.setCurrentPosition(1);
        engines.countDiceSum(3, 3); // Sum: 6, should result in approachTrackMove = 1
        approachTrack.moveForward(engines);
        assertTrue(approachTrack.getGameOver());
    }

    @Test
    void testMoveForwardTwoSpacesIntoPlaneToken() {
        engines.countDiceSum(5, 5); // Sum: 10, should result in approachTrackMove = 2
        approachTrack.moveForward(engines);
        engines.countDiceSum(5, 5); // Sum: 10, should result in approachTrackMove = 2
        approachTrack.moveForward(engines);
        assertTrue(approachTrack.getGameOver());
    }

    @Test
    void testMoveForwardBeyondLastTrack() {
        approachTrack.setCurrentPosition(5);
        engines.countDiceSum(3, 3); // Sum: 6, should result in approachTrackMove = 1
        approachTrack.moveForward(engines);
        assertTrue(approachTrack.getGameOver());
    }

    @Test
    void testNoMovementWithLowDiceSum() {
        engines.countDiceSum(2, 2); // Sum: 4, should result in approachTrackMove = 0
        approachTrack.moveForward(engines);
        assertEquals(0, approachTrack.getCurrentPosition());
        assertFalse(approachTrack.getGameOver());
    }

    @Test
    void testIsLastTrackWhenMovingToPositionSix() {
        approachTrack.setCurrentPosition(6);
        approachTrack.isLastTrack();
        assertTrue(approachTrack.getLastTrack(), "lastTrack should be true when currentPosition is 6");
    }

    @Test
    void testIsLastTrackMaintainsFalseForNonLastPositions() {
        approachTrack.setCurrentPosition(3);
        approachTrack.isLastTrack();
        assertFalse(approachTrack.getLastTrack(), "lastTrack should remain false when currentPosition is not 6");

        approachTrack.setCurrentPosition(5);
        approachTrack.isLastTrack();
        assertFalse(approachTrack.getLastTrack(), "lastTrack should remain false when currentPosition is not 6");
    }

    @Test
    void testMoveForwardOneSpaceUnblocked() {
        int initialPosition = approachTrack.getCurrentPosition();
        engines.countDiceSum(3, 3); // Sum: 6, should result in approachTrackMove = 1

        // Clear the path
        int[] planeTokens = approachTrack.getPlaneTokens();
        planeTokens[1] = 0;
        approachTrack.setPlaneTokens(planeTokens);

        approachTrack.moveForward(engines);

        int finalPosition = approachTrack.getCurrentPosition();

        assertEquals(1, engines.getApproachTrackMove(), "Engines should set approachTrackMove to 1");
        assertEquals(initialPosition + 1, finalPosition, "ApproachTrack should move forward by 1 space when path is clear");
        assertFalse(approachTrack.getGameOver(), "Game should not be over when path is clear");
        assertFalse(approachTrack.getLastTrack(), "Should not be on last track after moving one space");
    }

    @Test
    void testMoveForwardOneSpaceWhenMoreThanLastTrackNum() {
        approachTrack.setCurrentPosition(6);
        engines.setApproachTrackMove(1); // Sum: 6, should result in approachTrackMove = 1

        approachTrack.moveForward(engines);

        assertTrue(approachTrack.getGameOver(), "Game should be over when trying to move beyond the last track");
        assertEquals(6, approachTrack.getCurrentPosition(), "Current position should not change when trying to move beyond the last track");
    }

    @Test
    void testMoveForwardTwoSpacesWhenMoreThanLastTrackNum() {
        approachTrack.setCurrentPosition(6);
        engines.setApproachTrackMove(2); // Sum: 6, should result in approachTrackMove = 1

        approachTrack.moveForward(engines);

        assertTrue(approachTrack.getGameOver(), "Game should be over when trying to move beyond the last track");
        assertEquals(6, approachTrack.getCurrentPosition(), "Current position should not change when trying to move beyond the last track");
    }

    @Test
    void shouldCrashWhenArePlanesMoveForwardOne() {
        approachTrack.setCurrentPosition(1);
        engines.setApproachTrackMove(2); // Sum: 6, should result in approachTrackMove = 1

        approachTrack.moveForward(engines);

        assertEquals(1, approachTrack.getCurrentPosition(), "Current position should not change when trying to move to the track that has a plane");
        assertTrue(approachTrack.getGameOver(), "Game should be over when trying to move to the track that has a plane");
    }

    @Test
    void isGameOverTest() {
        assertFalse(approachTrack.isGameOver(), "Game should not be over initially");

        approachTrack.setGameOver(true);
        assertTrue(approachTrack.isGameOver(), "Game should be over after setting gameOver to true");

        approachTrack.setGameOver(false);
        assertFalse(approachTrack.isGameOver(), "Game should not be over after resetting gameOver to false");
    }
}
