//package com.espabila.skyteam.view;
//
//import com.badlogic.gdx.ApplicationAdapter;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.ui.*;
//import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
//import com.badlogic.gdx.utils.viewport.FitViewport;
//import com.espabila.skyteam.controller.GameController;
//
//public class GameView extends ApplicationAdapter {
//    private GameController gameController;
//    private Stage stage;
//    private Skin skin;
//
////    void updateGameState(GameController.GameState state);
////    void showMessage(String message);
////    void highlightCurrentPlayer(Player player);
////    void updateAxisStatus(AxisStatus status);
////    void updateConcentrationStatus(ConcentrationStatus status);
////    void updateEnginesStatus(EnginesStatus status);
////    void updateRadioStatus(RadioStatus status);
////    void updateApproachTrackStatus(ApproachTrackStatus status);
////    void updateLandGearStatus(LandGearStatus status);
////    void updateBrakesStatus(BrakesStatus status);
////    void updateFlapsStatus(FlapsStatus status);
////    void updatePlayerDice(Player player);
////    void enableDicePlacement(boolean enable, int diceValue);
//
//    @Override
//    public void create() {
//        gameController = new GameController();
//        stage = new Stage(new FitViewport(800, 480));
//        skin = new Skin(Gdx.files.internal("uiskin.json"));
//
//        setupUI();
//
//        Gdx.input.setInputProcessor(stage);
//    }
//
//    private void setupUI() {
//        TextButton startButton = new TextButton("Start New Game", skin);
//        startButton.setPosition(400 - startButton.getWidth() / 2, 260);
//        startButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
//                gameController.startNewGame();
//                showRoleSelectionDialog();
//            }
//        });
//
//        TextButton quitButton = new TextButton("Quit", skin);
//        quitButton.setPosition(400 - quitButton.getWidth() / 2, 200);
//        quitButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
//                Gdx.app.exit();
//            }
//        });
//
//        stage.addActor(startButton);
//        stage.addActor(quitButton);
//    }
//
//    private void showRoleSelectionDialog() {
//        Dialog dialog = new Dialog("Select Roles", skin) {
//            protected void result(Object object) {
//                gameController.rolesSelection();
//                showStrategyDialog();
//            }
//        };
//        dialog.text("Decide who will be the Pilot and who will be the Co-Pilot.");
//        dialog.button("OK");
//        dialog.show(stage);
//    }
//
//    private void showStrategyDialog() {
//        Dialog dialog = new Dialog("Discuss Strategy", skin) {
//            protected void result(Object object) {
//                gameController.startPlaying();
//                setupGameBoard();
//            }
//        };
//        dialog.text("Discuss your strategy before starting the game (add more instructions).");
//        dialog.button("Start Game");
//        dialog.show(stage);
//    }
//
//    private void setupGameBoard() {
//        stage.clear();
//        Texture boardTexture = new Texture("board.png");
//        Image boardImage = new Image(boardTexture);
//        boardImage.setPosition(0, 0);
//        stage.addActor(boardImage);
//
//        // Add game elements (dice, slots, etc.)
//        setupDice();
//        setupAxis();
//        setupConcentration();
//        setupEngines();
//        setupRadio();
//        setupLandingGear();
//        setupBrakes();
//        setupFlaps();
//    }
//
//    private void setupDice() {

//    }
//
//    private void setupAxis() {

//    }
//
//    private void setupConcentration() {

//    }
//
//    private void setupEngines() {

//    }
//
//    private void setupRadio() {

//    }
//
//    private void setupLandingGear() {

//    }
//
//    private void setupBrakes() {

//    }
//
//    private void setupFlaps() {

//    }
//
//    @Override
//    public void render() {
//        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
//        stage.draw();
//

//        if (gameController.getCurrentState() == GameController.GameState.START_GAME) {

//            gameController.updateGameState();
//            if (gameController.isGameOver()) {
//                showGameOverDialog();
//            }
//        }
//    }
//
//    private void showGameOverDialog() {
//        String message = gameController.isWin() ? "Congratulations! You won!" : "Game Over. You lost.";
//        Dialog dialog = new Dialog("Game Over", skin) {
//            protected void result(Object object) {
//                setupUI(); // Reset to main menu
//            }
//        };
//        dialog.text(message);
//        dialog.button("Back to Main Menu");
//        dialog.show(stage);
//    }
//
//    @Override
//    public void resize(int width, int height) {
//        stage.getViewport().update(width, height, true);
//    }
//
//    @Override
//    public void dispose() {
//        stage.dispose();
//        skin.dispose();
//    }
//}
