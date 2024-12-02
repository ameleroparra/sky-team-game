package com.espabila.skyteam.model;

import com.sun.org.apache.xpath.internal.operations.Bool;

public class AltitudeTrack {
    private int currentRound;
    private Boolean pilotTurn;
    private Boolean lastRound;

    public AltitudeTrack(){
        this.pilotTurn = true;
        this.currentRound = 0;
        this.lastRound = false;
    }

    public void newRound(){
        if(currentRound < 6){
            currentRound += 1;
            pilotTurn = !pilotTurn;
        }
    }

    public void islastRound(){
        if(currentRound == 6){
            lastRound = true;
            pilotTurn = !pilotTurn;
        }
    }
}
