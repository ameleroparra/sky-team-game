package com.espabila.skyteam.model;

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

    public void checkLastRound(){
        if(currentRound == 6){
            lastRound = true;
            pilotTurn = !pilotTurn;
        }
    }

    public void rerollAvailable(Reroll reroll){
        if(currentRound == 0 || currentRound==4){
            reroll.setRerollAvailable(true);
        }
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public Boolean isPilotTurn() {
        return pilotTurn;
    }

    public Boolean isLastRound() {
        return lastRound;
    }
}
