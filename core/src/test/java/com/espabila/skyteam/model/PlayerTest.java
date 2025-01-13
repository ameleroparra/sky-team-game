package com.espabila.skyteam.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Player player;

    @BeforeEach
    public void setUp() {
        player = new Player() {
        };
    }

    @Test
    public void testRollFourDiceAndAddValuesToDiceList() {
        player.rollDices();

        List<Integer> diceList = player.getDiceList();
        assertEquals(4, diceList.size());

        System.out.println("Player rolled: " + diceList);

        for (int diceValue : diceList) {
            assertTrue(diceValue >= 1 && diceValue <= 6);
        }
    }

    @Test
    public void testRemoveDiceValueFromList() {
        List<Integer> initialDiceList = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        player.setDiceList(initialDiceList);

        player.removeDice(3);

        List<Integer> expectedDiceList = Arrays.asList(1, 2, 4);
        assertEquals(expectedDiceList, player.getDiceList());
        assertFalse(player.getDiceList().contains(3));
    }

}
