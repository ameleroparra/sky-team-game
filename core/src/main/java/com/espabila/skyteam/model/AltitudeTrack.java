package com.espabila.skyteam.model;

public class AltitudeTrack {
    private int currentRound;
    private Boolean pilotTurn;
    private Boolean lastRound;

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

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
        checkLastRound();
    }

    public void checkLastRound(){
        if(currentRound == 6){
            lastRound = true;
            pilotTurn = true;
        }
    }

    public void rerollAvailable(Reroll reroll){
        if(currentRound == 0 || currentRound== 4){
            reroll.setRerollAvailable(true);
            System.out.println("rerollAvailable was called" + currentRound);
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

    public void resetAltitudeTrack(){
        this.pilotTurn = true;
        this.currentRound = 0;
        this.lastRound = false;
    }
}
