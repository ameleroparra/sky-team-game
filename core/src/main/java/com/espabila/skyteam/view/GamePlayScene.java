package com.espabila.skyteam.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.espabila.skyteam.SkyTeamGame;
import com.espabila.skyteam.controller.GameController;
import com.espabila.skyteam.model.CoPilot;
import com.espabila.skyteam.model.Pilot;
import com.espabila.skyteam.model.Player;

import java.util.List;


public class GamePlayScene implements Screen {
    private final GameController gameController;
    private Texture backgroundTexture;
    private Music backgroundMusic;
    private Sound selectedSound;
    private Sound placedSound;

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

    // Coffee related things
    private Image firstCoffeeSlot;
    private Image secondCoffeeSlot;
    private Image thirdCoffeeSlot;
    private Texture coffeeTexture;

    private Image diceSelectImage;
    private Texture diceSelectTexture;
    private Image valueMinusOneImage;
    private Image valuePlusOneImage;

    // Engine slots
    private Image pilotEngineSlot;
    private Image copilotEngineSlot;

    // Axis slots
    private Texture axisPlaneTexture;
    private Image axisPlaneImage;
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

    // Altitude Tracks slot
    private Texture[] altitudeTextures;
    private Image altitudeTrackSlot;

    // Approach Tracks slots
    private Texture[] approachTextures;
    private Image[] approachTrackSlots;
    private static final int approachTrackAmount = 7;

    // Reroll slots
    private Texture rerollTexture;
    private Image rerollSlot;

    // Markers slots
    private Texture lowMarkerTexture;
    private Texture highMarkerTexture;
    private Image lowMarkerImage;
    private Image highMarkerImage;

    // General slots
    private Texture emptySlotTexture;
    private Texture noRerollTexture;
    private Texture tickIcon;

    // Change Turn button
    private Image blurryScreen;
    private TextButton readyButton;
    private Sound movementSound;



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

