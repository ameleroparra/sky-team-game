package com.espabila.skyteam.model;

public class LandGear {
    private boolean[] activated;
    private int[] firstRequired;
    private int[] secondRequired;
    private int[] thirdRequired;

    public boolean[] getActivated() {
        return activated;
    }

    public void setActivated(boolean[] activated) {
        this.activated = activated;
    }

    public int[] getFirstRequired() {
        return firstRequired;
    }

    public void setFirstRequired(int[] firstRequired) {
        this.firstRequired = firstRequired;
    }

    public int[] getSecondRequired() {
        return secondRequired;
    }

    public void setSecondRequired(int[] secondRequired) {
        this.secondRequired = secondRequired;
    }

    public int[] getThirdRequired() {
        return thirdRequired;
    }

    public void setThirdRequired(int[] thirdRequired) {
        this.thirdRequired = thirdRequired;
    }

    public LandGear(){
        this.activated = new boolean[3];
        this.firstRequired = new int[]{1, 2};
        this.secondRequired = new int[]{3, 4};
        this.thirdRequired = new int[]{5, 6};
    }

    public boolean activateLandGear(Pilot pilot, int gearIndex, int value){
        //check if the value used by the player match the necessary ones to activate the flap
        if (gearIndex == 0) {
            for (int element : firstRequired) {
                if (element == value) {
                    activated[gearIndex] = true;
                    pilot.removeDice(value);
                    return true;
                }
            }
            return false;

        }

        else if (gearIndex == 1) {
            for (int element : secondRequired) {
                if (element == value) {
                    activated[gearIndex] = true;
                    pilot.removeDice(value);
                    return true;
                }
            }
            return false;
        }

        else if (gearIndex == 2) {
            for (int element : thirdRequired) {
                if (element == value) {
                    activated[gearIndex] = true;
                    pilot.removeDice(value);
                    return true;
                }
            }
            return false;
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
