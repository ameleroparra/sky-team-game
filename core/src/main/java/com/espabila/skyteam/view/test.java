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


public class test implements ApplicationListener {
    private GameController gameController;
    private Texture backgroundTexture;
    private Music backgroundMusic;
    private Sound achievementSound;
    private SpriteBatch batch;
    private FitViewport viewport;
    private Stage stage;
    Skin skin;

    private int[] diceValues;

    //dices code
    private Texture[] diceTextures;
    private Image[] diceImages;
    private static final int diceAmount = 4;


    @Override
    public void create() {
        gameController = new GameController();
        gameController.startNewGame();
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



        // pilot radio button creation and adding it to the stage
        Texture pilotRadioTexture = new Texture("noNumber.jpg");
        Image pilotRadioButton = new Image(pilotRadioTexture);
        pilotRadioButton.setPosition(295,960);
        pilotRadioButton.setSize(100,100);
        stage.addActor(pilotRadioButton);



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


        // table try
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);


        //button end turn
        TextButton endTurnButton = new TextButton("End Turn", skin);
        table.add(endTurnButton).bottom().width(200).height(50).padTop(950);



    }

    private void updateDiceImages() {
        List<Integer> pilotDice = gameController.getPilot().getDiceList();
        List<Integer> copilotDice = gameController.getCoPilot().getDiceList();

        diceValues = new int[diceAmount];

        for (int i = 0; i < diceAmount; i++) {
            diceValues[i] = pilotDice.get(i);
            diceImages[i].setDrawable(new Image(diceTextures[diceValues[i]]).getDrawable());
        }
    }

    private void diceSelected(int diceIndex) {
        System.out.println("Dice " + (diceValues[diceIndex]) + " selected");

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
