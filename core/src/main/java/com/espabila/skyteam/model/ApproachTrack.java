package com.espabila.skyteam.model;

import java.util.Arrays;

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

    public void setPlaneTokens(int[] planeTokens) {
        this.planeTokens = planeTokens;
    }

    public void setGameOver(Boolean gameOver) {
        this.gameOver = gameOver;
    }


    public void moveForward(Engines engines) {
        int moveAmount = engines.getApproachTrackMove();

        if (moveAmount != 0) {
            for (int i = 0; i < moveAmount; i++) {
                if (currentPosition + 1 < planeTokens.length) {
                    if (planeTokens[0] == 0) {
                        // Remove the first element (pop)
                        planeTokens = Arrays.copyOfRange(planeTokens, 1, planeTokens.length);
                        currentPosition++;
                    } else {
                        gameOver = true;
                        break;
                    }
                } else {
                    // We've reached the end of the track
                    gameOver = true;
                    break;
                }
            }
        }
        currentPosition = 0;

        // Update lastTrackNum
        lastTrackNum = planeTokens.length - 1;

        isLastTrack();
    }

    public void isLastTrack() {
        lastTrack = (planeTokens.length == 1);
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
}
