package com.espabila.skyteam.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RerollTest {

    Reroll reroll;
    Player player;

    @BeforeEach
    void setUp() {
        reroll = new Reroll();
        player = new Player() {
        };
    }

    @Test
    @DisplayName("Should not modify player dice list when reroll available is false"  )
    void shouldNotModifyPlayerDiceListWhenRerollAvailableIsFalse() {
        player.rollDices();

        List<Integer> originalDiceList = player.getDiceList();
        reroll.useReroll(player, 3);

        assertEquals(originalDiceList, player.getDiceList());
    }

    @Test
    void shouldRerollCorrectDicesWhenUseRerollCalledWithValidValue() {
        reroll.setRerollAvailable(true);

        List<Integer> originalDiceList = new ArrayList<>(List.of(1, 2, 3, 4));
        player.setDiceList(new ArrayList<>(originalDiceList));

        reroll.useReroll(player, 3);
        reroll.useReroll(player, 1);

        List<Integer> newDiceList = player.getDiceList();

        assertEquals(originalDiceList.size(), newDiceList.size());

        assertNotEquals(originalDiceList, newDiceList);

        assertEquals(originalDiceList.get(1), newDiceList.get(1));
        assertEquals(originalDiceList.get(3), newDiceList.get(3));
    }
}
