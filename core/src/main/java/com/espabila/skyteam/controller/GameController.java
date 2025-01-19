package com.espabila.skyteam.controller;

import com.espabila.skyteam.SkyTeamGame;
import com.espabila.skyteam.model.*;
import com.espabila.skyteam.view.GamePlayScene;

public class GameController {
    private Player pilot;
    private Player coPilot;
    private Player currentPlayer;
    private Radio radio;
    private Brakes brakes;
    private Engines engines;
    private Axis axis;
    private AltitudeTrack altitudeTrack;
    private ApproachTrack approachTrack;
    private Concentration concentration;
    private LandGear landGear;
    private Flaps flaps;
    private GamePlayScene gamePlayScene;
    private Boolean gameOver;
    private SkyTeamGame game;
    private final Reroll reroll;
    private boolean rerollUsedThisRound;

    public void setGamePlayScene(GamePlayScene gamePlayScene) {
        this.gamePlayScene = gamePlayScene;
    }

    // Constructor
    public GameController(){
        pilot = new Pilot();
        coPilot = new CoPilot();
        currentPlayer = pilot;
        radio = new Radio();
        brakes = new Brakes();
        engines = new Engines();
        axis = new Axis();
        altitudeTrack = new AltitudeTrack();
        concentration = new Concentration();
        landGear = new LandGear();
        flaps = new Flaps();
        reroll = new Reroll();
        approachTrack = new ApproachTrack();
        gamePlayScene = null;
        gameOver = false;
        rerollUsedThisRound = false;
    }

    public void startNewGame(){
        pilot.resetDiceList();
        coPilot.resetDiceList();
        currentPlayer = pilot;
        radio.resetSlots();
        brakes.resetBrakes();
        engines.resetEngines();
        axis.resetAxisSlots();
        altitudeTrack.resetAltitudeTrack();
        concentration.resetConcentration();
        landGear.resetLandGear();
        flaps.resetFlaps();
        approachTrack.resetApproachTrack();
        altitudeTrack.rerollAvailable(reroll);
        pilot.rollDice();
        coPilot.rollDice();
    }

    public void startNewRound() {
        gamePlayScene.showCover();
        if(gameOver){
            gamePlayScene.gameOverScreen();
        }
        else{
            altitudeTrack.newRound();
            checkSpecialCases();
            radio.resetSlots();
            engines.resetEnginesSlots();
            axis.resetAxisSlots();
            altitudeTrack.rerollAvailable(reroll);
            gamePlayScene.updateRerollVisuals();
            gamePlayScene.updateFlapImages();
            gamePlayScene.updateLandGearImages();

            pilot.rollDice();
            coPilot.rollDice();
            if (altitudeTrack.isPilotTurn()) {
                currentPlayer = pilot;
            } else {
                currentPlayer = coPilot;
            }
            rerollUsedThisRound = false;
            setPilotRerollActive(false);
            setPilotRerollActive(false);

            gamePlayScene.resetNextRoundSlots();
            gamePlayScene.updateDiceImages();
            gamePlayScene.updateApproachTrackVisuals();
            gamePlayScene.updateRerollVisuals();

            gamePlayScene.startNewRound();
        }
    }

    // Getters and Setters from models that are needed for management
    public Player getCurrentPlayer() { return currentPlayer; }

    public int getLastApproachTrackNum(){
        return approachTrack.getLastTrackNum();
    }

    public int[] getApproachTrackPlaneTokens() {
        return approachTrack.getPlaneTokens();
    }

    public int getAltitudeTrackCurrentRound() {
        return altitudeTrack.getCurrentRound();
    }

    public void switchTurn() {
        currentPlayer = (currentPlayer == pilot) ? coPilot : pilot;
    }

    // Dice placement
    // Radios
    public void placeDiceOnRadioSlot(int diceValue) {
        if (currentPlayer instanceof Pilot) {
            radio.placeDice(currentPlayer, diceValue);
            radio.removePlaneToken(diceValue, approachTrack);
            gamePlayScene.updateApproachTrackVisuals();
        }
        else if(currentPlayer instanceof CoPilot){
            radio.placeDice(currentPlayer, diceValue);
            radio.removePlaneToken(diceValue, approachTrack);
            gamePlayScene.updateApproachTrackVisuals();
        } else {
            gamePlayScene.showErrorMessage("Only the Pilot can place dice on the Radio.");
        }
    }

