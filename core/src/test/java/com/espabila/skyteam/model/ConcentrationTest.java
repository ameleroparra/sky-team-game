package com.espabila.skyteam.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConcentrationTest {

    Concentration concentration;
    Player player;

    @Mock
    private Player mockPlayer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        concentration = new Concentration();
        player = new Player() {
        };
    }

    @Test
    void testPlaceDiceInUnactivatedSlotMock() {
        int diceValue = 4;
        int slotIndex = 1;

        assertFalse(concentration.isActivated(slotIndex));

        concentration.placeDice(mockPlayer, diceValue, slotIndex);

        assertTrue(concentration.isActivated(slotIndex));
        verify(mockPlayer).removeDice(diceValue);
    }

    // Integration test
    @Test
    void testPlaceDiceInUnactivatedSlot() {
        Player player = new Player() {};
        int diceValue = 4;
        int slotIndex = 1;

        assertFalse(concentration.isActivated(slotIndex));
        concentration.placeDice(player, diceValue, slotIndex);
        assertTrue(concentration.isActivated(slotIndex));
    }

    @Test
    void testDoNotPlaceDiceInActivatedSlotMock() {
        int diceValue = 4;
        int slotIndex = 1;

        // Setup
        concentration.setActivated(slotIndex, true);

        // Action
        concentration.placeDice(mockPlayer, diceValue, slotIndex);

        // Verify
        assertTrue(concentration.isActivated(slotIndex));
        verify(mockPlayer, never()).removeDice(diceValue);
        verifyNoMoreInteractions(mockPlayer);
    }

    @Test
    void testDoNotPlaceDiceInActivatedSlot() {
        Player player = new Player() {};
        int diceValue = 4;
        int slotIndex = 1;

        concentration.setActivated(slotIndex, true);
        List<Integer> initialDiceList = new ArrayList<>(Arrays.asList(2, 4, 5));
        player.setDiceList(initialDiceList);

        concentration.placeDice(player, diceValue, slotIndex);

        assertTrue(concentration.isActivated(slotIndex));
        assertEquals(initialDiceList, player.getDiceList());
    }

    @Test
    void testAllActivatedReturnsTrueWhenAllSlotsActivated() {
        concentration.setActivated(0, true);
        concentration.setActivated(1, true);
        concentration.setActivated(2, true);

        assertTrue(concentration.allActivated());

        concentration.setActivated(1, false);

        assertFalse(concentration.allActivated());
    }

    @Test
    void testPlaceDiceInUnactivatedSlotZeroMock() {
        Player mockPlayer = mock(Player.class);
        int diceValue = 3;
        int slotIndex = 0;

        assertFalse(concentration.isActivated(slotIndex));

        concentration.placeDice(mockPlayer, diceValue, slotIndex);

        assertTrue(concentration.isActivated(slotIndex));
        verify(mockPlayer).removeDice(diceValue);
    }

    @Test
    void testPlaceDiceInUnactivatedSlotOneMock() {
        Player mockPlayer = mock(Player.class);
        int diceValue = 4;
        int slotIndex = 1;

        assertFalse(concentration.isActivated(slotIndex));

        concentration.placeDice(mockPlayer, diceValue, slotIndex);

        assertTrue(concentration.isActivated(slotIndex));
        verify(mockPlayer).removeDice(diceValue);
    }

    @Test
    void testPlaceDiceInUnactivatedSlotTwoMock() {
        Player mockPlayer = mock(Player.class);
        int diceValue = 5;
        int slotIndex = 2;

        assertFalse(concentration.isActivated(slotIndex));

        concentration.placeDice(mockPlayer, diceValue, slotIndex);

        assertTrue(concentration.isActivated(slotIndex));
        verify(mockPlayer).removeDice(diceValue);
    }

    @Test
    void testPlaceDiceWhenAllSlotsActivated() {
        Player mockPlayer = mock(Player.class);
        int diceValue = 3;

        // Activate all slots
        concentration.setActivated(0, true);
        concentration.setActivated(1, true);
        concentration.setActivated(2, true);

        // Try to place dice in each slot
        concentration.placeDice(mockPlayer, diceValue, 0);
        concentration.placeDice(mockPlayer, diceValue, 1);
        concentration.placeDice(mockPlayer, diceValue, 2);

        // Verify that removeDice was never called
        verify(mockPlayer, never()).removeDice(anyInt());

        // Verify that all slots are still activated
        assertTrue(concentration.isActivated(0));
        assertTrue(concentration.isActivated(1));
        assertTrue(concentration.isActivated(2));
    }

    @Test
    void testUseDownDoesNotModifyDiceValueWhenItIsOne() {
        Concentration concentration = new Concentration();
        Player player = new Player() {};
        List<Integer> diceList = new ArrayList<>(Arrays.asList(1, 4, 5));
        player.setDiceList(diceList);

        int initialDiceValue = 1;
        int diceIndex = 0;

        concentration.useDown(player, initialDiceValue, diceIndex);

        assertEquals(1, player.getDiceList().get(diceIndex));
    }

    @Test
    void testUseDownDecreasesDiceValueByOne() {
        Concentration concentration = new Concentration();
        Player player = new Player() {};
        List<Integer> diceList = new ArrayList<>(Arrays.asList(3, 4, 5));
        player.setDiceList(diceList);

        int initialDiceValue = 3;
        int diceIndex = 0;

        concentration.useDown(player, initialDiceValue, diceIndex);

        assertEquals(2, player.getDiceList().get(diceIndex));
    }

    @Test
    void testUseUpDoesNotModifyDiceValueWhenItIsSix() {
        Concentration concentration = new Concentration();
        Player player = new Player() {};
        List<Integer> diceList = new ArrayList<>(Arrays.asList(6, 4, 5));
        player.setDiceList(diceList);

        int initialDiceValue = 6;
        int diceIndex = 0;

        concentration.useUp(player, initialDiceValue, diceIndex);

        assertEquals(6, player.getDiceList().get(diceIndex));
    }

    @Test
    void testUseUpIncreasesDiceValueByOne() {
        Concentration concentration = new Concentration();
        Player player = new Player() {};
        List<Integer> diceList = new ArrayList<>(Arrays.asList(3, 4, 5));
        player.setDiceList(diceList);

        int initialDiceValue = 3;
        int diceIndex = 0;

        concentration.useUp(player, initialDiceValue, diceIndex);

        assertEquals(4, player.getDiceList().get(diceIndex));
    }

    @Test
    void testResetConcentrationSetsAllActivatedSlotsToFalse() {
        Concentration concentration = new Concentration();

        // Activate all slots
        concentration.setActivated(0, true);
        concentration.setActivated(1, true);
        concentration.setActivated(2, true);
        concentration.setPlusOne(true);
        concentration.setMinusOne(true);

        // Reset concentration
        concentration.resetConcentration();

        // Assert that all slots are deactivated
        assertFalse(concentration.isActivated(0));
        assertFalse(concentration.isActivated(1));
        assertFalse(concentration.isActivated(2));
        assertFalse(concentration.isPlusOne());
        assertFalse(concentration.isMinusOne());
    }

}
