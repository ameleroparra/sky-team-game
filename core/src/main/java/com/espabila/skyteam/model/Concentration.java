package com.espabila.skyteam.model;

import java.util.ArrayList;
import java.util.List;

public class Concentration {
    private int firstSlot;
    private int secondSlot;
    private int thirdSlot;
    private boolean[] activated;
    private boolean plusOne; //should be changed in controller to work here
    private boolean minusOne; //should be changed in controller to work here

    public Concentration(){
        this.firstSlot = 0;
        this.secondSlot = 0;
        this.thirdSlot = 0;
        this.activated = new boolean[3];
        this.plusOne = false;
        this.minusOne = false;
    }

    public void setPlusOne(boolean plusOne) {
        this.plusOne = plusOne;
    }

    public void setMinusOne(boolean minusOne) {
        this.minusOne = minusOne;
    }

    public void placeDice(Player player, int diceValue, int slotIndex){
        if (slotIndex == 0 && !activated[0]){
            activated[0] = true;
            System.out.println("Coffee placed on Slot 1");
            player.removeDice(diceValue);
        }
        else if (slotIndex == 1 && !activated[1]){
            activated[1] = true;
            System.out.println("Coffee placed on Slot 2");
            player.removeDice(diceValue);
        }
        else if (slotIndex == 2 && !activated[2]){
            activated[2] = true;
            System.out.println("Coffee placed on Slot 3");
            player.removeDice(diceValue);
        }
        else {
            System.out.println("Coffee is already available on this slot.");
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

    public void useUp(Player player, int SlotIndex, int diceValue, int diceIndex){
        if (diceValue != 6) {
            diceValue = diceValue + 1;
        }
    }

    public void useDown(Player player, int SlotIndex, int diceValue, int diceIndex){
        if (diceValue != 1) {
            diceValue = diceValue - 1;
        }
    }

    public void useToken(Player player, int slotIndex, int diceValue, int diceIndex){

        int counter = 0;
        for (int i = 0; i < 3; i++){
            if (activated[i]){
                counter++;
            }
        }
        if (counter == 0){
            System.out.println("No coffee tokens available.");
        }

        else if(plusOne &&!minusOne){
            if (diceValue == 6){
                System.out.println("Cannot add + 1 to dice value. Choose substracting 1 or change the dice");
            }
            else if(slotIndex == 0){
                activated[0] = false;
                player.getDiceList().set(diceIndex, diceValue + 1);
            }
            else if(slotIndex == 1){
                activated[1] = false;
                player.getDiceList().set(diceIndex, diceValue + 1);
            }
            else if(slotIndex == 2){
                activated[2] = false;
                player.getDiceList().set(diceIndex, diceValue + 1);
            }
        }

        else if(minusOne &&!plusOne){
            if(diceValue == 1){
                System.out.println("Cannot substract 1 from the dice value. Choose adding 1 or change the dice");
            }
            else if(slotIndex == 0){
                activated[0] = false;
                player.getDiceList().set(diceIndex, diceValue - 1);
            }
            else if(slotIndex == 1){
                activated[1] = false;
                player.getDiceList().set(diceIndex, diceValue - 1);
            }
            else if(slotIndex == 2){
                activated[2] = false;
                player.getDiceList().set(diceIndex, diceValue - 1);
            }
        }
    }

    public void resetConcentration(){
        for (int i = 0; i < activated.length; i++) {
            activated[i] = false;
        }
        plusOne = false;
        minusOne = false;
    }
}
