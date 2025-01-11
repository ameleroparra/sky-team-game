package com.espabila.skyteam.model;


public class Brakes {
    private int[] requiredValue;
    private boolean[] activated;
    private int brakesSum;

    public Brakes() {
        this.requiredValue = new int[]{2,4,6};
        this.activated = new boolean[3];
        this.brakesSum = 0;
    }

    public int[] getRequiredValue() {
        return requiredValue;
    }

    public void setRequiredValue(int[] requiredValue) {
        this.requiredValue = requiredValue;
    }

    public boolean[] getActivated() {
        return activated;
    }

    public void setActivated(boolean[] activated) {
        this.activated = activated;
    }

    public int getBrakesSum() {
        return brakesSum;
    }

    public void setBrakesSum(int brakesSum) {
        this.brakesSum = brakesSum;
    }

    public boolean activateBrakes(Pilot pilot, int brakeSlot, int diceValue){
        if (brakeSlot > 0 && !activated[brakeSlot - 1]){
            return false;
        }
        else if (diceValue == requiredValue[brakeSlot]){
            activated[brakeSlot] = true;
            brakesSum = diceValue;
            pilot.removeDice(diceValue);
            return true;
        }
        return false;
    }

    public void resetBrakes(){
        for (int i = 0; i < activated.length; i++){
            activated[i] = false;
        }
    }

    // Getter for checking brake status
    public boolean isBrakeActivated(int brakeSlot) {
        return activated[brakeSlot];
    }
}
