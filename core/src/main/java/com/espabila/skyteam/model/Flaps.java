package com.espabila.skyteam.model;

public class Flaps {

    private int[][] requiredValue;
    private boolean[] activated;

    public Flaps() {
        this.requiredValue = new int[][]{{1,2},{2,3},{4,5},{5,6}}; //Flaps and its needed values
        this.activated = new boolean[4]; //flaps deactivated
    }

    public boolean activateFlap(CoPilot coPilot, int flapIndex, int value){
        if (flapIndex > 0 && !activated[flapIndex - 1]){
            return false;
        }

        for (int check : requiredValue[flapIndex]){
            if (check == value){
                activated[flapIndex] = true;
                coPilot.removeDice(value);
                return true;
            }
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
