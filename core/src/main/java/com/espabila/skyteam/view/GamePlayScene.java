package com.espabila.skyteam.view;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.espabila.skyteam.SkyTeamGame;
import com.espabila.skyteam.controller.GameController;
import com.espabila.skyteam.model.CoPilot;
import com.espabila.skyteam.model.Pilot;
import com.espabila.skyteam.model.Player;

import java.util.List;


public class GamePlayScene implements Screen {
    private GameController gameController;
    private Texture backgroundTexture;
    private Music backgroundMusic;
    private Sound selectedSound;
    private Sound placedSound;
    private Sound movementSound;

    private SpriteBatch batch;
    private FitViewport viewport;
    private Stage stage;
    Skin skin;

    private int[] diceValues;
    private int selectedDiceValue = 0;

    //dices code
    private Texture[] diceTextures;
    private Image[] diceImages;
    private static final int diceAmount = 4;

    // Radio slots
    private Image pilotRadioSlot;
    private Image firstCoPilotRadioSlot;
    private Image secondCoPilotRadioSlot;

    // Brakes slots
    private Image firstBrakesSlot;
    private Image secondBrakesSlot;
    private Image thirdBrakesSlot;

    // Coffee slots
    private Image firstCoffeeSlot;
    private Image secondCoffeeSlot;
    private Image thirdCoffeeSlot;

    // Engine slots
    private Image pilotEngineSlot;
    private Image copilotEngineSlot;

    // Axis slots
    private Image pilotAxisSlot;
    private Image copilotAxisSlot;

    // Landing gear slots
    private Image firstLandGearSlot;
    private Image secondLandGearSlot;
    private Image thirdLandGearSlot;

    // Flaps slot
    private Image firstFlapsSlot;
    private Image secondFlapsSlot;
    private Image thirdFlapsSlot;
    private Image fourthFlapsSlot;

    private Texture emptySlotTexture;

    private boolean isPilotTurn = false;

    private final SkyTeamGame game;

    public GamePlayScene(SkyTeamGame game, GameController gameController) {
        this.game = game;
        this.gameController = gameController;

        // Initialize components

        stage = new Stage(new FitViewport(SkyTeamGame.WIDTH, SkyTeamGame.HEIGHT));
        Gdx.input.setInputProcessor(stage);

        // Initialize skin
        skin = new Skin(Gdx.files.internal("uiskin.json")); // Make sure this file exists in your assets folder

        // Initialize your UI components here
    }


    @Override
    public void show() {
        gameController = new GameController();
        gameController.startNewGame();
        isPilotTurn = !isPilotTurn;

        backgroundTexture = new Texture("background.png");
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("planeAmbience.wav"));
        backgroundMusic.setLooping(true);
        backgroundMusic.play();
        selectedSound = Gdx.audio.newSound(Gdx.files.internal("beep.wav"));
        placedSound = Gdx.audio.newSound(Gdx.files.internal("ding.wav"));
        movementSound = Gdx.audio.newSound(Gdx.files.internal("movement.mp3"));

