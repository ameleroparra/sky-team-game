package com.espabila.skyteam.model;
import org.mockito.Mockito;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
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
    void testRerollAvailableWhenCurrentRoundIsZeroMockito() {
        Reroll mockReroll = Mockito.mock(Reroll.class);
        when(mockReroll.getRerollAvailable()).thenReturn(false);

        altitudeTrack.rerollAvailable(mockReroll);

        verify(mockReroll).setRerollAvailable(true);
    }

    // Integration test
    @Test
    void testRerollAvailableWhenCurrentRoundIsZero() {
        Reroll reroll = new Reroll();
        reroll.setRerollAvailable(false);

        altitudeTrack.rerollAvailable(reroll);

        assertTrue(reroll.getRerollAvailable());
    }

    @Test
    void testRerollAvailableWhenCurrentRoundIsFourMockito() {
        Reroll mockReroll = Mockito.mock(Reroll.class);
        when(mockReroll.getRerollAvailable()).thenReturn(false);

        altitudeTrack.setCurrentRound(4);

        altitudeTrack.rerollAvailable(mockReroll);

        verify(mockReroll).setRerollAvailable(true);
    }

    // Integration test
    @Test
    void testRerollAvailableWhenCurrentRoundIsFour() {
        Reroll reroll = new Reroll();
        reroll.setRerollAvailable(false);

        altitudeTrack.setCurrentRound(4);

        altitudeTrack.rerollAvailable(reroll);

        assertTrue(reroll.getRerollAvailable());
    }

    @Test
    void testRerollAvailableWhenCurrentRoundIsNotZeroOrFourMockito() {
        Reroll mockReroll = Mockito.mock(Reroll.class);

        altitudeTrack.setCurrentRound(2);
        altitudeTrack.rerollAvailable(mockReroll);

        verify(mockReroll, never()).setRerollAvailable(anyBoolean());

        verify(mockReroll, never()).getRerollAvailable();

        verifyNoMoreInteractions(mockReroll);
    }

    // Integration test
    @Test
    void testRerollAvailableWhenCurrentRoundIsNotZeroOrFour() {
        Reroll reroll = new Reroll();
        reroll.setRerollAvailable(false);

        altitudeTrack.setCurrentRound(2);

        altitudeTrack.rerollAvailable(reroll);

        assertFalse(reroll.getRerollAvailable());
    }

    @Test
    void testResetAltitudeTrack() {
        altitudeTrack.setCurrentRound(5);
        altitudeTrack.setPilotTurn(false);
        altitudeTrack.setLastRound(true);

        altitudeTrack.resetAltitudeTrack();

        assertTrue(altitudeTrack.isPilotTurn());
        assertEquals(0, altitudeTrack.getCurrentRound());
        assertFalse(altitudeTrack.isLastRound());
    }
}
