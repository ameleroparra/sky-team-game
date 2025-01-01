package com.espabila.skyteam.view;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.espabila.skyteam.controller.GameController;

import java.util.List;


public class GamePlayScene implements ApplicationListener {
    private GameController gameController;
    private Texture backgroundTexture;
    private Music backgroundMusic;
    private Sound achievementSound;

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

    private boolean isPilotTurn = false;


    @Override
    public void create() {
        gameController = new GameController();
        gameController.startNewGame();
        isPilotTurn = !isPilotTurn;

        backgroundTexture = new Texture("fondo.png");
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("planeAmbience.wav"));
        backgroundMusic.setLooping(true);
        backgroundMusic.play();
        achievementSound = Gdx.audio.newSound(Gdx.files.internal("beep.wav"));

        batch = new SpriteBatch();
        viewport = new FitViewport(1920, 1080);
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


        // pilot radio slot creation
        Texture pilotRadioTexture = new Texture("noNumber.jpg");
        Image pilotRadioSlot = new Image(pilotRadioTexture);
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
        Texture firstCoPilotRadioTexture = new Texture("noNumber.jpg");
        Image firstCoPilotRadioSlot = new Image(firstCoPilotRadioTexture);
        firstCoPilotRadioSlot.setPosition(1490,960);
        firstCoPilotRadioSlot.setSize(100,100);
        stage.addActor(firstCoPilotRadioSlot);

        Texture secondCoPilotRadioTexture = new Texture("noNumber.jpg");
        Image secondCoPilotRadioSlot = new Image(secondCoPilotRadioTexture);
        secondCoPilotRadioSlot.setPosition(1618,960);
        secondCoPilotRadioSlot.setSize(100,100);
        stage.addActor(secondCoPilotRadioSlot);


        // Brakes slot creation
        Texture firstBrakesTexture = new Texture("noNumber.jpg");
        Image firstBrakesSlot = new Image(firstBrakesTexture);
        firstBrakesSlot.setPosition(110,525);
        firstBrakesSlot.setSize(100,100);
        stage.addActor(firstBrakesSlot);

        Texture secondBrakesTexture = new Texture("noNumber.jpg");
        Image secondBrakesSlot = new Image(secondBrakesTexture);
        secondBrakesSlot.setPosition(260,525);
        secondBrakesSlot.setSize(100,100);
        stage.addActor(secondBrakesSlot);

        Texture thirdBrakesTexture = new Texture("noNumber.jpg");
        Image thirdBrakesSlot = new Image(thirdBrakesTexture);
        thirdBrakesSlot.setPosition(408,525);
        thirdBrakesSlot.setSize(100,100);
        stage.addActor(thirdBrakesSlot);


        //coffee slots
        Texture firstCoffeeTexture = new Texture("noNumber.jpg");
        Image firstCoffeeSlot = new Image(firstCoffeeTexture);
        firstCoffeeSlot.setPosition(1417,525);
        firstCoffeeSlot.setSize(100,100);
        stage.addActor(firstCoffeeSlot);

        Texture secondCoffeeTexture = new Texture("noNumber.jpg");
        Image secondCoffeeSlot = new Image(secondCoffeeTexture);
        secondCoffeeSlot.setPosition(1565,525);
        secondCoffeeSlot.setSize(100,100);
        stage.addActor(secondCoffeeSlot);

        Texture thirdCoffeeTexture = new Texture("noNumber.jpg");
        Image thirdCoffeeSlot = new Image(thirdCoffeeTexture);
        thirdCoffeeSlot.setPosition(1715,525);
        thirdCoffeeSlot.setSize(100,100);
        stage.addActor(thirdCoffeeSlot);


        //Engine slot creation
        Texture pilotEngineTexture = new Texture("noNumber.jpg");
        Image pilotEngineSlot = new Image(pilotEngineTexture);
        pilotEngineSlot.setPosition(622,390);
        pilotEngineSlot.setSize(100,100);
        stage.addActor(pilotEngineSlot);

        Texture copilotEngineTexture = new Texture("noNumber.jpg");
        Image copilotEngineSlot = new Image(copilotEngineTexture);
        copilotEngineSlot.setPosition(1187,390);
        copilotEngineSlot.setSize(100,100);
        stage.addActor(copilotEngineSlot);


        // Axis slots creation
        Texture pilotAxisTexture = new Texture("noNumber.jpg");
        Image pilotAxisSlot = new Image(pilotAxisTexture);
        pilotAxisSlot.setPosition(768,225);
        pilotAxisSlot.setSize(100,100);
        stage.addActor(pilotAxisSlot);

        Texture copilotAxisTexture = new Texture("noNumber.jpg");
        Image copilotAxisSlot = new Image(copilotAxisTexture);
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

                isPilotTurn =!isPilotTurn;
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
            }
        });
        table.add(endTurnButton).bottom().width(200).height(50).padTop(950);



    }


    private void updateDiceImages() {
        List<Integer> currentPlayerDice = isPilotTurn ? gameController.getPilot().getDiceList() : gameController.getCoPilot().getDiceList();
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
            achievementSound.play(1.0f);

        }
    }


    private void placeDice(Image slot) {
        if (selectedDiceValue != 0) {
            slot.setDrawable(new Image(diceTextures[selectedDiceValue]).getDrawable());

            if (isPilotTurn) {
                gameController.getPilot().removeDice(selectedDiceValue);
            } else {
                gameController.getCoPilot().removeDice(selectedDiceValue);
            }

            updateDiceImages();
            selectedDiceValue = 0;
        }
    }







    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);

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
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }


    @Override
    public void dispose() {
        batch.dispose();
        backgroundTexture.dispose();
        backgroundMusic.dispose();
        achievementSound.dispose();
        stage.dispose();

        for (Texture texture : diceTextures) {
            texture.dispose();
        }
    }
}
