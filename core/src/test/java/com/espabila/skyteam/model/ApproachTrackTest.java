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
        MockitoAnnotations.openMocks(this);
        approachTrack = new ApproachTrack();
        engines = new Engines();
    }

    @Test
    void testMoveForwardTwoSpacesIntoPlaneTokenMock() {
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
        engines.countDiceSum();
        approachTrack.moveForward(engines);
        engines.countDiceSum();
        approachTrack.moveForward(engines);
        assertTrue(approachTrack.getGameOver());
    }

    @Test
    void testNoMovementWhenLowDiceSumMock() {
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
        assertFalse(approachTrack.getLastTrack());

        approachTrack.setCurrentPosition(5);
        approachTrack.isLastTrack();
        assertFalse(approachTrack.getLastTrack());
    }

    @Test
    void testIsGameOver() {
        assertFalse(approachTrack.isGameOver());

        approachTrack.setGameOver(true);
        assertTrue(approachTrack.isGameOver());

        approachTrack.setGameOver(false);
        assertFalse(approachTrack.isGameOver());
    }

    @Test
    void testMoveForwardExceedingTrackLengthMock() {
        when(mockEngines.getApproachTrackMove()).thenReturn(2);
        approachTrack.setPlaneTokens(new int[]{0, 1});
        approachTrack.setCurrentPosition(0);

        approachTrack.moveForward(mockEngines);

        verify(mockEngines).getApproachTrackMove();
        assertTrue(approachTrack.getGameOver());
        assertEquals(0, approachTrack.getCurrentPosition());
        assertEquals(0, approachTrack.getLastTrackNum());
        assertTrue(approachTrack.getLastTrack());
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

        assertTrue(approachTrack.getGameOver());
        assertEquals(0, approachTrack.getCurrentPosition());
        assertEquals(0, approachTrack.getLastTrackNum());
        assertTrue(approachTrack.getLastTrack());
        assertEquals(2, engines.getApproachTrackMove());
    }

    @Test
    void testMoveForwardWhenFirstTokenIsNotZeroMock() {
        approachTrack.setPlaneTokens(new int[]{1, 2, 1, 3, 2});
        approachTrack.setCurrentPosition(0);
        when(mockEngines.getApproachTrackMove()).thenReturn(1);

        approachTrack.moveForward(mockEngines);

        verify(mockEngines).getApproachTrackMove();
        assertTrue(approachTrack.getGameOver());
        assertEquals(0, approachTrack.getCurrentPosition());
        assertArrayEquals(new int[]{1, 2, 1, 3, 2}, approachTrack.getPlaneTokens());
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

        assertTrue(approachTrack.getGameOver());
        assertEquals(0, approachTrack.getCurrentPosition());
        assertArrayEquals(new int[]{1, 2, 1, 3, 2}, approachTrack.getPlaneTokens());
        assertEquals(1, engines.getApproachTrackMove());
    }

    @Test
    void testMoveForwardWhenCurrentPositionEqualsPlaneTokensLengthAfterMoveMock() {
        approachTrack.setPlaneTokens(new int[]{0, 1});
        approachTrack.setCurrentPosition(0);
        when(mockEngines.getApproachTrackMove()).thenReturn(2);

        approachTrack.moveForward(mockEngines);

        verify(mockEngines).getApproachTrackMove();
        assertTrue(approachTrack.getGameOver());
        assertEquals(0, approachTrack.getCurrentPosition());
        assertEquals(0, approachTrack.getLastTrackNum());
        assertTrue(approachTrack.getLastTrack());
        assertArrayEquals(new int[]{1}, approachTrack.getPlaneTokens());
    }

    // Integration test
    @Test
    void testMoveForwardWhenCurrentPositionEqualsPlaneTokensLengthAfterMove() {
        approachTrack.setPlaneTokens(new int[]{0, 1});
        approachTrack.setCurrentPosition(0);
        engines.setPilotSlot(5);
        engines.setCopilotSlot(5);

        engines.countDiceSum();
        approachTrack.moveForward(engines);

        assertTrue(approachTrack.getGameOver());
        assertEquals(0, approachTrack.getCurrentPosition());
        assertEquals(0, approachTrack.getLastTrackNum());
        assertTrue(approachTrack.getLastTrack());
        assertArrayEquals(new int[]{1}, approachTrack.getPlaneTokens());
        assertEquals(2, engines.getApproachTrackMove());
    }

    @Test
    void testLastTrackNumUpdateAfterMovementMock() {
        when(mockEngines.getApproachTrackMove()).thenReturn(2);
        approachTrack.setPlaneTokens(new int[]{0, 0, 1, 2, 1});

        approachTrack.moveForward(mockEngines);

        assertEquals(2, approachTrack.getLastTrackNum());
    }

    // Integration test
    @Test
    void testLastTrackNumUpdateAfterMovement() {
        approachTrack.setPlaneTokens(new int[]{0, 0, 1, 2, 1});
        engines.setPilotSlot(5);
        engines.setCopilotSlot(5);

        engines.countDiceSum();
        approachTrack.moveForward(engines);

        assertEquals(2, approachTrack.getLastTrackNum());
        assertEquals(0, approachTrack.getCurrentPosition());
        assertEquals(2, engines.getApproachTrackMove());
    }

    @Test
    void testIsLastTrackCalledAfterMoveForwardMock() {
        when(mockEngines.getApproachTrackMove()).thenReturn(1);
        approachTrack.setPlaneTokens(new int[]{0, 1});

        approachTrack.moveForward(mockEngines);

        assertTrue(approachTrack.getLastTrack());
        assertEquals(1, approachTrack.getPlaneTokens().length);
        assertEquals(1, approachTrack.getPlaneTokens()[0]);
    }

    // Integration Test
    @Test
    void testIsLastTrackCalledAfterMoveForward() {
        approachTrack.setPlaneTokens(new int[]{0, 1});
        engines.setPilotSlot(3);
        engines.setCopilotSlot(3);

        engines.countDiceSum();
        approachTrack.moveForward(engines);

        assertTrue(approachTrack.getLastTrack());
        assertEquals(1, approachTrack.getPlaneTokens().length);
        assertEquals(1, approachTrack.getPlaneTokens()[0]);
        assertEquals(0, approachTrack.getCurrentPosition());
        assertEquals(1, engines.getApproachTrackMove());
    }

    @Test
    void testResetApproachTrackAfterGameOver() {
        approachTrack.setCurrentPosition(5);
        approachTrack.setLastTrack(true);
        approachTrack.setLastTrackNum(0);
        approachTrack.setPlaneTokens(new int[]{3});
        approachTrack.setGameOver(true);

        approachTrack.resetApproachTrack();

        assertEquals(0, approachTrack.getCurrentPosition());
        assertFalse(approachTrack.getLastTrack());
        assertEquals(6, approachTrack.getLastTrackNum());
        assertArrayEquals(new int[]{0, 0, 1, 2, 1, 3, 2}, approachTrack.getPlaneTokens());
        assertFalse(approachTrack.getGameOver());
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
