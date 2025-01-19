package com.espabila.skyteam.model;


import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    private List<Integer> diceList;

    public List<Integer> getDiceList() {
        return diceList;
    }

    public void setDiceList(List<Integer> diceList) {
        this.diceList = diceList;
    }

    public Player(){
        diceList =  new ArrayList<>();
    }

    public void rollDice(){
        if(!diceList.isEmpty()){
            resetDiceList();
        }
        for(int i = 0; i < 4; i++){
            Dice dice = new Dice();
            diceList.add(dice.roll());
        }
    }

    public void removeDice(int value){
        diceList.remove((Integer) value);
    }

    public void resetDiceList(){
        diceList.clear();
    }



}
