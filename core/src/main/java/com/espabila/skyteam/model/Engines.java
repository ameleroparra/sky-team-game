package com.espabila.skyteam.model;

public class    Engines {
    private double pilotMarker;
    private double copilotMarker;
    private double finalPilotMarker;
    private double finalCopilotMarker;
    private int approachTrackMove;
    private int pilotSlot;
    private int copilotSlot;
    private int diceSum;

    private Flaps flaps;
    private LandGear landGear;

    public double getPilotMarker() {
        return pilotMarker;
    }

    public void setPilotMarker(double pilotMarker) {
        this.pilotMarker = pilotMarker;
    }

    public double getCopilotMarker() {
        return copilotMarker;
    }

    public void setCopilotMarker(double copilotMarker) {
        this.copilotMarker = copilotMarker;
    }

    public double getFinalPilotMarker() {
        return finalPilotMarker;
    }

    public void setFinalPilotMarker(double finalPilotMarker) {
        this.finalPilotMarker = finalPilotMarker;
    }

    public double getFinalCopilotMarker() {
        return finalCopilotMarker;
    }

    public void setFinalCopilotMarker(double finalCopilotMarker) {
        this.finalCopilotMarker = finalCopilotMarker;
    }

    public int getPilotSlot() {
        return pilotSlot;
    }

    public void setPilotSlot(int pilotSlot) {
        this.pilotSlot = pilotSlot;
    }

    public int getCopilotSlot() {
        return copilotSlot;
    }

    public void setCopilotSlot(int copilotSlot) {
        this.copilotSlot = copilotSlot;
    }

    public int getDiceSum() {
        return diceSum;
    }

    public void setDiceSum(int diceSum) {
        this.diceSum = diceSum;
    }

    public Engines(){
        this.pilotMarker = 4.5;
        this.copilotMarker = 8.5;
        this.finalPilotMarker = 7.5;
        this.finalCopilotMarker = 12.5;
        this.approachTrackMove = 0;
        this.pilotSlot = 0;
        this.copilotSlot = 0;
        this.diceSum = 0;
    }

    public void placeDice(Player player, int diceValue){
        if(player instanceof Pilot && pilotSlot == 0){
            pilotSlot = diceValue;
            System.out.println("Pilot placed Dice " + diceValue);
            player.removeDice(diceValue);
        }
        else if(player instanceof CoPilot && copilotSlot == 0){
            copilotSlot = diceValue;
            System.out.println("Copilot placed a dice " + diceValue);
            player.removeDice(diceValue);
        }
        else {
            System.out.println("Dice was already placed");
        }
    }

    public Boolean areDicesPlaced(){
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

    public void setApproachTrackMove(int approachTrackMove) {
        this.approachTrackMove = approachTrackMove;
    }

    public void advancePilotMarker(){
        if(pilotMarker < finalPilotMarker){
            this.pilotMarker += 1;
            System.out.println("Pilot marker moved forward: " + pilotMarker);
        }
        else{
            System.out.println("Pilot Engine is fully opened");
        }
    }

    public void advanceCopilotMarker(){
        if(copilotMarker < finalCopilotMarker){
            this.copilotMarker += 1;
            System.out.println("Co-Pilot marker moved forward: " + copilotMarker);
        }
        else{
            System.out.println("Co-Pilot Engine is fully opened");
        }
    }

    public void countDiceSum(){
        diceSum = this.pilotSlot + this.copilotSlot;
        if(diceSum < pilotMarker){
            this.approachTrackMove = 0;
            System.out.println("Approach track moved 0");
        }
        else if(pilotMarker < diceSum && diceSum < copilotMarker){
            this.approachTrackMove = 1;
            System.out.println("Approach track moved 1");
        }
        else if(copilotMarker < diceSum){
            this.approachTrackMove = 2;
            System.out.println("Approach track moved 2");
        }
        System.out.println("Dice sum: " + diceSum);
    }

    public void resetEnginesSlots(){
        this.pilotSlot = 0;
        this.copilotSlot = 0;
    }


    public void resetEngines(){
        this.pilotMarker = 4.5;
        this.copilotMarker = 8.5;
        this.pilotSlot = 0;
        this.copilotSlot = 0;
        this.approachTrackMove = 0;
    }


}
