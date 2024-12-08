package com.espabila.skyteam.model;

public class Axis {
     private int[] position;
     private int currentIndexPosition;
     private int pilotDice = 0;
     private int copilotDice = 0;
     private int absResult;

     //initiate axis on position 0
     public Axis() {
         this.position = new int[] {-2, -1, 0, 1, 2};
         this.currentIndexPosition = 2;
     }

     //makes sure pilot places a dice, and only one
     public void pilotSlot(Dice dice){
        if (pilotDice == 0){
            pilotDice = dice.getValue();
        }
        else {
            //return warning message "no more than one dice allowed"
        }
     }

     //makes sure copilot places a dice, and only one
     public void copilotSlot(Dice dice){
         if (copilotDice == 0){
             copilotDice = dice.getValue();
         }
         else {
             //return warning message "no more than one dice allowed"
         }
     }

     // Calculates how much and in which direction should the axis move
     public void currentIndexCalculation(){

         if (pilotDice != 0 && copilotDice != 0){
             absResult = Math.abs(pilotDice - copilotDice);

             // moves axis to the pilot side, left
             if (pilotDice > copilotDice){
                 currentIndexPosition = currentIndexPosition - absResult;
             }

             //moves axis to the copilot side, right
             else if (pilotDice < copilotDice){
                 currentIndexPosition = currentIndexPosition + absResult;
             }
         }
     }

     // getter for the final result
     public int getPosition() {
         return currentIndexPosition;
     }



}
