package com.espabila.skyteam.model;

import java.util.Random;

public class Dice {
    private int value;
    private Random rand;
    private Boolean placed;

    public Dice() {
        rand = new Random();
        roll();
        this.placed = false;
    }

    //choose a random value from 1 to 6 for the dice
    public void roll(){
        value = rand.nextInt(6) + 1;
   }

   public int getValue() {
        return value;
   }

   public Boolean isPlaced(){
        return placed;
   }

}
