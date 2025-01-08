package com.espabila.skyteam;

import com.badlogic.gdx.Game;
import com.espabila.skyteam.controller.GameController;
import com.espabila.skyteam.view.MenuScene;
import com.espabila.skyteam.view.GamePlayScene;

public class SkyTeamGame extends Game {
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;

    private GameController gameController;

    @Override
    public void create() {
        gameController = new GameController();
        setScreen(new MenuScene(this, gameController));
    }

    @Override
    public void dispose() {
        super.dispose();
        // Dispose of any resources here
    }
}
