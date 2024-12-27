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

    public void placeDice(Player player, int diceValue){
        if(player instanceof Pilot && pilotSlot == 0){
            pilotSlot = diceValue;
            player.removeDice(diceValue);
        }
        else if(player instanceof CoPilot){
            if(copilotFirstSlot == 0){
                copilotFirstSlot = diceValue;
                player.removeDice(diceValue);
            }
            else if(copilotSecondSlot == 0){
                copilotSecondSlot = diceValue;
                player.removeDice(diceValue);
            }
        }
        else {
            System.out.println("Dice was already placed");
        }
    }

    public void removePlaneToken(int diceValue, ApproachTrack approachTrack){
        int findPlane = diceValue - 1;
        int[] planeTokens= approachTrack.getPlaneTokens();
        int currentTrackPosition = approachTrack.getCurrentPosition();
        int planePosition = currentTrackPosition + findPlane;
        int lastTrack = approachTrack.getLastTrackNum();
        if(planePosition <= lastTrack){
            if(planeTokens[findPlane] > 0){
            planeTokens[findPlane] -= 1;
            }
        }
        else {
            if(planeTokens[lastTrack] > 0){
                planeTokens[lastTrack] -=1;
            }
        }
        approachTrack.setPlaneTokens(planeTokens);
    }
}
