package com.espabila.skyteam.model;

public class Axis {
     private int[] position;
     private int currentIndexPosition;
     private int absResult;
     private int pilotSlot;
     private int copilotSlot;

     //initiate axis on position 0
     public Axis() {
         this.position = new int[] {-2, -1, 0, 1, 2};
         this.currentIndexPosition = 2;
         this.pilotSlot = 0;
         this.copilotSlot = 0;
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


     // Calculates how much and in which direction should the axis move
     public void currentIndexCalculation(){

         if (pilotSlot != 0 && copilotSlot != 0){
             absResult = Math.abs(pilotSlot - copilotSlot);

             // moves axis to the pilot side, left
             if (pilotSlot > copilotSlot){
                 currentIndexPosition = currentIndexPosition - absResult;
             }

             //moves axis to the copilot side, right
             else if (pilotSlot < copilotSlot){
                 currentIndexPosition = currentIndexPosition + absResult;
             }
         }
     }

     // getter for the final result
     public int getPosition() {
         return currentIndexPosition;
     }

     // check this. uses
}
