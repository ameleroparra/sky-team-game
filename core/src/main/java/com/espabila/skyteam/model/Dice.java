package com.espabila.skyteam.model;

import java.util.Random;

public class Dice {
    private int value;
    private Random randomNum;

    public Dice() {
        randomNum = new Random();
    }

    //choose a random value from 1 to 6 for the dice
    public int roll(){
        return value = randomNum.nextInt(6) + 1;
   }

   public int getValue() {
        return value;
   }

   // why randomNum?
}
