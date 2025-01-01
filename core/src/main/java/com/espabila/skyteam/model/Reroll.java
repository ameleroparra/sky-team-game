package com.espabila.skyteam.model;

import java.util.ArrayList;
import java.util.List;

public class Reroll {
    private Boolean rerollAvailable;
    private List<Integer> rerolledDices;

    public Reroll(){
        this.rerollAvailable = false;
        this.rerolledDices = new ArrayList<>();
    }

    public Boolean isRerollAvailable() {
        return rerollAvailable;
    }

    public void setRerollAvailable(Boolean rerollAvailable) {
        this.rerollAvailable = rerollAvailable;
    }

    public void useReroll(Player player, int value){ //player gives value of a dice that he wants to reroll
        if(rerollAvailable){
            rerolledDices = player.getDiceList();
            int index = player.getDiceList().indexOf(value);
            Dice dice = new Dice();
            int newValue = dice.roll();
            rerolledDices.set(index, newValue);
            player.setDiceList(rerolledDices);
        }
    }
}
