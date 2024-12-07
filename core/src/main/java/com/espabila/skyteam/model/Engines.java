package com.espabila.skyteam.model;

public class Engines {
    private double pilotMarker;
    private double copilotMarker;
    private double finalPilotMarker;
    private double finalCopilotMarker;
    private int approachTrackMove;


    public Engines(){
        this.pilotMarker = 4.5;
        this.copilotMarker = 8.5;
        this.finalPilotMarker = 6.5;
        this.finalCopilotMarker = 12.5;
        this.approachTrackMove = 0;
    }

    public int getApproachTrackMove() {
        return approachTrackMove;
    }

    public void advancePilotMarker(){
        if(pilotMarker < finalPilotMarker){
            this.pilotMarker += 1;
        }
        else{
            System.out.println("Pilot Engine is fully opened");
        }
    }

    public void advanceCopilotMarker(){
        if(copilotMarker < finalCopilotMarker){
            this.copilotMarker += 1;
        }
        else{
            System.out.println("Co-Pilot Engine is fully opened");
        }
    }

    public void countDiceSum(int pilotDice, int copilotDice){
        int diceSum = pilotDice + copilotDice;
        if(diceSum < pilotMarker){
            this.approachTrackMove = 0;
        }
        else if(pilotMarker < diceSum && diceSum < copilotMarker){
            this.approachTrackMove = 1;
        }
        else if(copilotMarker < diceSum){
            this.approachTrackMove = 2;
        }
    }


}
