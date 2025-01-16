package com.espabila.skyteam.model;

import java.util.Arrays;

public class ApproachTrack {
    private int currentPosition;
    private Boolean lastTrack;
    private int lastTrackNum;
    private int[] planeTokens;
    private Boolean gameOver;

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public Boolean getLastTrack() {
        return lastTrack;
    }

    public void setLastTrack(Boolean lastTrack) {
        this.lastTrack = lastTrack;
    }

    public int getLastTrackNum() {
        return lastTrackNum;
    }

    public void setLastTrackNum(int lastTrackNum) {
        this.lastTrackNum = lastTrackNum;
    }

    public int[] getPlaneTokens() {
        return planeTokens;
    }

    public void setPlaneTokens(int[] planeTokens) {
        this.planeTokens = planeTokens;
    }

    public Boolean getGameOver() {
        return gameOver;
    }

    public void setGameOver(Boolean gameOver) {
        this.gameOver = gameOver;
    }

    public ApproachTrack(){
        this.currentPosition = 0;
        this.lastTrack = false;
        this.lastTrackNum = 6;
        this.planeTokens = new int[]{0, 0, 1, 2, 1, 3, 2};
        this.gameOver = false;
    }

    public void moveForward(Engines engines) {
        int moveAmount = engines.getApproachTrackMove();
        if (moveAmount != 0) {
            for (int i = 0; i < moveAmount; i++) {
                if (currentPosition + 1 <= planeTokens.length) {
                    if (planeTokens[0] == 0) {
                        // Remove the first element (pop)
                        planeTokens = Arrays.copyOfRange(planeTokens, 1, planeTokens.length);
                        currentPosition++;
                    } else {
                        gameOver = true;
                        break;
                    }
                } else {
                    gameOver = true;
                    break;
                }
            }
        }
        currentPosition = 0;

        lastTrackNum = planeTokens.length - 1;

        isLastTrack();
    }

    public boolean isLastTrack() {
        return lastTrack = (planeTokens.length == 1);
    }

    public Boolean isGameOver(){
        return gameOver;
    }

    public void resetApproachTrack(){
        this.currentPosition = 0;
        this.lastTrack = false;
        this.lastTrackNum = 6;
        this.planeTokens = new int[]{0, 0, 1, 2, 1, 3, 2};
        this.gameOver = false;
    }

    public int howManyplanesAtTheAirport() {  // it's a method for the last round when only one track is left to make sure there are no planes left at the airport otherwise game over
        return planeTokens[0];
    }
}
