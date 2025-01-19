package com.espabila.skyteam.model;

import java.util.ArrayList;
import java.util.List;

public class Concentration {
    private int firstSlot;
    private int secondSlot;
    private int thirdSlot;
    private boolean[] activated;
    public int getFirstSlot() {
        return firstSlot;
    }

    public void setFirstSlot(int firstSlot) {
        this.firstSlot = firstSlot;
    }

    public int getSecondSlot() {
        return secondSlot;
    }

    public void setSecondSlot(int secondSlot) {
        this.secondSlot = secondSlot;
    }

    public int getThirdSlot() {
        return thirdSlot;
    }

    public void setThirdSlot(int thirdSlot) {
        this.thirdSlot = thirdSlot;
    }

    public boolean[] getActivated() {
        return activated;
    }

    public void setActivated(boolean[] activated) {
        this.activated = activated;
    }


    public Concentration(){
        this.firstSlot = 0;
        this.secondSlot = 0;
        this.thirdSlot = 0;
        this.activated = new boolean[3];
    }

    public void setActivated(int slotIndex, boolean activated) {
        this.activated[slotIndex] = activated;
    }

    public void placeDice(Player player, int diceValue, int slotIndex){
        if (slotIndex == 0 && !activated[0]){
            activated[0] = true;
            player.removeDice(diceValue);
        }
        else if (slotIndex == 1 && !activated[1]){
            activated[1] = true;
            player.removeDice(diceValue);
        }
        else if (slotIndex == 2 && !activated[2]){
            activated[2] = true;
            player.removeDice(diceValue);
        }
    }

    public boolean isActivated(int slotIndex){
        return activated[slotIndex];
    }


    public boolean allActivated(){
        for (int i = 0; i < 3; i++){
            if (!activated[i]){
                return false;
            }
        }
        return true;
    }

    public void useDown(Player player, int diceValue, int diceIndex){
        if (diceValue != 1) {
            int newValue = diceValue - 1;
            player.getDiceList().set(diceIndex, newValue);
        }
    }

    public void useUp(Player player, int diceValue, int diceIndex){
        if (diceValue != 6) {
            int newValue = diceValue + 1;
            player.getDiceList().set(diceIndex, newValue);
        }
    }


    public void resetConcentration(){
        for (int i = 0; i < activated.length; i++) {
            activated[i] = false;
        }
    }
}
