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
                if(!activated[flapIndex]){
                    activated[flapIndex] = true;
                    coPilot.removeDice(value);
                    return true;
                }
                else{
                    System.out.println("This Flap is already activated.");
                }
            }
        }
        return false;
    }

    public boolean isActivated(int flapIndex){
        return activated[flapIndex];
    }

    public boolean allActivated(){
        for (int i = 0; i < 4; i++){
            if (!activated[i]){
                return false;
            }
        }
        return true;
    }

    public void resetFlaps(){
        for(int i = 0; i < activated.length; i++){
            activated[i] = false;
        }
    }


}
