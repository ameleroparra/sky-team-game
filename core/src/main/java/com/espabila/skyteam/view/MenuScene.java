package com.espabila.skyteam.view;
import com.espabila.skyteam.SkyTeamGame;
import com.espabila.skyteam.controller.GameController;
import com.espabila.skyteam.view.TutorialScene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MenuScene implements Screen {
    private Stage stage;
    private Skin skin;
    private final SkyTeamGame game;
    private Texture background;
    private final GameController gameController;
    private SpriteBatch batch;

    public MenuScene(SkyTeamGame game, GameController gameController) {
        this.game = game;
        this.gameController = new GameController();

    }

    public void show() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        background = new Texture("menuLogo.png");
        batch = new SpriteBatch();

        Table table = new Table();
        table.setFillParent(true);
        table.left().pad(40).bottom().pad(40);
        stage.addActor(table);

        TextButton playButton = new TextButton("Play", skin);
        TextButton tutorialButton = new TextButton("Tutorial", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        table.add(playButton).left().pad(25).width(600).height(150);
        table.row();
        table.add(tutorialButton).left().pad(25).width(600).height(150);
        table.row();
        table.add(exitButton).left().pad(25).width(600).height(150);

        playButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                GameController gameController = new GameController();
                game.setScreen(new GamePlayScene(game, gameController));
            }
        });

        tutorialButton.addListener(new ClickListener() {
           public void clicked(InputEvent event, float x, float y) {
               game.setScreen(new TutorialScene(game));
           }

        });

        exitButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });


        Gdx.input.setInputProcessor(stage);
    }


    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

    }


    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }


    public void pause() {

    }


    public void resume() {

    }


    public void hide() {

    }


    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
