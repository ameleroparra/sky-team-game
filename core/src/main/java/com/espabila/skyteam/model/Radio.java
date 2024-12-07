package com.espabila.skyteam.model;

public class Radio {

    public Radio(){

    }

    public void removePlaneToken(Dice dice, ApproachTrack approachTrack){
        int findPlane = dice.getValue() - 1;
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
