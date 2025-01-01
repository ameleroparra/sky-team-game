package com.espabila.skyteam.model;

import java.util.ArrayList;
import java.util.List;

public class Concentration {
    private int firstSlot;
    private int secondSlot;
    private int thirdSlot;
    private boolean coffeeOnSlotOne;
    private boolean coffeeOnSlotTwo;
    private boolean coffeeOnSlotThree;
    private boolean plusOne; //should be changed in controller to work here
    private boolean minusOne; //should be changed in controller to work here

    public Concentration(){
        this.firstSlot = 0;
        this.secondSlot = 0;
        this.thirdSlot = 0;
        this.coffeeOnSlotOne = false;
        this.coffeeOnSlotTwo = false;
        this.coffeeOnSlotThree = false;
        this.plusOne = false;
        this.minusOne = false;
    }

    public boolean isCoffeeOnSlotOne() {
        return coffeeOnSlotOne;
    }

    public void setCoffeeOnSlotOne(boolean coffeeOnSlotOne) {
        this.coffeeOnSlotOne = coffeeOnSlotOne;
    }

    public boolean isCoffeeOnSlotTwo() {
        return coffeeOnSlotTwo;
    }

    public void setCoffeeOnSlotTwo(boolean coffeeOnSlotTwo) {
        this.coffeeOnSlotTwo = coffeeOnSlotTwo;
    }

    public boolean isCoffeeOnSlotThree() {
        return coffeeOnSlotThree;
    }

    public void setCoffeeOnSlotThree(boolean coffeeOnSlotThree) {
        this.coffeeOnSlotThree = coffeeOnSlotThree;
    }

    public void setPlusOne(boolean plusOne) {
        this.plusOne = plusOne;
    }

    public void setMinusOne(boolean minusOne) {
        this.minusOne = minusOne;
    }

    public void placeDice(Player player, int diceValue, int slotNumber){
        if (slotNumber == 1 && !coffeeOnSlotOne){
            coffeeOnSlotOne = true;
            player.removeDice(diceValue);
        }
        else if (slotNumber == 2 && !coffeeOnSlotTwo){
            coffeeOnSlotTwo = true;
            player.removeDice(diceValue);
        }
        else if (slotNumber == 3 && !coffeeOnSlotThree){
            coffeeOnSlotThree = true;
            player.removeDice(diceValue);
        }
        else {
            System.out.println("Coffee is already available on this slot.");
        }
    }

    public void useToken(Player player, int slotNum, int diceValue, int index){
        if(!coffeeOnSlotOne && !coffeeOnSlotTwo && !coffeeOnSlotThree){
            System.out.println("No coffee tokens available.");
        }
        else if(plusOne && !minusOne){
            if(diceValue == 6){
                System.out.println("Cannot add + 1 to dice value. Choose substracting 1 or change the dice");
            }
            else if(slotNum == 1){
                coffeeOnSlotOne = false;
                player.getDiceList().set(index, diceValue + 1);
            }
            else if(slotNum == 2){
                coffeeOnSlotTwo = false;
                player.getDiceList().set(index, diceValue + 1);
            }
            else if(slotNum == 3){
                coffeeOnSlotThree = false;
                player.getDiceList().set(index, diceValue + 1);
            }
        }
        else if(minusOne && !plusOne){
            if(diceValue == 1){
                System.out.println("Cannot substract 1 from the dice value. Choose adding 1 or change the dice");
            }
            else if(slotNum == 1){
                coffeeOnSlotOne = false;
                player.getDiceList().set(index, diceValue - 1);
            }
            else if(slotNum == 2){
                coffeeOnSlotTwo = false;
                player.getDiceList().set(index, diceValue - 1);
            }
            else if(slotNum == 3){
                coffeeOnSlotThree = false;
                player.getDiceList().set(index, diceValue - 1);
            }
        }
        else {
            System.out.println("Cannot add and substract the value at the same time. Choose either adding 1 or substracting 1");
        }
    }
}
