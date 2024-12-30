package com.espabila.skyteam.model;

public class Concentration {
    private int tokenLimit = 3;
    private int tokenAmount = 0;
    private int slots;

    public Concentration(){
        this.slots = 0;
    }

    public void generateToken(){
        if (tokenAmount <= tokenLimit){
            // code to take a dice


            tokenAmount++;
        }
        else {
            //return warning message "no more coffee space"
        }
    }

    public void placeDice(Player player, int diceValue){

    }

    public int useTokenPositive(int diceValue) {
        if (tokenAmount != 0) {
            diceValue = diceValue + 1;
            tokenAmount--;
        }
        return diceValue;
    }

    public int useTokenNegative(int diceValue) {
        if (tokenAmount != 0) {
            diceValue = diceValue - 1;
            tokenAmount--;
        }
        return diceValue;
    }
}
