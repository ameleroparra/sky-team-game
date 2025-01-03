package com.espabila.skyteam.model;

public class LandGear {
    private int[][] switches;
    private boolean[] activated;

    public LandGear(){
        this.switches = new int[][]{{1,2},{3,4},{5,6}};
        this.activated = new boolean[3];
    }

    public boolean activateEngine(Pilot pilot, int gearIndex, int value){
        //check if the value used by the player match the necessary ones to activate the flap
        for (int check : switches[gearIndex]){
            if (check == value){
                if(!activated[gearIndex]){
                    activated[gearIndex] = true;
                    pilot.removeDice(value);
                    return true;
                }
                else{
                    System.out.println("This Land Gear is already activated.");
                }
            }
        }

        return false;
    }

    public boolean isActivated(int gearIndex){
        return activated[gearIndex];
    }

    public boolean allActivated(){
        for (int i = 0; i < 3; i++){
            if (!activated[i]){
                return false;
            }
        }
        return true;
    }

    public void resetLandGear(){
        for (int i = 0; i < 3; i++){
            activated[i] = false;
        }
    }



}
