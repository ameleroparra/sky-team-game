package com.espabila.skyteam.model;

public class Radio {
    private int pilotSlot;
    private int copilotFirstSlot;
    private int copilotSecondSlot;

    public Radio(){
        this.pilotSlot = 0;
        this.copilotFirstSlot = 0;
        this.copilotSecondSlot = 0;
    }

    public int getPilotSlot() {
        return pilotSlot;
    }

    public void setPilotSlot(int pilotSlot) {
        this.pilotSlot = pilotSlot;
    }

    public int getCopilotFirstSlot() {
        return copilotFirstSlot;
    }

    public void setCopilotFirstSlot(int copilotFirstSlot) {
        this.copilotFirstSlot = copilotFirstSlot;
    }

    public int getCopilotSecondSlot() {
        return copilotSecondSlot;
    }

    public void setCopilotSecondSlot(int copilotSecondSlot) {
        this.copilotSecondSlot = copilotSecondSlot;
    }

//    public void placeDice(Player player, int diceValue){
//        if(player instanceof Pilot && pilotSlot == 0){
//            System.out.println("Pilot placed Dice");
//            pilotSlot = diceValue;
//            player.removeDice(diceValue);
//        }
//        else if(player instanceof CoPilot){
//            System.out.println("Copilot placed a dice");
//            if(copilotFirstSlot == 0){
//                copilotFirstSlot = diceValue;
//                player.removeDice(diceValue);
//            }
//            else if(copilotSecondSlot == 0){
//                copilotSecondSlot = diceValue;
//                player.removeDice(diceValue);
//            }
//        }
//        else {
//            System.out.println("Dice was already placed");
//        }
//    }

    public void placeDicePilotSlot(Pilot pilot, int diceValue){
        pilotSlot = diceValue;
        pilot.removeDice(diceValue);
    }

    public void removePlaneToken(int diceValue, ApproachTrack approachTrack){
        int[] planeTokens= approachTrack.getPlaneTokens();
        int currentTrackPosition = approachTrack.getCurrentPosition();
        int planePosition = currentTrackPosition + diceValue - 1;
        int lastTrackNum = approachTrack.getLastTrackNum();

        if(planePosition <= lastTrackNum){
            if(planeTokens[planePosition] > 0){
            planeTokens[planePosition] -= 1;
            }
        }
        else {
            if(planeTokens[lastTrackNum-1] > 0){
                planeTokens[lastTrackNum-1] -=1;
            }
        }
        approachTrack.setPlaneTokens(planeTokens);
    }

    public void resetSlots(){
        pilotSlot = 0;
        copilotFirstSlot = 0;
        copilotSecondSlot = 0;
    }
}
