package com.espabila.skyteam;

import com.badlogic.gdx.Game;
import com.espabila.skyteam.controller.GameController;
import com.espabila.skyteam.view.GamePlayScene;

public class SkyTeamGame extends Game {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private GameController gameController;

    @Override
    public void create() {
        gameController = new GameController();
        setScreen(new GamePlayScene(this, gameController));
    }

    @Override
    public void dispose() {
        super.dispose();
        // Dispose of any resources here
    }
}
