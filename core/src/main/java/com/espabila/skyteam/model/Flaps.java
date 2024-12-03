package com.espabila.skyteam.model;

public class Flaps {

    private int[][] requiredValue;
    private boolean[] activated;

    public Flaps() {
        this.requiredValue = new int[][]{{1,2},{2,3},{4,5},{5,6}}; //Flaps and its needed values
        this.activated = new boolean[4]; //flaps deactivated
    }

    public boolean activateFlap(int flapIndex, Dice dice){

        // check if previous flap is activated in case player want to activate something that is not the first flap
        if (flapIndex > 0 && !activated[flapIndex - 1]){
            //here write something to remember the player that flaps go in order
            return false;
        }

        int diceValue = dice.getValue();

        //check if the value used by the player match the necessary ones to activate the flap
        for (int check : requiredValue[flapIndex]){
            if (check == diceValue){
                activated[flapIndex] = true;
                return true;
            }
            //here we can tell the player that the dice does not match the necessary value
        }

        return false;
    }

    public boolean allActivated(){
        for (int i = 0; i < 4; i++){
            if (!activated[i]){
                return false;
            }
        }

        return true;
    }


}
