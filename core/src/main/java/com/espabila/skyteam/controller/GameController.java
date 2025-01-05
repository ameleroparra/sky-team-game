package com.espabila.skyteam.controller;

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
    private Concentration concentration;
    private LandGear landGear;
    private Flaps flaps;
    private ApproachTrack approachTrack;
    private GamePlayScene gamePlayScene;
    private Boolean gameOver;

    public void setPilot(Player pilot) {
        this.pilot = pilot;
    }

    public void setCoPilot(Player coPilot) {
        this.coPilot = coPilot;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Radio getRadio() {
        return radio;
    }

    public void setRadio(Radio radio) {
        this.radio = radio;
    }

    public Brakes getBrakes() {
        return brakes;
    }

    public void setBrakes(Brakes brakes) {
        this.brakes = brakes;
    }

    public Engines getEngines() {
        return engines;
    }

    public void setEngines(Engines engines) {
        this.engines = engines;
    }

    public Axis getAxis() {
        return axis;
    }

    public void setAxis(Axis axis) {
        this.axis = axis;
    }

    public AltitudeTrack getAltitudeTrack() {
        return altitudeTrack;
    }

    public void setAltitudeTrack(AltitudeTrack altitudeTrack) {
        this.altitudeTrack = altitudeTrack;
    }

    public Concentration getConcentration() {
        return concentration;
    }

    public void setConcentration(Concentration concentration) {
        this.concentration = concentration;
    }

    public LandGear getLandGear() {
        return landGear;
    }

    public void setLandGear(LandGear landGear) {
        this.landGear = landGear;
    }

    public Flaps getFlaps() {
        return flaps;
    }

    public void setFlaps(Flaps flaps) {
        this.flaps = flaps;
    }

    public ApproachTrack getApproachTrack() {
        return approachTrack;
    }

    public void setApproachTrack(ApproachTrack approachTrack) {
        this.approachTrack = approachTrack;
    }

    public GamePlayScene getGamePlayScene() {
        return gamePlayScene;
    }

    public void setGamePlayScene(GamePlayScene gamePlayScene) {
        this.gamePlayScene = gamePlayScene;
    }

    public Boolean getGameOver() {
        return gameOver;
    }

    public void setGameOver(Boolean gameOver) {
        this.gameOver = gameOver;
    }

    //constructor
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
        approachTrack = new ApproachTrack();
        gamePlayScene = null;
        gameOver = false;
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

        // Initial dice roll for both players
        pilot.rollDices();
        coPilot.rollDices();
    }

    // Player Management
    public Player getPilot() { return pilot; }
    public Player getCoPilot() { return coPilot; }
    public Player getCurrentPlayer() { return currentPlayer; }
    public void switchTurn() {
        if (isRoundOver()) {
            if (altitudeTrack.isLastRound()) {
                gameOver = true;
            } else {
                altitudeTrack.newRound();
                altitudeTrack.checkLastRound();

                pilot.rollDices();
                coPilot.rollDices();

                currentPlayer = altitudeTrack.isPilotTurn() ? pilot : coPilot;
            }
        } else {
            currentPlayer = (currentPlayer == pilot) ? coPilot : pilot;
        }
    }

    private void startNewRound() {
        altitudeTrack.newRound();
        pilot.rollDices();
        coPilot.rollDices();
        currentPlayer = altitudeTrack.isPilotTurn() ? pilot : coPilot;
    }

    public boolean isPilotTurn() { return currentPlayer == pilot; }

    // Dice Placement
    public void placeDiceOnRadio(int diceValue) {
        radio.placeDice(currentPlayer, diceValue);
    }

    public void placeDiceOnPilotRadioSlot(int diceValue) {
        if (currentPlayer instanceof Pilot) {
            radio.placeDicePilotSlot((Pilot) currentPlayer, diceValue);
        }
        else{
            showErrorMessage("Only the Pilot can place dice on the Radio.");
        }
    }









    public void placeDiceOnBrakes(int diceValue, int brakeSlot) {
        if (currentPlayer instanceof Pilot) {
            boolean success = brakes.activateBrakes((Pilot) currentPlayer, brakeSlot, diceValue);
            if (success) {
                currentPlayer.removeDice(diceValue);
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










    public void placeDiceOnEngines(int diceValue) {
        engines.placeDice(currentPlayer, diceValue);
    }
    public void placeDiceOnAxis(int diceValue) {
        axis.placeDice(currentPlayer, diceValue);
    }
    public void placeDiceOnConcentration(int diceValue, int slotNumber) {
        concentration.placeDice(currentPlayer, diceValue, slotNumber);
    }
    public void placeDiceOnLandGear(int diceValue, int gearIndex) {
        if (currentPlayer instanceof Pilot) {
            landGear.activateEngine((Pilot) currentPlayer, gearIndex, diceValue);
        } else {
            gamePlayScene.showErrorMessage("Only the Pilot can activate land gears.");
        }
    }
    public void placeDiceOnFlaps(int flapsIndex, int diceValue) {
        if (currentPlayer instanceof CoPilot) {
            flaps.activateFlap((CoPilot) currentPlayer, flapsIndex, diceValue);
        } else {
            gamePlayScene.showErrorMessage("Only the Co-Pilot can activate flaps.");
        }
    }

    public boolean isRoundOver() {
        return pilot.getDiceList().isEmpty() && coPilot.getDiceList().isEmpty();
    }

    public boolean isGameOver() {
        // Implementation to check if the game is over
        return false; // Replace with actual game over logic
    }

    private void checkEndRoundConditions() {
        if(axis.getPilotSlot() > 0 && axis.getCopilotSlot() > 0 && engines.getPilotSlot() > 0 && engines.getCopilotSlot() > 0) {
            startNewRound();
        }
        else {
            gameOver = true;
        }
    }

    private void showErrorMessage(String message) {
        if (gamePlayScene != null) {
            gamePlayScene.showErrorMessage(message);
        } else {
            System.err.println("Error: " + message);
        }
    }

//    private boolean checkLandingConditions() {
//            engines.areInCorrectPosition() &&
//            axis.isInSafePosition() &&
//            landGear.isFullyDeployed() &&
//            flaps.areCorrectlySet() &&
//            approachTrack.isApproachSuccessful();
//    }
}
