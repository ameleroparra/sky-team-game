package com.espabila.skyteam.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiceTest {
    @Test
    void testRollReturnsBetweenOneAndSix() {
        Dice dice = new Dice();
        for (int i = 0; i < 1000; i++) {
            int result = dice.roll();
            assertTrue(result >= 1 && result <= 6, "Rolled value should be between 1 and 6");
        }
    }

}
