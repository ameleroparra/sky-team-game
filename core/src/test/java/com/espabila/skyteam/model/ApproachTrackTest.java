package com.espabila.skyteam.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApproachTrackTest {

    private ApproachTrack approachTrack;
    private Engines engines;

    @Mock
    private Engines mockEngines;

    @BeforeEach
    void setUp() {
        approachTrack = new ApproachTrack();
        MockitoAnnotations.openMocks(this);
        engines = new Engines();
    }

    @Test
    void testMoveForwardTwoSpacesIntoPlaneTokenMockito() {
        when(mockEngines.getApproachTrackMove()).thenReturn(2);

        approachTrack.moveForward(mockEngines);
        approachTrack.moveForward(mockEngines);

        verify(mockEngines, times(2)).getApproachTrackMove();
        assertTrue(approachTrack.getGameOver());
    }

    // Integration test
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
    void testNoMovementWhenLowDiceSumMockito() {
        when(mockEngines.getApproachTrackMove()).thenReturn(0);

        approachTrack.moveForward(mockEngines);

        verify(mockEngines).getApproachTrackMove();
        assertEquals(0, approachTrack.getCurrentPosition());
        assertFalse(approachTrack.getGameOver());
    }

    // Integration test
    @Test
    void testNoMovementWhenLowDiceSum() {
        engines.setPilotSlot(2);
        engines.setCopilotSlot(2);
        engines.countDiceSum();
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

    @Test
    void testMoveForwardExceedingTrackLengthMockito() {
        when(mockEngines.getApproachTrackMove()).thenReturn(2); // Maximum move amount
        approachTrack.setPlaneTokens(new int[]{0, 1});
        approachTrack.setCurrentPosition(0);

        approachTrack.moveForward(mockEngines);

        verify(mockEngines).getApproachTrackMove();
        assertTrue(approachTrack.getGameOver(), "Game should be over when moveAmount reaches end of track");
        assertEquals(0, approachTrack.getCurrentPosition(), "Current position should always be zero");
        assertEquals(0, approachTrack.getLastTrackNum(), "When it's the last track with only one track total, it matches current position number");
        assertTrue(approachTrack.getLastTrack(), "Last track flag should be set to true");
    }

    // Integration test
    @Test
    void testMoveForwardExceedingTrackLength() {
        engines.setPilotSlot(5);
        engines.setCopilotSlot(5);
        approachTrack.setPlaneTokens(new int[]{0, 1});
        approachTrack.setCurrentPosition(0);

        engines.countDiceSum();
        approachTrack.moveForward(engines);

        assertTrue(approachTrack.getGameOver(), "Game should be over when moveAmount reaches end of track");
        assertEquals(0, approachTrack.getCurrentPosition(), "Current position should be always zero");
        assertEquals(0, approachTrack.getLastTrackNum(), "When it's the last track, it matches current position number");
        assertTrue(approachTrack.getLastTrack(), "Last track flag should be set to true");
        assertEquals(2, engines.getApproachTrackMove(), "Approach track move should be 2");
    }

    @Test
    void testMoveForwardWhenFirstTokenIsNotZeroMockito() {
        approachTrack.setPlaneTokens(new int[]{1, 2, 1, 3, 2});
        approachTrack.setCurrentPosition(0);
        when(mockEngines.getApproachTrackMove()).thenReturn(1);

        approachTrack.moveForward(mockEngines);

        verify(mockEngines).getApproachTrackMove();
        assertTrue(approachTrack.getGameOver(), "Game should be over when first token is non-zero");
        assertEquals(0, approachTrack.getCurrentPosition(), "Current position should remain 0");
        assertArrayEquals(new int[]{1, 2, 1, 3, 2}, approachTrack.getPlaneTokens(), "Plane tokens should remain unchanged");
    }

    // Integration test
    @Test
    void testMoveForwardWhenFirstTokenIsNotZero() {
        approachTrack.setPlaneTokens(new int[]{1, 2, 1, 3, 2});
        approachTrack.setCurrentPosition(0);
        engines.setPilotSlot(3);
        engines.setCopilotSlot(3);

        engines.countDiceSum();
        approachTrack.moveForward(engines);

        // Assert
        assertTrue(approachTrack.getGameOver(), "Game should be over when first token is non-zero");
        assertEquals(0, approachTrack.getCurrentPosition(), "Current position should remain 0");
        assertArrayEquals(new int[]{1, 2, 1, 3, 2}, approachTrack.getPlaneTokens(), "Plane tokens should remain unchanged");
        assertEquals(1, engines.getApproachTrackMove(), "Approach track move should be 1");
    }

    @Test
    void testMoveForwardWhenCurrentPositionEqualsPlaneTokensLengthAfterMoveMockito() {
        ApproachTrack approachTrack = new ApproachTrack();
        Engines mockEngines = mock(Engines.class);

        approachTrack.setPlaneTokens(new int[]{0, 1});
        approachTrack.setCurrentPosition(0);
        when(mockEngines.getApproachTrackMove()).thenReturn(2);

        approachTrack.moveForward(mockEngines);

        verify(mockEngines).getApproachTrackMove();
        assertTrue(approachTrack.getGameOver(), "Game should be over when the last token is processed");
        assertEquals(0, approachTrack.getCurrentPosition(), "Current position should remain 0");
        assertEquals(0, approachTrack.getLastTrackNum(), "When it's the last track with only one track total, it matches current position number");
        assertTrue(approachTrack.getLastTrack(), "Last track flag should be set to true");
        assertArrayEquals(new int[]{1}, approachTrack.getPlaneTokens(), "Plane tokens should have only the last token remaining");
    }

    // Integration test
    @Test
    void testMoveForwardWhenCurrentPositionEqualsPlaneTokensLengthAfterMove() {
        ApproachTrack approachTrack = new ApproachTrack();
        Engines engines = new Engines();

        approachTrack.setPlaneTokens(new int[]{0, 1});
        approachTrack.setCurrentPosition(0);
        engines.setPilotSlot(5);
        engines.setCopilotSlot(5);

        engines.countDiceSum();
        approachTrack.moveForward(engines);

        assertTrue(approachTrack.getGameOver(), "Game should be over when the last token is processed");
        assertEquals(0, approachTrack.getCurrentPosition(), "Current position should remain 0");
        assertEquals(0, approachTrack.getLastTrackNum(), "When it's the last track with only one track total, it matches current position number");
        assertTrue(approachTrack.getLastTrack(), "Last track flag should be set to true");
        assertArrayEquals(new int[]{1}, approachTrack.getPlaneTokens(), "Plane tokens should have only the last token remaining");
        assertEquals(2, engines.getApproachTrackMove(), "Approach track move should be 2");
    }

    @Test
    void testLastTrackNumUpdateAfterMovementMockito() {
        ApproachTrack approachTrack = new ApproachTrack();
        Engines mockEngines = mock(Engines.class);

        when(mockEngines.getApproachTrackMove()).thenReturn(2);
        approachTrack.setPlaneTokens(new int[]{0, 0, 1, 2, 1});

        approachTrack.moveForward(mockEngines);

        assertEquals(2, approachTrack.getLastTrackNum(), "lastTrackNum should be updated to 2 after moving forward");
    }

    // Integration test
    @Test
    void testLastTrackNumUpdateAfterMovement() {
        ApproachTrack approachTrack = new ApproachTrack();
        Engines engines = new Engines();

        approachTrack.setPlaneTokens(new int[]{0, 0, 1, 2, 1});
        engines.setPilotSlot(5);
        engines.setCopilotSlot(5);

        engines.countDiceSum();
        approachTrack.moveForward(engines);

        assertEquals(2, approachTrack.getLastTrackNum(), "lastTrackNum should be updated to 2 after moving forward");
        assertEquals(0, approachTrack.getCurrentPosition(), "Current position should always zero");
        assertEquals(2, engines.getApproachTrackMove(), "Approach track move should be 2");
    }

    @Test
    void testIsLastTrackCalledAfterMoveForwardMockito() {
        ApproachTrack approachTrack = new ApproachTrack();
        Engines mockEngines = mock(Engines.class);

        when(mockEngines.getApproachTrackMove()).thenReturn(1);
        approachTrack.setPlaneTokens(new int[]{0, 1});

        approachTrack.moveForward(mockEngines);

        assertTrue(approachTrack.getLastTrack(), "Last track flag should be set to true after moving forward");
        assertEquals(1, approachTrack.getPlaneTokens().length, "PlaneTokens should have only one element left");
        assertEquals(1, approachTrack.getPlaneTokens()[0], "The remaining plane token should be 1");
    }

    // Integration Test
    @Test
    void testIsLastTrackCalledAfterMoveForward() {
        ApproachTrack approachTrack = new ApproachTrack();
        Engines engines = new Engines();

        approachTrack.setPlaneTokens(new int[]{0, 1});
        engines.setPilotSlot(3);
        engines.setCopilotSlot(3);

        engines.countDiceSum();
        approachTrack.moveForward(engines);

        assertTrue(approachTrack.getLastTrack(), "Last track flag should be set to true after moving forward");
        assertEquals(1, approachTrack.getPlaneTokens().length, "PlaneTokens should have only one element left");
        assertEquals(1, approachTrack.getPlaneTokens()[0], "The remaining plane token should be 1");
        assertEquals(0, approachTrack.getCurrentPosition(), "Current position should always be zero");
        assertEquals(1, engines.getApproachTrackMove(), "Approach track move should be 1");
    }

    @Test
    void testResetApproachTrackAfterGameOver() {
        // Set up the ApproachTrack to simulate a game that has ended
        approachTrack.setCurrentPosition(5);
        approachTrack.setLastTrack(true);
        approachTrack.setLastTrackNum(0);
        approachTrack.setPlaneTokens(new int[]{3});
        approachTrack.setGameOver(true);

        // Call resetApproachTrack
        approachTrack.resetApproachTrack();

        // Assert that all values are reset to their initial state
        assertEquals(0, approachTrack.getCurrentPosition(), "Current position should be reset to 0");
        assertFalse(approachTrack.getLastTrack(), "Last track flag should be reset to false");
        assertEquals(6, approachTrack.getLastTrackNum(), "Last track number should be reset to 6");
        assertArrayEquals(new int[]{0, 0, 1, 2, 1, 3, 2}, approachTrack.getPlaneTokens(), "Plane tokens should be reset to initial state");
        assertFalse(approachTrack.getGameOver(), "Game over flag should be reset to false");
    }

    @Test
    public void testHowManyplanesAtTheAirport() {
        assertEquals(0, approachTrack.howManyplanesAtTheAirport());

        approachTrack.setPlaneTokens(new int[]{2});
        assertEquals(2, approachTrack.howManyplanesAtTheAirport());

        approachTrack.setPlaneTokens(new int[]{3});
        assertEquals(3, approachTrack.howManyplanesAtTheAirport());

        approachTrack.setPlaneTokens(new int[]{0});
        assertEquals(0, approachTrack.howManyplanesAtTheAirport());
    }
}