    // Brakes
    public void placeDiceOnBrakes(int diceValue, int brakeSlot) {
        if (currentPlayer instanceof Pilot) {
            boolean success = brakes.activateBrakes((Pilot) currentPlayer, brakeSlot, diceValue);
            if (success) {
                gamePlayScene.updateBrakeVisuals(brakeSlot, true);
            } else {
                if (brakeSlot > 0 && !brakes.isBrakeActivated(brakeSlot - 1)) {
                    gamePlayScene.showErrorMessage("You must activate the previous brake first.");
                } else {
                    gamePlayScene.showErrorMessage("Invalid dice value for this brake.");
                }
            }
        } else {
            gamePlayScene.showErrorMessage("Only the Pilot can activate brakes.");
        }
    }

    // Engines
    public void placeDiceOnEngines(int diceValue) {
        if (currentPlayer instanceof Pilot) {
            if (engines.getPilotSlot() == 0) {
                engines.placeDice(currentPlayer, diceValue);
            }
            else {
                gamePlayScene.showErrorMessage("There is already a dice in the pilot slot.");
            }
        } else if (currentPlayer instanceof CoPilot) {
            if (engines.getCopilotSlot() == 0) {
                engines.placeDice(currentPlayer, diceValue);
            }
            else {
                gamePlayScene.showErrorMessage("There is already a dice in the co-pilot slot.");
            }
        }
        if (engines.areDicesPlaced()) {
            engines.countDiceSum();
            if(altitudeTrack.isLastRound()){
                if(!(engines.getDiceSum() < brakes.getBrakesSum())){
                    System.out.println("sum of engines is more than the sum of brakes");
                    gameOver = true;
                    gamePlayScene.gameOverScreen();
                }
                else {
                    System.out.println("It's the last round and the sum of engines is less than the sum of brakes");
                }
            }
            else {
                approachTrack.moveForward(engines);
                if(approachTrack.getGameOver()){
                    gameOver = true;
                    gamePlayScene.gameOverScreen();
                }
                else{
                    gamePlayScene.updateApproachTrackVisuals();
                }
            }
        }

    }

    // Axis
    public void placeDiceOnAxis(int diceValue) {
        if (currentPlayer instanceof Pilot) {
            if (axis.getPilotSlot() == 0) {
                axis.placeDice(currentPlayer, diceValue);
            }
            else {
                gamePlayScene.showErrorMessage("There is already a dice in the pilot slot.");
            }
        }else if (currentPlayer instanceof CoPilot) {
            if (axis.getCopilotSlot() == 0) {
                axis.placeDice(currentPlayer, diceValue);
            }
            else {
                gamePlayScene.showErrorMessage("There is already a dice in the co-pilot slot.");
            }
        }

        if (axis.areDicePlaced()) {
            axis.currentIndexCalculation();
            int currentPosition = axis.getPosition();
            gamePlayScene.updateAxisPlaneVisuals(currentPosition);
            if(axis.getGameOver()){
                gameOver = true;
                gamePlayScene.gameOverScreen();
            }
        }
    }

    // Concentration
    public void placeDiceOnConcentration(int diceValue, int slotIndex) {
        boolean isActivated = concentration.isActivated(slotIndex);
        if (!isActivated) {
            concentration.placeDice(currentPlayer, diceValue, slotIndex);
            isActivated = concentration.isActivated(slotIndex);
            gamePlayScene.updateCoffeeVisuals(slotIndex, isActivated);
        } else {
            gamePlayScene.showErrorMessage("this Slot is already activated");
        }
    }

    public void changeValueDown(int diceValue, int diceIndex) {
        concentration.useDown(currentPlayer, diceValue, diceIndex);
        gamePlayScene.hideConcentrationImages();
        concentration.setActivated(gamePlayScene.coffeeSlotIndex, false);
        gamePlayScene.updateCoffeeVisuals(gamePlayScene.coffeeSlotIndex, false);
    }

