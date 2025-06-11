package io.github.tennis.screens;

import static io.github.tennis.GameResources.BUTTON_IMG_PATH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.tennis.GameSettings;
import io.github.tennis.Tennis;
import io.github.tennis.components.ButtonView;
import io.github.tennis.components.TextView;

import static io.github.tennis.GameResources.*;

public class SettingsScreen implements Screen {

    private final Tennis tennis;

    private ButtonView backButton;
    private ButtonView soundToggle;
    private ButtonView musicToggle;

    private TextView titleText;
    private TextView soundLabel;
    private TextView musicLabel;

    private boolean soundEnabled = true;
    private boolean musicEnabled = true;

    private Texture backgroundTexture;

    public SettingsScreen(Tennis tennis) {
        this.tennis = tennis;

        // UI-параметры
        float centerX = GameSettings.SCREEN_WIDTH / 2f;
        float y = 500;

        titleText = new TextView(tennis.commonWhiteFont, centerX - 100, y + 100, "Settings");

        soundLabel = new TextView(tennis.commonWhiteFont, centerX - 200, y, "Sound Effects:");
        soundToggle = new ButtonView(tennis.textureManager.BUTTON_IMG, centerX + 100, y - 40, 150, 60, tennis.commonWhiteFont, "ON");

        y -= 100;

        musicLabel = new TextView(tennis.commonWhiteFont, centerX - 200, y, "Music:");
        musicToggle = new ButtonView(tennis.textureManager.BUTTON_IMG,  centerX + 100, y - 40, 150, 60, tennis.commonWhiteFont, "ON");

        y -= 150;

        backButton = new ButtonView(tennis.textureManager.BUTTON_IMG, centerX - 100, y, 200, 70,tennis.commonWhiteFont ,"Back to Menu");

        backgroundTexture = tennis.textureManager.BG;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        tennis.camera.update();
        tennis.batch.setProjectionMatrix(tennis.camera.combined);

        tennis.batch.begin();
        tennis.batch.draw(backgroundTexture, 0, 0, GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);
        titleText.draw(tennis.batch);
        soundLabel.draw(tennis.batch);
        musicLabel.draw(tennis.batch);
        soundToggle.draw(tennis.batch);
        musicToggle.draw(tennis.batch);
        backButton.draw(tennis.batch);
        tennis.batch.end();

        handleInput();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touch = tennis.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            Vector2 touchPos = new Vector2(touch.x, touch.y);

            if (soundToggle.isHit(touchPos.x, touchPos.y)) {
                soundEnabled = !soundEnabled;
                soundToggle.setText(soundEnabled ? "ON" : "OFF");
                tennis.soundManager.setEnabled(soundEnabled);
            }

            if (musicToggle.isHit(touchPos.x, touchPos.y)) {
                musicEnabled = !musicEnabled;
                musicToggle.setText(musicEnabled ? "ON" : "OFF");
                if (musicEnabled) {
                    tennis.soundManager.playMusic();
                } else {
                    tennis.soundManager.stopMusic();
                }
            }

            if (backButton.isHit(touchPos.x, touchPos.y)) {
                tennis.setScreen(new MenuScreen(tennis));
            }
        }
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        backgroundTexture.dispose();
    }
}

