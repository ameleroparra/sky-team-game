package com.espabila.skyteam.model;

public class LandGear {
    private int[][] switches;
    private boolean[] activated;

    public LandGear(Integer value){
        this.switches = new int[][]{{1,2},{3,4},{5,6}};
        this.activated = new boolean[3];
    }

    public boolean activateFlap(Pilot pilot, int gearIndex, int value){
        //check if the value used by the player match the necessary ones to activate the flap
        for (int check : switches[gearIndex]){
            if (check == value){
                activated[gearIndex] = true;
                pilot.removeDice(value);
                return true;
            }
        }

        return false;
    }

    public boolean allActivated(){
        for (int i = 0; i < 3; i++){
            if (!activated[i]){
                return false;
            }
        }
        return true;
    }



}
