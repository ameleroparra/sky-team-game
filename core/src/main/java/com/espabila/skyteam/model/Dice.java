package com.espabila.skyteam.model;

import java.util.Random;

public class Dice {
    private int value;
    private Random randomNum;
    private Boolean placed;

    public Dice() {
        randomNum = new Random();
        roll();
        this.placed = false;
    }

    //choose a random value from 1 to 6 for the dice
    public void roll(){
        value = randomNum.nextInt(6) + 1;
   }

   public int getValue() {
        return value;
   }

   public Boolean isPlaced(){
        return placed;
   }

}
