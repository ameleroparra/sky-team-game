package com.espabila.skyteam.model;


import java.util.List;

public abstract class Player {
    private AltitudeTrack pilotTurn;
    private List<Dice> diceList;

    public Player(){
    }

    public void rollDice(){
        for(int i = 0; i < 4; i++){
            this.diceList.add(new Dice());
        }
    }

}
