package com.espabila.skyteam.model;

public class ApproachTrack {
    private int currentPosition;
    private Boolean lastTrack;
    private int lastTrackNum;
    private int[] planeTokens;
    private Boolean gameOver;

    public ApproachTrack(){
        this.currentPosition = 0;
        this.lastTrack = false;
        this.lastTrackNum = 6;
        this.planeTokens = new int[]{0, 0, 1, 2, 1, 3, 2};
        this.gameOver = false;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public int getLastTrackNum() {
        return lastTrackNum;
    }

    public Boolean getLastTrack() {
        return lastTrack;
    }

    public int[] getPlaneTokens() {
        return planeTokens;
    }

    public Boolean getGameOver() {
        return gameOver;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void setLastTrack(Boolean lastTrack) {
        this.lastTrack = lastTrack;
    }

    public void setPlaneTokens(int[] planeTokens) {
        this.planeTokens = planeTokens;
    }

    public void setGameOver(Boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void moveForward(Engines engines) {
        int moveOneForward = currentPosition + 1;
        int moveTwoForward = currentPosition + 2;

        if (engines.getApproachTrackMove() != 0) {
            if (engines.getApproachTrackMove() == 1 && moveOneForward <= lastTrackNum) {
                if (planeTokens[moveOneForward] == 0) {
                    currentPosition = moveOneForward;
                } else {
                    gameOver = true;
                }
            } else if (engines.getApproachTrackMove() == 2 && moveTwoForward <= lastTrackNum) {
                if (planeTokens[moveOneForward] == 0 && planeTokens[moveTwoForward] == 0) {
                    currentPosition = moveTwoForward;
                } else {
                    gameOver = true;
                }
            } else {
                gameOver = true;
            }
        }
        isLastTrack();
    }

    public void isLastTrack(){
        if(currentPosition == 6){
            lastTrack = true;
        }
    }
}
