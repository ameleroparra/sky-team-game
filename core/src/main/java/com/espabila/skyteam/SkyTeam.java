package com.espabila.skyteam;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.github.czyzby.autumn.mvc.stereotype.View;
import com.github.czyzby.autumn.mvc.stereotype.preference.Skin;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.parser.action.ActionContainer;

/** The first application's view. See first.lml file for widgets layout. */
@View(id = "first", value = "ui/templates/first.lml", first = true)
public class SkyTeam implements ActionContainer {
    /** Default application size. */
    public static final int WIDTH = 640, HEIGHT = 480;
    /** Path to the skin files. */
    @Skin private final String skinFile = "ui/uiskin";

    /** Since this method is annotated with LmlAction and this class implements ActionContainer, this method will be
     * available in the LML template: first.lml
     * @param button its text will be changed. */
    @LmlAction("setClicked")
    public void changeButtonText(TextButton button) {
        button.setText("Clicked.");
    }
}