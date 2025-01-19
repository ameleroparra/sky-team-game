package com.espabila.skyteam.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RerollTest {

    Reroll reroll;
    Player player;
    Pilot mockPilot;
    CoPilot mockCopilot;
    Dice mockDice;

    @BeforeEach
    void setUp() {
        reroll = new Reroll();
        player = new Player() {
        };
        mockPilot = mock(Pilot.class);
        mockCopilot = mock(CoPilot.class);
        mockDice = mock(Dice.class);

    }

    @Test
    void testDNotModifyPlayerDiceListWhenRerollAvailableIsFalse() {
        player.rollDices();

        List<Integer> originalDiceList = player.getDiceList();
        reroll.useReroll(player, 3);

        assertEquals(originalDiceList, player.getDiceList());
    }

    @Test
    void testRerollInitialAvailable() {
        assertFalse(reroll.getRerollAvailable());
    }

    @Test
    void testRerollBecomesUnavailableAfterUse() {
        reroll.useReroll(player, 3);
        assertFalse(reroll.getRerollAvailable());
    }

    // mock
    @Test
    void testUseRerollForPilot() {

        reroll.setPilotRerollActive(true);
        List<Integer> originalDiceList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        List<Integer> checkDiceList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        when(mockPilot.getDiceList()).thenReturn(originalDiceList);

        for (int i = 0; i < originalDiceList.size(); i++) {
            reroll.useReroll(mockPilot, i);
        }

        assertNotEquals(originalDiceList, checkDiceList);


    }
    @Test
    void testUseRerollForCoPilot() {
        reroll.setCoPilotRerollActive(true);
        List<Integer> originalDiceList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        List<Integer> checkDiceList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        when(mockCopilot.getDiceList()).thenReturn(originalDiceList);

        for (int i = 0; i < originalDiceList.size(); i++) {
            reroll.useReroll(mockCopilot, i);
        }

        assertNotEquals(originalDiceList, checkDiceList);
    }



}