    public void changeValueUp(int diceValue, int diceIndex) {
        concentration.useUp(currentPlayer, diceValue, diceIndex);
        gamePlayScene.hideConcentrationImages();
        concentration.setActivated(gamePlayScene.coffeeSlotIndex, false);
        gamePlayScene.updateCoffeeVisuals(gamePlayScene.coffeeSlotIndex, false);
    }

    // Reroll
    public void useRerollDice(int diceIndex) {
        if(currentPlayer instanceof Pilot && getPilotRerollActive()) {
            reroll.useReroll(currentPlayer, diceIndex);
        }
        else if(currentPlayer instanceof CoPilot && getCoPilotRerollActive()){
            reroll.useReroll(currentPlayer, diceIndex);
        }
    }

    public void useRerollSlot() {
        if (reroll.getRerollAvailable()) {
            gamePlayScene.createRerollScreen();
            reroll.setPilotRerollActive(true);
            reroll.setCoPilotRerollActive(true);
            reroll.setRerollAvailable(false);
        }
    }

    public boolean isRerollAvailable() {
        return reroll.getRerollAvailable();
    }

    public void setPilotRerollActive(boolean active) {
        reroll.setPilotRerollActive(active);
    }

    public void setCoPilotRerollActive(boolean active) {
        reroll.setCoPilotRerollActive(active);
    }

    public boolean getPilotRerollActive() {
        return reroll.isPilotRerollActive();
    }

    public boolean getCoPilotRerollActive() {
        return reroll.isCoPilotRerollActive();
    }

    // Land Gear
    public boolean placeDiceOnLandGear(int diceValue, int gearIndex) {
        landGear.activateLandGear((Pilot) currentPlayer, gearIndex, diceValue);

        if (landGear.isActivated(gearIndex)) {
            engines.advancePilotMarker();
        }

        return landGear.isActivated(gearIndex);
    }

    public boolean landGearIsActivated(int gearIndex) {
        return landGear.isActivated(gearIndex);
    }

    // Flaps
    public boolean placeDiceOnFlaps(int diceValue, int flapsIndex) {
        flaps.activateFlap((CoPilot) currentPlayer, flapsIndex, diceValue);

        if (flaps.isActivated(flapsIndex)) {
            engines.advanceCopilotMarker();
        }

        return flaps.isActivated(flapsIndex);
    }

    public boolean flapIsActivated(int flapIndex) {
        return flaps.isActivated(flapIndex);
    }

    // Check conditions
    public boolean isRoundOver() {
        return pilot.getDiceList().isEmpty() && coPilot.getDiceList().isEmpty();
    }

    public void checkUpAfterDicePlacement(){
        if(isRoundOver()){
            if(altitudeTrack.isLastRound()){
                if(checkLandingConditions()){
                    gamePlayScene.winScreen();
                }
                else{
                    gameOver = true;
                    gamePlayScene.gameOverScreen();
                }
            }
            else {
                checkEndRoundConditions();
            }

        }
    }

    private void checkEndRoundConditions() {
        if(axis.areDicePlaced() && engines.areDicesPlaced()) {
            startNewRound();
        }
        else {
            gameOver = true;
            gamePlayScene.gameOverScreen();
        }
    }

    private void checkSpecialCases() {
        if(!approachTrack.isLastTrack() && altitudeTrack.isLastRound()){
            gameOver = true;
            gamePlayScene.gameOverScreen();
        }
    }

    private boolean checkLandingConditions() {
        return approachTrack.isLastTrack() &&
        altitudeTrack.isLastRound() &&
        approachTrack.howManyplanesAtTheAirport() == 0 &&
        engines.areDicesPlaced() &&
        axis.areDicePlaced() &&
        axis.axisAreHorizontal() &&
        (engines.getDiceSum() < brakes.getBrakesSum()) &&
        flaps.allActivated() &&
        landGear.allActivated();
    }
}