        batch = new SpriteBatch();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));


        // dice code
        diceTextures = new Texture[7];
        diceTextures[0] = new Texture("noNumber.jpg"); // no number initial

        for (int i = 1; i <= 6; i++) {
            diceTextures[i] = new Texture("number" + i + ".jpg"); // generate number textures
        }
        diceImages = new Image[diceAmount];

        for (int i = 0; i < diceAmount; i++) {
            final int index = i;
            diceImages[i] = new Image(diceTextures[0]);
            diceImages[i].setSize(100, 100);
            diceImages[i].setPosition(719 + i * (100 + 28), 960);

            diceImages[i].addListener(new ClickListener() { //make images clickable
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    diceSelected(index);
                }
            });
            stage.addActor(diceImages[i]);

        }

        updateDiceImages();

        emptySlotTexture = new Texture("noNumber.jpg");
        // pilot radio slot creation
        pilotRadioSlot = new Image(emptySlotTexture);
        pilotRadioSlot.setPosition(295,960);
        pilotRadioSlot.setSize(100,100);


        pilotRadioSlot.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                placeDice(pilotRadioSlot);
            }
        });
        stage.addActor(pilotRadioSlot);


        // copilot radio slot creation
        firstCoPilotRadioSlot = new Image(emptySlotTexture);
        firstCoPilotRadioSlot.setPosition(1490,960);
        firstCoPilotRadioSlot.setSize(100,100);
        stage.addActor(firstCoPilotRadioSlot);

        secondCoPilotRadioSlot = new Image(emptySlotTexture);
        secondCoPilotRadioSlot.setPosition(1618,960);
        secondCoPilotRadioSlot.setSize(100,100);
        stage.addActor(secondCoPilotRadioSlot);


        // Brakes slot creation
        firstBrakesSlot = new Image(emptySlotTexture);
        firstBrakesSlot.setPosition(110,525);
        firstBrakesSlot.setSize(100,100);
        stage.addActor(firstBrakesSlot);

        secondBrakesSlot = new Image(emptySlotTexture);
        secondBrakesSlot.setPosition(260,525);
        secondBrakesSlot.setSize(100,100);
        stage.addActor(secondBrakesSlot);

        thirdBrakesSlot = new Image(emptySlotTexture);
        thirdBrakesSlot.setPosition(408,525);
        thirdBrakesSlot.setSize(100,100);
        stage.addActor(thirdBrakesSlot);


        //coffee slots
        firstCoffeeSlot = new Image(emptySlotTexture);
        firstCoffeeSlot.setPosition(1417,525);
        firstCoffeeSlot.setSize(100,100);
        stage.addActor(firstCoffeeSlot);

        secondCoffeeSlot = new Image(emptySlotTexture);
        secondCoffeeSlot.setPosition(1565,525);
        secondCoffeeSlot.setSize(100,100);
        stage.addActor(secondCoffeeSlot);

        thirdCoffeeSlot = new Image(emptySlotTexture);
        thirdCoffeeSlot.setPosition(1715,525);
        thirdCoffeeSlot.setSize(100,100);
        stage.addActor(thirdCoffeeSlot);


        //Engine slot creation
        pilotEngineSlot = new Image(emptySlotTexture);
        pilotEngineSlot.setPosition(622,390);
        pilotEngineSlot.setSize(100,100);
        stage.addActor(pilotEngineSlot);

        copilotEngineSlot = new Image(emptySlotTexture);
        copilotEngineSlot.setPosition(1187,390);
        copilotEngineSlot.setSize(100,100);
        stage.addActor(copilotEngineSlot);


        // Axis slots creation
        pilotAxisSlot = new Image(emptySlotTexture);
        pilotAxisSlot.setPosition(768,225);
        pilotAxisSlot.setSize(100,100);
        stage.addActor(pilotAxisSlot);

        copilotAxisSlot = new Image(emptySlotTexture);
        copilotAxisSlot.setPosition(1065,228);
        copilotAxisSlot.setSize(100,100);
        stage.addActor(copilotAxisSlot);

        // table creation
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);


        //Change turns System
        Image blurryScreen = new Image(new Texture("blurry.png")); //creates blurry background
        blurryScreen.setVisible(false);
        stage.addActor(blurryScreen);

        TextButton readyButton = new TextButton("Ready", skin); //creates button to exit blurry background
        readyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                blurryScreen.setVisible(false);
                readyButton.setVisible(false);
                readyButton.toFront();

                movementSound.play(1.0f);
                gameController.switchTurn();
                updateDiceImages();
            }
        });
        readyButton.setSize(200, 50);
        readyButton.setVisible(false);
        stage.addActor(readyButton);

        TextButton endTurnButton = new TextButton("End Turn", skin); //creates button to enter blurry background
        endTurnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                blurryScreen.setVisible(true);
                readyButton.setVisible(true);
                readyButton.toFront();
                movementSound.play(1.0f);
            }
        });
        table.add(endTurnButton).bottom().width(200).height(50).padTop(950);

    }


    private void updateDiceImages() {
        List<Integer> currentPlayerDice = gameController.getCurrentPlayer().getDiceList();
        diceValues = new int[diceAmount];

        for (int i = 0; i < diceAmount; i++) {
            if (i < currentPlayerDice.size()) {
                diceValues[i] = currentPlayerDice.get(i);
                diceImages[i].setDrawable(new Image(diceTextures[diceValues[i]]).getDrawable());
            } else {
                diceValues[i] = 0;
                diceImages[i].setDrawable(new Image(diceTextures[0]).getDrawable());
            }
        }
    }


    private void diceSelected(int diceIndex) {
        if (diceValues[diceIndex] != 0) {
            selectedDiceValue = diceValues[diceIndex];
            selectedSound.play(1.0f);
        }
    }

    private void placeDice(Image slot) {
        if (selectedDiceValue != 0) {
            try {
                // Determine which component the dice is being placed on
                if (slot == pilotAxisSlot || slot == copilotAxisSlot) {
                    if (isCorrectPlayerSlot(slot)) {
                        gameController.placeDiceOnAxis(selectedDiceValue);
                    }

                } else if (slot == firstCoffeeSlot || slot == secondCoffeeSlot || slot == thirdCoffeeSlot) {
                    if (isCorrectPlayerSlot(slot)) {
                        int slotNumber = slot == firstCoffeeSlot ? 1 : (slot == secondCoffeeSlot ? 2 : 3);
                        gameController.placeDiceOnConcentration(selectedDiceValue, slotNumber);
                    }

                } else if (slot == pilotEngineSlot || slot == copilotEngineSlot) {
                    gameController.placeDiceOnEngines(selectedDiceValue);

                } else if (slot == pilotRadioSlot){
                    gameController.placeDiceOnPilotSlot(selectedDiceValue);

                }
                else if (slot == firstCoPilotRadioSlot || slot == secondCoPilotRadioSlot) {
                    if (isCorrectPlayerSlot(slot)) {
                        gameController.placeDiceOnRadio(selectedDiceValue);
                    }
                    else{
                        showErrorMessage("This is Pilot's slots.");
                    }

                } else if (slot == firstLandGearSlot || slot == secondLandGearSlot || slot == thirdLandGearSlot) {
                    int gearIndex = slot == firstLandGearSlot ? 0 : (slot == secondLandGearSlot ? 1 : 2);
                    gameController.placeDiceOnLandGear(selectedDiceValue, gearIndex);

                } else if (slot == firstBrakesSlot || slot == secondBrakesSlot || slot == thirdBrakesSlot) {
                    int brakeIndex = slot == firstBrakesSlot ? 0 : (slot == secondBrakesSlot ? 1 : 2);
                    gameController.placeDiceOnBrakes(selectedDiceValue, brakeIndex);

                } else if (slot == firstFlapsSlot || slot == secondFlapsSlot || slot == thirdFlapsSlot || slot == fourthFlapsSlot) {
                    int flapIndex = slot == firstFlapsSlot ? 0 : (slot == secondFlapsSlot ? 1 : (slot == thirdFlapsSlot ? 2 : 3));
                    gameController.placeDiceOnFlaps(selectedDiceValue, flapIndex);

                } else {
                    throw new IllegalArgumentException("Invalid slot selected");
                }

                slot.setDrawable(new Image(diceTextures[selectedDiceValue]).getDrawable());
                placedSound.play(1.0f);
                updateDiceImages();
                selectedDiceValue = 0;
//                    checkEndTurn();

                if (gameController.isGameOver()) {
                    showGameOverDialog();
                }
            } catch (Exception e) {
                showErrorMessage("Invalid placement: " + e.getMessage());
            }
        }
    }

    private boolean isCorrectPlayerSlot(Image slot) {
        Player currentPlayer = gameController.getCurrentPlayer();
        if (currentPlayer instanceof Pilot) {
            return slot == pilotAxisSlot || slot == pilotRadioSlot || slot == pilotEngineSlot;
        } else if (currentPlayer instanceof CoPilot) {
            return slot == copilotAxisSlot || slot == firstCoPilotRadioSlot || slot == secondCoPilotRadioSlot || slot == copilotEngineSlot;
        }
        else {
            System.out.println("Warning: Unknown player type: " + currentPlayer.getClass().getSimpleName());
            return false;
        }
    }

    public void showErrorMessage(String errorMessage) {
        if (skin == null) {
            System.err.println("Error: Skin is not initialized. Error message: " + errorMessage);
            return;
        }
        Dialog dialog = new Dialog("Error", skin);
        dialog.text(errorMessage);
        dialog.button("OK");
        dialog.show(stage);
    }

    private void showGameOverDialog() {
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        stage.getViewport().update(width, height, true);
    }


    @Override
    public void pause() {
        backgroundMusic.pause();
    }


    @Override
    public void resume() {
        backgroundMusic.play();
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        // Calculate the scaling factors
        float scaleX = viewport.getWorldWidth() / backgroundTexture.getWidth();
        float scaleY = viewport.getWorldHeight() / backgroundTexture.getHeight();
        float scale = Math.max(scaleX, scaleY);

        // Calculate the dimensions to maintain aspect ratio
        float width = backgroundTexture.getWidth() * scale;
        float height = backgroundTexture.getHeight() * scale;

        // Calculate position to center the background
        float x = (viewport.getWorldWidth() - width) / 2;
        float y = (viewport.getWorldHeight() - height) / 2;

        // Draw the background
        batch.draw(backgroundTexture, x, y, width, height);

        batch.end();

        stage.getViewport().apply();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void hide() {
        // This method is called when this screen is no longer the current screen for a Game.
        dispose();
    }


    @Override
    public void dispose() {
        batch.dispose();
        backgroundTexture.dispose();
        backgroundMusic.dispose();
        selectedSound.dispose();
        stage.dispose();

        for (Texture texture : diceTextures) {
            texture.dispose();
        }
    }
}
