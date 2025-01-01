package com.espabila.skyteam.controller;

import com.espabila.skyteam.model.CoPilot;
import com.espabila.skyteam.model.Pilot;
import com.espabila.skyteam.model.Player;

public class GameController {
    private Player pilot;
    private Player coPilot;

    //constructor
    public GameController(){

    }

    public void startNewGame(){

        // initialize players and roll dices
        pilot = new Pilot();
        coPilot = new CoPilot();
        pilot.rollDices();
        coPilot.rollDices();
    }

    //getters
    public Player getPilot() {
        return pilot;
    }

    public Player getCoPilot() {
        return coPilot;
    }
}
