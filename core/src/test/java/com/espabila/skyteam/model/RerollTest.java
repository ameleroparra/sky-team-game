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
    void testDNotModifyPlayerDiceListWhenRerollAvailableIsFalse() {
        player.rollDices();

        List<Integer> originalDiceList = player.getDiceList();
        reroll.useReroll(player, 3);

        assertEquals(originalDiceList, player.getDiceList());
    }
}
