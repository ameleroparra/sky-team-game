package com.espabila.skyteam.model;

public class Brakes {
    private int[] requiredValue;
    private boolean[] activated;

    public Brakes() {
        this.requiredValue = new int[]{2,4,6};
        this.activated = new boolean[3];
    }

    public boolean activateBrakes(int brakeIndex, Dice dice){

        int diceValue = dice.getValue();

        // check if previous flap is activated in case player want to activate something that is not the first flap
        if (brakeIndex > 0 && !activated[brakeIndex - 1]){
            //here write something to remember the player that flaps go in order
            return false;
        }
        //check if the value used by the player match the necessary ones to activate the flap
        else if (diceValue == requiredValue[brakeIndex]){
            activated[brakeIndex] = true;
            return true;
        }

        return false;
    }
}
