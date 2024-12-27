package com.espabila.skyteam.model;

public class Engines {
    private double pilotMarker;
    private double copilotMarker;
    private double finalPilotMarker;
    private double finalCopilotMarker;
    private int approachTrackMove;
    private int pilotSlot;
    private int copilotSlot;


    public Engines(){
        this.pilotMarker = 4.5;
        this.copilotMarker = 8.5;
        this.finalPilotMarker = 6.5;
        this.finalCopilotMarker = 12.5;
        this.approachTrackMove = 0;
        this.pilotSlot = 0;
        this.copilotSlot = 0;
    }

    public double getPilotMarker() {
        return pilotMarker;
    }

    public double getCopilotMarker() {
        return copilotMarker;
    }

    public void placeDice(Player player, int diceValue){
        if(player instanceof Pilot && pilotSlot == 0){
            pilotSlot = diceValue;
            player.removeDice(diceValue);
        }
        else if(player instanceof CoPilot && copilotSlot == 0){
            copilotSlot = diceValue;
            player.removeDice(diceValue);
        }
        else {
            System.out.println("Dice was already placed");
        }
    }

    public Boolean areDicesPlaced(int diceValue){
        if(pilotSlot > 0 && copilotSlot > 0){
            return true;
        }
        else {
            return false;
        }
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
