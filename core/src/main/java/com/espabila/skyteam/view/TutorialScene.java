package com.espabila.skyteam.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.espabila.skyteam.SkyTeamGame;
import com.espabila.skyteam.controller.GameController;

public class TutorialScene implements Screen {

    private final SkyTeamGame game;
    private Skin skin;
    private Stage stage;

    private Texture background;
    private SpriteBatch batch;
    private Table table;


    private Texture[] tutorialTexture;
    private int currentIndex = 0;
    private Image tutorialImage;

    public TutorialScene(SkyTeamGame game) {
        this.game = game;
    }

    public void show() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        background = new Texture("tutorialBackground.png");
        batch = new SpriteBatch();

        // Table creation
        table = new Table();
        table.setFillParent(true);
        table.bottom().pad(40);
        stage.addActor(table);

        // Tutorial Images
        tutorialTexture = new Texture[2];
        tutorialTexture[0] = new Texture("tutorialRadio.png");
        tutorialTexture[1] = new Texture("tutorialMarkers.png");
        tutorialImage = new Image(tutorialTexture[currentIndex]);

        table.add(tutorialImage).colspan(3).center().width(1600).height(900).padBottom(20);
        table.row();

// Buttons for table
        TextButton backButton = new TextButton("Back", skin);
        TextButton closeButton = new TextButton("Close", skin);
        TextButton nextButton = new TextButton("Next", skin);

        table.add(backButton).left().width(100).height(50).pad(25);
        table.add(closeButton).center().width(100).height(50).pad(25);
        table.add(nextButton).right().width(100).height(50).pad(25);

        nextButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (currentIndex < tutorialTexture.length - 1) {
                    currentIndex = (currentIndex + 1);
                    tutorialImage.setDrawable(new Image(tutorialTexture[currentIndex]).getDrawable());
                }
            }
        });

        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (currentIndex > 0) {
                    currentIndex = (currentIndex - 1);
                    tutorialImage.setDrawable(new Image(tutorialTexture[currentIndex]).getDrawable());
                }
            }
        });

        closeButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScene(game, new GameController()));
            }
        });


        Gdx.input.setInputProcessor(stage);
    }

    public void resize(int width, int height) {}

    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public void pause() {}

    public void resume() {}

    public void hide() {}

    public void dispose() {
        stage.dispose();
        skin.dispose();
        batch.dispose();
        background.dispose();
        for (Texture texture : tutorialTexture) {
            texture.dispose();
        }

    }
}
