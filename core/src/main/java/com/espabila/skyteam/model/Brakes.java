package com.espabila.skyteam.model;

public class Brakes {
    private int[] requiredValue;
    private boolean[] activated;

    public Brakes() {
        this.requiredValue = new int[]{2,4,6};
        this.activated = new boolean[3];
    }

    public boolean activateBrakes(Pilot pilot, int brakeSlot, int diceValue){
        if (brakeSlot > 0 && !activated[brakeSlot - 1]){
            return false;
        }
        else if (diceValue == requiredValue[brakeSlot]){
            activated[brakeSlot] = true;
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
}
