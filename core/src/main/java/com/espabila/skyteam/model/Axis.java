package com.espabila.skyteam.model;

public class Axis {
     private int[] position;
     private int currentIndexPosition;
     private int absResult;
     private int pilotSlot;
     private int copilotSlot;
    private Boolean gameOver;

    public int getPosition() {
        return position[currentIndexPosition];
    }

    public void setPosition(int[] position) {
        this.position = position;
    }

    public int getCurrentIndexPosition() {
        return currentIndexPosition;
    }

    public void setCurrentIndexPosition(int currentIndexPosition) {
        this.currentIndexPosition = currentIndexPosition;
    }

    public int getAbsResult() {
        return absResult;
    }

    public void setAbsResult(int absResult) {
        this.absResult = absResult;
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

    public Boolean getGameOver() {
        return gameOver;
    }

    public void setGameOver(Boolean gameOver) {
        this.gameOver = gameOver;
    }

    //initiate axis on position 0
     public Axis() {
         this.position = new int[] {-2, -1, 0, 1, 2};
         this.currentIndexPosition = 2;
         this.pilotSlot = 0;
         this.copilotSlot = 0;
         this.gameOver = false;
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

     public Boolean areDicePlaced(){
         if(pilotSlot > 0 && copilotSlot > 0){
             return true;
         }
         else {
             return false;
         }
     }

     // Calculates how much and in which direction should the axis move
     public void currentIndexCalculation() {

         absResult = Math.abs(pilotSlot - copilotSlot);
         int leftMove = currentIndexPosition - absResult;
         int rightMove = currentIndexPosition + absResult;

         // moves axis to the pilot side, left
         if (pilotSlot > copilotSlot) {
             if (leftMove < 0) {
                 currentIndexPosition = 0;
                 gameOver = true;
             } else {
                 currentIndexPosition = leftMove;
             }
         }
         //moves axis to the copilot side, right
         else if (pilotSlot < copilotSlot) {
             if (rightMove > 4) {
                 currentIndexPosition = 4;
                 gameOver = true;
             } else {
                 currentIndexPosition = rightMove;
             }
         }
     }

     public void resetAxisSlots() {
         pilotSlot = 0;
         copilotSlot = 0;
     }

    public boolean axisAreHorizontal(){
        return getPosition() == 0;
    }
}
