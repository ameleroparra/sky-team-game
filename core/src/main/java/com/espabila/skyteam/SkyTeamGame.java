package com.espabila.skyteam;

import com.badlogic.gdx.Game;
import com.espabila.skyteam.controller.GameController;
import com.espabila.skyteam.view.GamePlayScene;

public class SkyTeamGame extends Game {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private GameController gameController;
    private GamePlayScene gamePlayScene;

    @Override
    public void create() {
        gameController = new GameController();
    }

    @Override
    public void dispose() {
        super.dispose();
        // Dispose of any resources here
    }
}
