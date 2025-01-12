package com.espabila.skyteam.model;

import java.util.ArrayList;
import java.util.List;

public class Reroll {
    private Boolean rerollAvailable;
    private List<Integer> rerolledDices;
    private boolean pilotRerollActive;
    private boolean coPilotRerollActive;

    public Reroll(){
        this.rerollAvailable = false;
        this.rerolledDices = new ArrayList<>();
        pilotRerollActive = false;
        coPilotRerollActive = false;
    }

    public void setPilotRerollActive(boolean pilotRerollActive) {
        this.pilotRerollActive = pilotRerollActive;
    }

    public void setCoPilotRerollActive(boolean coPilotRerollActive) {
        this.coPilotRerollActive = coPilotRerollActive;
    }

    public boolean isPilotRerollActive() {
        return pilotRerollActive;
    }

    public boolean isCoPilotRerollActive() {
        return coPilotRerollActive;
    }

    public Boolean isRerollAvailable() {
        return rerollAvailable;
    }

    public void setRerollAvailable(Boolean rerollAvailable) {
        this.rerollAvailable = rerollAvailable;
        System.out.println("set reroll available in reroll class is called" + rerollAvailable);
    }

    public void useReroll(Player player, int diceIndex){ //player gives value of a dice that he wants to reroll
        if(player instanceof Pilot && pilotRerollActive){
            rerolledDices = player.getDiceList();
            Dice dice = new Dice();
            int newValue = dice.roll();
            rerolledDices.set(diceIndex, newValue);
            player.setDiceList(rerolledDices);
            System.out.println(newValue);
        }
        else if (player instanceof CoPilot && coPilotRerollActive){
            rerolledDices = player.getDiceList();
            Dice dice = new Dice();
            int newValue = dice.roll();
            rerolledDices.set(diceIndex, newValue);
            player.setDiceList(rerolledDices);
            System.out.println(newValue);
        }
    }
}
