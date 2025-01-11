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

    public int getLastApproachTrackNum(){
        return approachTrack.getLastTrackNum();
    }
    public int getCurrentApproachTrackPosition() {
        return approachTrack.getCurrentPosition();
    }

    public int[] getApproachTrackPlaneTokens() {
        return approachTrack.getPlaneTokens();
    }

    public int[] setApproachTrackPlaneTokens(int[] planeTokens) {
        approachTrack.setPlaneTokens(planeTokens);
        return approachTrack.getPlaneTokens();
    }

    public void switchTurn() {
        if (isRoundOver()) {
            System.out.println("Switch Turn Method: the round is Over");
            if (altitudeTrack.isLastRound()) {
                gameOver = true;
                gamePlayScene.gameOverScreen();
            } else {
                startNewRound();
            }
        } else {
            System.out.println("Switch Turn Method: the round is not Over");
            currentPlayer = (currentPlayer == pilot) ? coPilot : pilot;
        }
    }

    private void startNewRound() {
        System.out.println("Controller: new round started");
        System.out.println("Turn before calling new round: " + currentPlayer);
        checkSpecialCases();
        if(gameOver){
            gamePlayScene.gameOverScreen();
        }
        else{
            altitudeTrack.newRound();
            System.out.println("Turn after calling new round: " + currentPlayer);
            radio.resetSlots();
            engines.resetEnginesSlots();
            axis.resetAxisSlots();

            pilot.rollDices();
            coPilot.rollDices();
            if (altitudeTrack.isPilotTurn()) {
                currentPlayer = pilot;
            } else {
                currentPlayer = coPilot;
            }

            System.out.println("Controller, pilot: " + pilot.getDiceList());
            System.out.println("Controller, coPilot: " + coPilot.getDiceList());


            gamePlayScene.resetNextRoundSlots();
            gamePlayScene.updateDiceImages();
            gamePlayScene.updateApproachTrackVisuals();

            gamePlayScene.startNewRound();
        }
    }

    public boolean isPilotTurn() { return currentPlayer == pilot; }

    // Radios
    public void placeDiceOnRadioSlot(int diceValue) {
        if (currentPlayer instanceof Pilot) {
            radio.placeDice(currentPlayer, diceValue);
            radio.removePlaneToken(diceValue, approachTrack);
            gamePlayScene.updateApproachTrackVisuals();
            checkUpAfterDicePlacement();
        }
        else if(currentPlayer instanceof CoPilot){
            radio.placeDice(currentPlayer, diceValue);
            radio.removePlaneToken(diceValue, approachTrack);
            gamePlayScene.updateApproachTrackVisuals();
            checkUpAfterDicePlacement();
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
                checkUpAfterDicePlacement();
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
                checkUpAfterDicePlacement();
            }
            else {
                gamePlayScene.showErrorMessage("There is already a dice in the pilot slot.");
            }
        } else if (currentPlayer instanceof CoPilot) {
            if (engines.getCopilotSlot() == 0) {
                engines.placeDice(currentPlayer, diceValue);
                checkUpAfterDicePlacement();
            }
            else {
                gamePlayScene.showErrorMessage("There is already a dice in the co-pilot slot.");
            }
        }

        if (engines.areDicesPlaced()) {
            engines.countDiceSum();
            if(altitudeTrack.isLastRound()){
                if(!(engines.getDiceSum() < brakes.getBrakesSum())){
                    gameOver = true;
                    gamePlayScene.gameOverScreen();
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
                checkUpAfterDicePlacement();
            }
            else {
                gamePlayScene.showErrorMessage("There is already a dice in the pilot slot.");
            }
        }else if (currentPlayer instanceof CoPilot) {
            if (axis.getCopilotSlot() == 0) {
                axis.placeDice(currentPlayer, diceValue);
                checkUpAfterDicePlacement();
            }
            else {
                gamePlayScene.showErrorMessage("There is already a dice in the co-pilot slot.");
            }
        }

        if (axis.areDicesPlaced()) {
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
            checkUpAfterDicePlacement();
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

    // Land Gear
    public boolean placeDiceOnLandGear(int diceValue, int gearIndex) {
        landGear.activateLandGear((Pilot) currentPlayer, gearIndex, diceValue);

        if (landGear.isActivated(gearIndex)) {
            engines.advancePilotMarker();
        }

        checkUpAfterDicePlacement();
        return landGear.isActivated(gearIndex);
    }

    // Flaps
    public boolean placeDiceOnFlaps(int diceValue, int flapsIndex) {
        flaps.activateFlap((CoPilot) currentPlayer, flapsIndex, diceValue);

        if (flaps.isActivated(flapsIndex)) {
            engines.advanceCopilotMarker();
        }

        checkUpAfterDicePlacement();
        return flaps.isActivated(flapsIndex);
    }

    public boolean isRoundOver() {
        return pilot.getDiceList().isEmpty() && coPilot.getDiceList().isEmpty();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    private void checkEndRoundConditions() {
        if(axis.areDicesPlaced() && engines.areDicesPlaced()) {
            startNewRound();
        }
        else {
            gameOver = true;
        }
    }

    private void checkUpAfterDicePlacement(){
        if(isRoundOver()){
            System.out.println("round over");
            if(altitudeTrack.isLastRound()){
                if(checkLandingConditions()){
//                    gamePlayScene.winScreen();
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
//
    private boolean checkLandingConditions() {
        return approachTrack.isLastTrack() &&
        altitudeTrack.isLastRound() &&
        engines.areDicesPlaced() &&
        axis.areDicesPlaced() &&
        axis.axisAreHorizontal() &&
        (engines.getDiceSum() < brakes.getBrakesSum()) &&
        flaps.allActivated() &&
        landGear.allActivated();
    }

    private void checkSpecialCases() {
        if(approachTrack.isLastTrack() && !altitudeTrack.isLastRound()){
            gameOver = true;
        }
        else if(!approachTrack.isLastTrack() && altitudeTrack.isLastRound()){
            gameOver = true;
        }
    }
}
