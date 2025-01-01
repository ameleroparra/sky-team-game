//package com.espabila.skyteam.controller;
//
//import com.espabila.skyteam.model.*;
////import com.espabila.skyteam.view.GameView;
//
//public class GameController {
//    public enum GameState {
//        MENU, ROLE_SELECTION, STRATEGY, PLAYING, GAME_OVER
//    }
//
//    private GameState currentState;
//    private Player currentPlayer;
//    private Player pilot;
//    private Player coPilot;
//    private AltitudeTrack altitudeTrack;
//    private Axis axis;
//    private Concentration concentration;
//    private Engines engines;
//    private Radio radio;
//    private LandGear landGear;
//    private Brakes brakes;
//    private Flaps flaps;
//    private ApproachTrack approachTrack;
//    private GameView gameView;
//
//    public GameController(GameView gameView) {
//        this.gameView = gameView;
//        currentState = GameState.MENU;
//    }
//
//    public void startNewGame() {
//        currentState = GameState.ROLE_SELECTION;
//        pilot = new Pilot();
//        coPilot = new CoPilot();
//        altitudeTrack = new AltitudeTrack();
//        axis = new Axis();
//        concentration = new Concentration();
//        engines = new Engines();
//        radio = new Radio();
//        landGear = new LandGear();
//        brakes = new Brakes();
//        flaps = new Flaps();
//        approachTrack = new ApproachTrack();
//        gameView.updateGameState(currentState);
//    }
//
//    public void rolesSelected() {
//        currentState = GameState.STRATEGY;
//        gameView.updateGameState(currentState);
//    }
//
//    public void startPlaying() {
//        currentState = GameState.PLAYING;
//        currentPlayer = pilot; // pilot starts first
//        gameView.updateGameState(currentState);
//        gameView.highlightCurrentPlayer(currentPlayer);
//    }
//
//    public void updateGameState() {
//        if (checkWinCondition()) {
//            currentState = GameState.GAME_OVER;
//            gameView.showMessage("Congratulations! The plane has landed safely!");
//        } else if (checkLoseCondition()) {
//            currentState = GameState.GAME_OVER;
//            gameView.showMessage("Game Over. Unfortunately, the plane has crashed.");
//        }
//        gameView.updateGameState(currentState);
//    }
//
//    public boolean isGameOver() {
//        return currentState == GameState.GAME_OVER;
//    }
//
//    public boolean isWin() {
//        return checkWinCondition();
//    }
//
//
//    public void placeDiceOnAxis(int diceValue) {
//        if (axis.placeDice(currentPlayer, diceValue)) {
//            currentPlayer.removeDice(diceValue);
//            gameView.updateAxisStatus(axis.getStatus());
//            gameView.updatePlayerDice(currentPlayer);
//            endTurn();
//        } else {
//            gameView.showMessage("Invalid placement on Axis. Try again.");
//        }
//    }
//
//    public void placeDiceOnConcentration(int diceValue) {
//        if (concentration.placeDice(currentPlayer, diceValue)) {
//            currentPlayer.removeDice(diceValue);
//            gameView.updateConcentrationStatus(concentration.getStatus());
//            gameView.updatePlayerDice(currentPlayer);
//            endTurn();
//        } else {
//            gameView.showMessage("Invalid placement on Concentration. Try again.");
//        }
//    }
//
//    public void placeDiceOnEngines(int diceValue) {
//        if (engines.placeDice(currentPlayer, diceValue)) {
//            currentPlayer.removeDice(diceValue);
//            gameView.updateEnginesStatus(engines.getStatus());
//            gameView.updatePlayerDice(currentPlayer);
//            endTurn();
//        } else {
//            gameView.showMessage("Invalid placement on Engines. Try again.");
//        }
//    }
//
//    public void placeOnRadio(int diceValue) {
//        if (radio.placeDice(currentPlayer, diceValue)) {
//            currentPlayer.removeDice(diceValue);
//            radio.removePlaneToken(diceValue, approachTrack);
//            gameView.updateRadioStatus(radio.getStatus());
//            gameView.updateApproachTrackStatus(approachTrack.getStatus());
//            gameView.updatePlayerDice(currentPlayer);
//            endTurn();
//        } else {
//            gameView.showMessage("Invalid placement on Radio. Try again.");
//        }
//    }
//
//    public void placeDiceOnGears(int diceValue, int gearIndex) {
//        if (currentPlayer instanceof Pilot && landGear.activateEngine((Pilot)currentPlayer, gearIndex, diceValue)) {
//            currentPlayer.removeDice(diceValue);
//            gameView.updateLandGearStatus(landGear.getStatus());
//            gameView.updatePlayerDice(currentPlayer);
//            endTurn();
//        } else {
//            gameView.showMessage("Invalid placement on Landing Gear. Try again.");
//        }
//    }
//
//    public void placeDiceOnBrakes(int brakeIndex, int diceValue) {
//        if (currentPlayer instanceof Pilot && brakes.activateBrakes((Pilot)currentPlayer, brakeIndex, diceValue)) {
//            currentPlayer.removeDice(diceValue);
//            gameView.updateBrakesStatus(brakes.getStatus());
//            gameView.updatePlayerDice(currentPlayer);
//            endTurn();
//        } else {
//            gameView.showMessage("Invalid placement on Brakes. Try again.");
//        }
//    }
//
//    public void placeDiceOnFlaps(int diceValue, int flapsIndex) {
//        if (currentPlayer instanceof CoPilot && flaps.activateFlap((CoPilot)currentPlayer, flapsIndex, diceValue)) {
//            currentPlayer.removeDice(diceValue);
//            gameView.updateFlapsStatus(flaps.getStatus());
//            gameView.updatePlayerDice(currentPlayer);
//            endTurn();
//        } else {
//            gameView.showMessage("Invalid placement on Flaps. Try again.");
//        }
//    }
//
//    private void endTurn() {
//        currentPlayer = (currentPlayer == pilot) ? coPilot : pilot;
//        gameView.highlightCurrentPlayer(currentPlayer);
//        updateGameState();
//    }
//
//    public void onDiceSelected(int diceValue) {
//        gameView.enableDicePlacement(true, diceValue);
//    }
//
//    public Player getCurrentPlayer() {
//        return currentPlayer;
//    }
//}
