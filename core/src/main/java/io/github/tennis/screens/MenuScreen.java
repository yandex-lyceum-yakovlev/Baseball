package io.github.tennis.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.tennis.GameSettings;
import io.github.tennis.Tennis;
import io.github.tennis.components.*;
import static io.github.tennis.GameResources.*;


public class MenuScreen extends ScreenAdapter {

    Tennis tennis;

//    MovingBackgroundView backgroundView;
    TextView titleView;
    ButtonView startButtonView;
    ButtonView settingsButtonView;
    ButtonView exitButtonView;

    private Texture backgroundTexture;

    public MenuScreen(Tennis tennis) {
        this.tennis = tennis;

//        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        titleView = new TextView(tennis.largeWhiteFont, 250, 760, "BaseBall");
        startButtonView = new ButtonView(BUTTON_IMG_PATH,140, 646, 440, 70, tennis.commonWhiteFont,  "Play");
        settingsButtonView = new ButtonView(BUTTON_IMG_PATH,140, 551, 440, 70, tennis.commonWhiteFont,  "Settings");
        exitButtonView = new ButtonView(BUTTON_IMG_PATH,140, 456, 440, 70, tennis.commonWhiteFont, "Exit");

        backgroundTexture = new Texture(BG_PATH);
    }

    @Override
    public void render(float delta) {

        handleInput();

        tennis.camera.update();
        tennis.batch.setProjectionMatrix(tennis.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        tennis.batch.begin();

        tennis.batch.draw(backgroundTexture, 0, 0, GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);
        titleView.draw(tennis.batch);
        exitButtonView.draw(tennis.batch);
        settingsButtonView.draw(tennis.batch);
        startButtonView.draw(tennis.batch);

        tennis.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            tennis.touch = tennis.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (startButtonView.isHit(tennis.touch.x, tennis.touch.y)) {
                tennis.setScreen(tennis.gameScreen);
            }
            if (exitButtonView.isHit(tennis.touch.x, tennis.touch.y)) {
                Gdx.app.exit();
            }
            if (settingsButtonView.isHit(tennis.touch.x, tennis.touch.y)) {
                tennis.setScreen(tennis.settingsScreen);
            }
        }
    }
}