        gameController.setGamePlayScene(this);
        // Initialize your UI components here
    }

    @Override
    public void show() {
        gameController.startNewGame();
        isPilotTurn = !isPilotTurn;

        backgroundTexture = new Texture("background.png");
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("planeAmbience.wav"));
        backgroundMusic.setLooping(true);
        backgroundMusic.play();
        selectedSound = Gdx.audio.newSound(Gdx.files.internal("beep.wav"));
        placedSound = Gdx.audio.newSound(Gdx.files.internal("ding.wav"));
        movementSound = Gdx.audio.newSound(Gdx.files.internal("movement.mp3"));

        tickIcon = new Texture("tickIcon.jpg");
        axisPlaneTexture = new Texture("planeAxisIcon.png");
        lowMarkerTexture = new Texture("lowMarkerIcon.png");
        highMarkerTexture = new Texture("highMarkerIcon.png");
        coffeeTexture = new Texture("coffeeIcon.jpg");
        diceSelectTexture = new Texture("diceSelect.png");

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
            public void clicked(InputEvent event, float x, float y) { // make slot clickable
                if (gameController.getCurrentPlayer() instanceof Pilot) {
                    placeDice(pilotRadioSlot);
                }
                else {
                    showErrorMessage("Only the pilot can use this slot.");
                }
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

        firstBrakesSlot.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) { // make slot clickable
                placeDice(firstBrakesSlot);
            }
        });
        stage.addActor(firstBrakesSlot);

        secondBrakesSlot = new Image(emptySlotTexture);
        secondBrakesSlot.setPosition(260,525);
        secondBrakesSlot.setSize(100,100);
        stage.addActor(secondBrakesSlot);

        secondBrakesSlot.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) { // make slot clickable
                placeDice(secondBrakesSlot);
            }
        });

        thirdBrakesSlot = new Image(emptySlotTexture);
        thirdBrakesSlot.setPosition(408,525);
        thirdBrakesSlot.setSize(100,100);
        stage.addActor(thirdBrakesSlot);

        thirdBrakesSlot.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) { // make slot clickable
                placeDice(thirdBrakesSlot);
            }
        });


        //coffee slots
        firstCoffeeSlot = new Image(emptySlotTexture);
        firstCoffeeSlot.setPosition(1417,525);
        firstCoffeeSlot.setSize(100,100);
        stage.addActor(firstCoffeeSlot);
        firstCoffeeSlot.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) { // make slot clickable

                placeDice(firstCoffeeSlot);

            }
        });

        secondCoffeeSlot = new Image(emptySlotTexture);
        secondCoffeeSlot.setPosition(1565,525);
        secondCoffeeSlot.setSize(100,100);
        stage.addActor(secondCoffeeSlot);
        secondCoffeeSlot.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) { // make slot clickable

                placeDice(secondCoffeeSlot);
            }
        });

        thirdCoffeeSlot = new Image(emptySlotTexture);
        thirdCoffeeSlot.setPosition(1715,525);
        thirdCoffeeSlot.setSize(100,100);
        stage.addActor(thirdCoffeeSlot);
        thirdCoffeeSlot.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) { // make slot clickable

                placeDice(thirdCoffeeSlot);
            }
        });

        // Landing gear slot creation
        firstLandGearSlot = new Image(emptySlotTexture);
        firstLandGearSlot.setPosition(295,279);
        firstLandGearSlot.setSize(100,100);
        stage.addActor(firstLandGearSlot);
        firstLandGearSlot.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) { // make slot clickable
                if (gameController.getCurrentPlayer() instanceof Pilot) {
                    placeDice(firstLandGearSlot);
                }
                else {
                    showErrorMessage("Only the pilot can interact with this slot.");
                }
            }
        });

        secondLandGearSlot = new Image(emptySlotTexture);
        secondLandGearSlot.setPosition(153,180);
        secondLandGearSlot.setSize(100,100);
        stage.addActor(secondLandGearSlot);
        secondLandGearSlot.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) { // make slot clickable
                if (gameController.getCurrentPlayer() instanceof Pilot) {
                    placeDice(secondLandGearSlot);
                }
                else {
                    showErrorMessage("Only the pilot can interact with this slot.");
                }
            }
        });

        thirdLandGearSlot = new Image(emptySlotTexture);
        thirdLandGearSlot.setPosition(10,110);
        thirdLandGearSlot.setSize(100,100);
        stage.addActor(thirdLandGearSlot);
        thirdLandGearSlot.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) { // make slot clickable
                if (gameController.getCurrentPlayer() instanceof Pilot) {
                    placeDice(thirdLandGearSlot);
                }
                else {
                    showErrorMessage("Only the pilot can interact with this slot.");
                }
            }
        });

        lowMarkerImage = new Image(lowMarkerTexture);
        lowMarkerImage.setPosition(890, 390);
        stage.addActor(lowMarkerImage);

        // Flaps slot creation
        firstFlapsSlot = new Image(emptySlotTexture);
        firstFlapsSlot.setPosition(1381,359);
        firstFlapsSlot.setSize(100,100);
        stage.addActor(firstFlapsSlot);
        firstFlapsSlot.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) { // make slot clickable
                if (gameController.getCurrentPlayer() instanceof CoPilot) {
                    placeDice(firstFlapsSlot);
                }
                else {
                    showErrorMessage("Only the copilot can interact with this slot.");
                }
            }
        });

        secondFlapsSlot = new Image(emptySlotTexture);
        secondFlapsSlot.setPosition(1523,279);
        secondFlapsSlot.setSize(100,100);
        stage.addActor(secondFlapsSlot);
        secondFlapsSlot.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) { // make slot clickable
                if (gameController.getCurrentPlayer() instanceof CoPilot) {
                    placeDice(secondFlapsSlot);
                }
                else {
                    showErrorMessage("Only the copilot can interact with this slot.");
                }
            }
        });

        thirdFlapsSlot = new Image(emptySlotTexture);
        thirdFlapsSlot.setPosition(1665,181);
        thirdFlapsSlot.setSize(100,100);
        stage.addActor(thirdFlapsSlot);
        thirdFlapsSlot.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) { // make slot clickable
                if (gameController.getCurrentPlayer() instanceof CoPilot) {
                    placeDice(thirdFlapsSlot);
                }
                else {
                    showErrorMessage("Only the copilot can interact with this slot.");
                }
            }
        });

        fourthFlapsSlot = new Image(emptySlotTexture);
        fourthFlapsSlot.setPosition(1808,110);
        fourthFlapsSlot.setSize(100,100);
        stage.addActor(fourthFlapsSlot);
        fourthFlapsSlot.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) { // make slot clickable
                if (gameController.getCurrentPlayer() instanceof CoPilot) {
                    placeDice(fourthFlapsSlot);
                }
                else {
                    showErrorMessage("Only the copilot can interact with this slot.");
                }
            }
        });

        highMarkerImage = new Image(highMarkerTexture);
        highMarkerImage.setPosition(1005, 390);
        stage.addActor(highMarkerImage);

        //Engine slot creation
        pilotEngineSlot = new Image(emptySlotTexture);
        pilotEngineSlot.setPosition(622,390);
        pilotEngineSlot.setSize(100,100);
        stage.addActor(pilotEngineSlot);
        pilotEngineSlot.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) { // make slot clickable
                if (gameController.getCurrentPlayer() instanceof Pilot) {
                    placeDice(pilotEngineSlot);
//                    gameController.moveForwardApproachTrack();
                } else {
                    showErrorMessage("Only the pilot can interact with this slot.");
                }
            }
        });

        copilotEngineSlot = new Image(emptySlotTexture);
        copilotEngineSlot.setPosition(1187,390);
        copilotEngineSlot.setSize(100,100);
        stage.addActor(copilotEngineSlot);
        copilotEngineSlot.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) { // make slot clickable
                if (gameController.getCurrentPlayer() instanceof CoPilot) {
                    placeDice(copilotEngineSlot);
//                    gameController.moveForwardApproachTrack();
                } else {
                    showErrorMessage("Only the copilot can interact with this slot.");
                }
            }
        });

        // Axis slots creation
        pilotAxisSlot = new Image(emptySlotTexture);
        pilotAxisSlot.setPosition(768,225);
        pilotAxisSlot.setSize(100,100);
        stage.addActor(pilotAxisSlot);
        pilotAxisSlot.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) { // make slot clickable
                if (gameController.getCurrentPlayer() instanceof Pilot) {
                    placeDice(pilotAxisSlot);
                } else {
                    showErrorMessage("Only the pilot can interact with this slot.");
                }
            }
        });

        copilotAxisSlot = new Image(emptySlotTexture);
        copilotAxisSlot.setPosition(1065,228);
        copilotAxisSlot.setSize(100,100);
        stage.addActor(copilotAxisSlot);
        copilotAxisSlot.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) { // make slot clickable
                if (gameController.getCurrentPlayer() instanceof CoPilot) {
                    placeDice(copilotAxisSlot);
                } else {
                    showErrorMessage("Only the copilot can interact with this slot.");
                }
            }
        });

        axisPlaneImage = new Image(axisPlaneTexture);
        axisPlaneImage.setPosition(928,268);
        axisPlaneImage.setOrigin(axisPlaneTexture.getWidth() / 2, axisPlaneTexture.getHeight() / 2);
        stage.addActor(axisPlaneImage);

        // Altitude track slots creation
        altitudeTextures = new Texture[7];
        for (int i = 0; i <= 6; i++) {
            altitudeTextures[i] = new Texture("altitude" + i + ".jpg"); // generate altitude textures
        }
        altitudeTrackSlot = new Image(altitudeTextures[6]);
        altitudeTrackSlot.setPosition(1100,875);
        stage.addActor(altitudeTrackSlot);

        // Reroll slot creation
        rerollTexture = new Texture("rerollIcon.jpg");
        noRerollTexture = new Texture("noRerollIcon.jpg");
        rerollSlot = new Image(noRerollTexture);
        rerollSlot.setPosition(1335, 875);
        rerollSlot.setSize(50, 50);
        stage.addActor(rerollSlot);

        // Approach track slots creation
        approachTextures = new Texture[5]; // create textures
        for (int i = 0; i <= 4; i++) {
            approachTextures[i] = new Texture("approach" + i + ".jpg"); // generate approach textures
        }

        approachTrackSlots = new Image[approachTrackAmount]; // create slots
        for (int i = 0; i < approachTrackAmount; i++) {
            approachTrackSlots[i] = new Image(approachTextures[0]);
            approachTrackSlots[i].setPosition(860, 531 + i * 50);
            stage.addActor(approachTrackSlots[i]);
        }

        updateApproachTrackVisuals();

        // table creation
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);


        //Change turns System
        blurryScreen = new Image(new Texture("blurry.png")); //creates blurry background
        blurryScreen.setVisible(false);
        stage.addActor(blurryScreen);

        readyButton = new TextButton("Ready", skin); //creates button to exit blurry background
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

        //Dice selection System
        diceSelectImage = new Image(diceSelectTexture);
        diceSelectImage.setVisible(false);
        stage.addActor(diceSelectImage);

        valueMinusOneImage = new Image(emptySlotTexture);
        valueMinusOneImage.setPosition(312, 295);
        valueMinusOneImage.setVisible(false);
        stage.addActor(valueMinusOneImage);
        valueMinusOneImage.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                updateDiceImages();
            }
        });

        valuePlusOneImage = new Image(emptySlotTexture);
        valuePlusOneImage.setPosition(1110, 295);
        valuePlusOneImage.setVisible(false);
        stage.addActor(valuePlusOneImage);
        valuePlusOneImage.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                updateDiceImages();
            }
        });

    }

    // show concentration background
    public void showDiceSelectImages(int diceValue) {
        diceSelectImage.setVisible(true);

        int minusOneValue = Math.max(1, diceValue - 1);
        int plusOneValue = Math.min(6, diceValue + 1);

        valueMinusOneImage.setDrawable(new TextureRegionDrawable(diceTextures[minusOneValue]));
        valueMinusOneImage.setVisible(true);

        valuePlusOneImage.setDrawable(new TextureRegionDrawable(diceTextures[plusOneValue]));
        valuePlusOneImage.setVisible(true);
    }

    public void updateAxisPlaneVisuals(int currentPosition) {
        int angle = currentPosition * -30;
        int duration = 1;
        axisPlaneImage.addAction(Actions.rotateTo(angle, duration));
    }

    public void updateLowMarkerVisuals (int activatedGears) {
        int duration = 1;
        int movement = 30;
        lowMarkerImage.addAction(Actions.moveBy(movement * (activatedGears), 0, duration));
    }


    public void updateHighMarkerVisuals (int activatedGears) {
        int duration = 1;
        int movement = 35;
        highMarkerImage.addAction(Actions.moveBy(movement * (activatedGears), 0, duration));
    }

    // Place a tick icon when a brake is activated
    public void updateBrakeVisuals(int brakeSlot, boolean activated) {
        Image slotImage;
        switch (brakeSlot) {
            case 0:
                slotImage = firstBrakesSlot;
                break;
            case 1:
                slotImage = secondBrakesSlot;
                break;
            case 2:
                slotImage = thirdBrakesSlot;
                break;
            default:
                return; // Invalid brake slot
        }

        if (activated) {
            slotImage.setDrawable(new TextureRegionDrawable(tickIcon));
            placedSound.play(1.0f);
            selectedDiceValue = 0;
            changeTurn();
        } else {
            slotImage.setDrawable(new TextureRegionDrawable(emptySlotTexture));
        }
    }

    // place a coffee icon when a coffee is activated
    public void updateCoffeeVisuals(int coffeeSlot, boolean activated) {
        Image slotImage;
        switch (coffeeSlot) {
            case 0:
                slotImage = firstCoffeeSlot;
                break;
            case 1:
                slotImage = secondCoffeeSlot;
                break;
            case 2:
                slotImage = thirdCoffeeSlot;
                break;
            default:
                return; // Invalid coffee slot
        }

        if (activated) {
            slotImage.setDrawable(new TextureRegionDrawable(coffeeTexture));
            slotImage.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    showErrorMessage("choose a dice now");
                }
            });

            placedSound.play(1.0f);
            changeTurn();
            selectedDiceValue = 0;
        } else {
            slotImage.setDrawable(new TextureRegionDrawable(emptySlotTexture));
        }
    }

    public void diceSelected(int diceIndex) {
        if (diceValues[diceIndex] != 0) {
            selectedDiceValue = diceValues[diceIndex];
            selectedSound.play(1.0f);
        }
    }

    // Update the dice array
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

    private void placeDice(Image slot) {
        if (selectedDiceValue != 0) {
            try {
                boolean placementSuccessful = false;

                if (!(slot.getDrawable() instanceof TextureRegionDrawable) ||
                    ((TextureRegionDrawable)slot.getDrawable()).getRegion().getTexture() != emptySlotTexture) {
                    showErrorMessage("This slot is already occupied.");
                    return;
                }

                // Determine which component the dice is being placed on
                // Engines
                if (slot == pilotEngineSlot || slot == copilotEngineSlot) {
                    gameController.placeDiceOnEngines(selectedDiceValue);
                    placementSuccessful = true;

                }

                // Axis
                else if (slot == pilotAxisSlot || slot == copilotAxisSlot) {
                    gameController.placeDiceOnAxis(selectedDiceValue);
                    placementSuccessful = true;
                }

                // Land Gear
                else if (slot == firstLandGearSlot || slot == secondLandGearSlot || slot == thirdLandGearSlot) {
                    int gearIndex;
                    if (slot == firstLandGearSlot) {
                        gearIndex = 0;
                    } else if (slot == secondLandGearSlot) {
                        gearIndex = 1;
                    } else if (slot == thirdLandGearSlot) {
                        gearIndex = 2;
                    } else {
                        return;
                    }

                    placementSuccessful = gameController.placeDiceOnLandGear(selectedDiceValue, gearIndex);
                    if (placementSuccessful) {
                       updateLowMarkerVisuals(1);
                    }
                    else {
                        showErrorMessage("invalid dice value");
                    }


                }

                // Flaps
                else if (slot == firstFlapsSlot || slot == secondFlapsSlot || slot == thirdFlapsSlot || slot == fourthFlapsSlot) {
                    int flapIndex;
                    if (slot == firstFlapsSlot) {
                        flapIndex = 0;
                    } else if (slot == secondFlapsSlot) {
                        flapIndex = 1;
                    } else if (slot == thirdFlapsSlot) {
                        flapIndex = 2;
                    } else if (slot == fourthFlapsSlot) {
                        flapIndex = 3;
                    } else {
                        return;
                    }

                    placementSuccessful = gameController.placeDiceOnFlaps(selectedDiceValue, flapIndex);
                    if (placementSuccessful) {
                        updateHighMarkerVisuals(1);
                    }
                    else {
                        showErrorMessage("you can not do that");
                    }

                }

                // Brakes
                else if (slot == firstBrakesSlot || slot == secondBrakesSlot || slot == thirdBrakesSlot) {
                    int brakeIndex;
                    if (slot == firstBrakesSlot) {
                        brakeIndex = 0;
                    } else if (slot == secondBrakesSlot) {
                        brakeIndex = 1;
                    } else if (slot == thirdBrakesSlot) {
                        brakeIndex = 2;
                    } else {
                        return;
                    }

                    gameController.placeDiceOnBrakes(selectedDiceValue, brakeIndex);


                }

                // Radios
                else if (slot == pilotRadioSlot){
                    gameController.placeDiceOnPilotRadioSlot(selectedDiceValue);
                    placementSuccessful = true;

                }

                // coffee
                else if (slot == firstCoffeeSlot || slot == secondCoffeeSlot || slot == thirdCoffeeSlot) {
                    if (selectedDiceValue != 0) {

                        int slotIndex;
                        if (slot == firstCoffeeSlot) {
                            slotIndex = 0;
                        } else if (slot == secondCoffeeSlot) {
                            slotIndex = 1;
                        } else if (slot == thirdCoffeeSlot) {
                            slotIndex = 2;
                        } else {
                            return;
                        }

                        gameController.placeDiceOnConcentration(selectedDiceValue, slotIndex);

                    }
                }



                else {
                    throw new IllegalArgumentException("Invalid slot selected");
                }
                if (placementSuccessful) {
                    slot.setDrawable(new Image(diceTextures[selectedDiceValue]).getDrawable());
                    placedSound.play(1.0f);
                    updateDiceImages();
                    selectedDiceValue = 0;
                    changeTurn();


                    if (gameController.isGameOver()) {
                        showGameOverDialog();
                    }
                }
            } catch (Exception e) {
                showErrorMessage("Invalid placement: " + e.getMessage());
            }

            updateDiceImages();
        }
    }

    private void changeTurn() {
        Timer.schedule(new Timer.Task() {
            public void run() {
                blurryScreen.setVisible(true);
                readyButton.setVisible(true);
                readyButton.toFront();
                movementSound.play(1.0f);
            }
        }, 1);
    }

    public void showErrorMessage(String errorMessage) {
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

    public void updateApproachTrackVisuals() {
        int[] planeTokens = gameController.getApproachTrackPlaneTokens();
        for (int i = 0; i < approachTrackAmount; i++) {
            int planeCount = planeTokens[i];
            if (planeCount >= 0 && planeCount < approachTextures.length) {
                approachTrackSlots[i].setDrawable(new TextureRegionDrawable(new TextureRegion(approachTextures[planeCount])));
            }
        }
    }
}
