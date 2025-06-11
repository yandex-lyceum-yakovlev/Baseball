package io.github.tennis;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.tennis.managers.SoundManager;
import io.github.tennis.managers.TextureManager;
import io.github.tennis.screens.GameScreen;
import io.github.tennis.screens.MenuScreen;
import io.github.tennis.screens.SettingsScreen;


import static io.github.tennis.GameSettings.*;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Tennis extends Game {
    public World world;

    public Vector3 touch;
    public SpriteBatch batch;
    public OrthographicCamera camera;

    public GameScreen gameScreen;
    public MenuScreen menuScreen;

    public SettingsScreen settingsScreen;

    float accumulator = 0;

    public BitmapFont largeWhiteFont;
    public BitmapFont commonWhiteFont;
    public BitmapFont commonBlackFont;

    public SoundManager soundManager;

    public TextureManager textureManager;


    @Override
    public void create() {

        Box2D.init();
        world = new World(new Vector2(0, 0), true);

        textureManager = new TextureManager();

        soundManager = new SoundManager();
        soundManager.playMusic();
//        soundManager.setEnabled(true);

        largeWhiteFont = FontBuilder.generate(48, Color.WHITE, GameResources.FONT_PATH);
        commonWhiteFont = FontBuilder.generate(24, Color.WHITE, GameResources.FONT_PATH);
        commonBlackFont = FontBuilder.generate(24, Color.BLACK, GameResources.FONT_PATH);


        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);

        gameScreen = new GameScreen(this);
        menuScreen = new MenuScreen(this);
        settingsScreen = new SettingsScreen(this);
        setScreen(menuScreen);

    }

    @Override
    public void dispose() {
        batch.dispose();
        if (menuScreen != null) menuScreen.dispose();
        if (gameScreen != null) gameScreen.dispose();
        if (settingsScreen != null) settingsScreen.dispose();
        soundManager.dispose();

    }

    public void stepWorld() {
        float delta = Gdx.graphics.getDeltaTime();
        accumulator += Math.min(delta, 0.25f);

        if (accumulator >= STEP_TIME) {
            accumulator -= STEP_TIME;
            world.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        }
    }}
