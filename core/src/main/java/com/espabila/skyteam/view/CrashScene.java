package com.espabila.skyteam.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.espabila.skyteam.SkyTeamGame;
import com.espabila.skyteam.controller.GameController;

import java.util.Random;

public class CrashScene implements Screen {
    private Stage stage;
    private Skin skin;
    private Texture background;
    private Music crashMusic;
    private Array<Sound> electricity;
    private Random random;
    private Timer.Task soundTask;

    private SpriteBatch batch;
    private Table table;
    private final SkyTeamGame game;
    private GameController gameController;



    public CrashScene(SkyTeamGame game, GameController gameController) {
        this.game = game;
        this.gameController = new GameController();
        this.electricity = new Array<>();
        this.random = new Random();
    }

    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        background = new Texture("crash.png");
        batch = new SpriteBatch();

        crashMusic = Gdx.audio.newMusic(Gdx.files.internal("fire.mp3"));
        crashMusic.setVolume(0.75f);
        crashMusic.setLooping(true);
        crashMusic.play();
        electricity.add(Gdx.audio.newSound(Gdx.files.internal("electricity1.mp3")));
        electricity.add(Gdx.audio.newSound(Gdx.files.internal("electricity2.mp3")));
        electricity.add(Gdx.audio.newSound(Gdx.files.internal("electricity3.mp3")));

        soundTask = Timer.schedule(new Timer.Task() {
            public void run() {
                playRandomSound();
            }
        }, 0, 2);

        // Table creation
        table = new Table();
        table.setFillParent(true);
        table.center().pad(40);
        stage.addActor(table);

        // Buttons for table
        TextButton restartButton = new TextButton("Restart", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        table.add(restartButton).width(100).height(50).pad(25);
        table.add(exitButton).center().width(100).height(50).pad(25);

        restartButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                gameController.startNewGame();
                game.setScreen(new GamePlayScene(game, gameController));
                crashMusic.stop();
                soundTask.cancel();
                for (Sound sound : electricity) {
                    sound.stop();
                }
            }
        });

        exitButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });


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

    public void resize(int width, int height) {}

    public void pause() {}

    public void resume() {}

    public void hide() {}

    public void dispose() {
    }

    private void playRandomSound() {
        int index = random.nextInt(electricity.size);
        electricity.get(index).play();
    }
}
