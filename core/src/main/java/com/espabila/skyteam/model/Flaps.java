package com.espabila.skyteam.model;

public class Flaps {
    private boolean[] activated;
    private int[] firstRequired;
    private int[] secondRequired;
    private int[] thirdRequired;
    private int[] fourthRequired;

    public Flaps() {
        this.activated = new boolean[4]; //flaps deactivated
        this.firstRequired = new int[]{1, 2};
        this.secondRequired = new int[]{2, 3};
        this.thirdRequired = new int[]{4, 5};
        this.fourthRequired = new int[]{5, 6};
    }

    public void setActivated(boolean[] activated) {
        this.activated = activated;
    }

    public boolean activateFlap(CoPilot coPilot, int flapIndex, int value){
        if (flapIndex > 0 && !activated[flapIndex - 1]){
            return false;
        }

        else if (flapIndex == 0){
            for (int element : firstRequired) {
                if (element == value) {
                    activated[flapIndex] = true;
                    System.out.println("Activated first flap");
                    coPilot.removeDice(value);
                    return true;
                }
            }
            return false;
        }

        else if (flapIndex == 1){
            for (int element : secondRequired) {
                if (element == value) {
                    activated[flapIndex] = true;
                    System.out.println("Activated second flap");
                    coPilot.removeDice(value);
                    return true;
                }
            }
            return false;
        }

        else if (flapIndex == 2){
            for (int element : thirdRequired) {
                if (element == value) {
                    activated[flapIndex] = true;
                    System.out.println("Activated third flap");
                    coPilot.removeDice(value);
                    return true;
                }
            }
            return false;
        }

        else if (flapIndex == 3){
            for (int element : fourthRequired) {
                if (element == value) {
                    activated[flapIndex] = true;
                    System.out.println("Activated fourth flap");
                    coPilot.removeDice(value);
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public boolean isActivated(int flapIndex){
        return activated[flapIndex];
    }

    public boolean allActivated(){
        for (int i = 0; i < 4; i++){
            if (!activated[i]){
                return false;
            }
        }
        return true;
    }

    public void resetFlaps(){
        for(int i = 0; i < activated.length; i++){
            activated[i] = false;
        }
    }


}
