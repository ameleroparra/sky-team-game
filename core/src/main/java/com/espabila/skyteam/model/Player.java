package com.espabila.skyteam.model;


import java.util.List;

public class Player {
    private String name;
    private Boolean isTurn;
    private List<Dice> diceList;

    public Boolean getTurn() {
        return isTurn;
    }

    public void setTurn(Boolean turn) {
        isTurn = turn;
    }

    public List<Dice> getDiceList() {
        return diceList;
    }

    public void setDiceList(List<Dice> diceList) {
        this.diceList = diceList;
    }
}
