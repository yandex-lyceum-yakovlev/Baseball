package io.github.tennis.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import io.github.tennis.GameResources;
import io.github.tennis.GameSettings;
import io.github.tennis.Tennis;
import io.github.tennis.components.ButtonView;
import io.github.tennis.components.TextView;

import static io.github.tennis.GameSettings.*;

public class GameHUD {
    private final Tennis tennis;
    private final BitmapFont font;

    private final TextView timeView;
    private final TextView scoreView;
    private final ButtonView menuButton;

    public GameHUD(Tennis tennis, BitmapFont font) {
        this.tennis = tennis;
        this.font = font;

        timeView = new TextView(font, 10, GameSettings.SCREEN_HEIGHT - 30, "Time: 0");
        scoreView = new TextView(font, GameSettings.SCREEN_WIDTH - 150, GameSettings.SCREEN_HEIGHT - 30, "Score: 0");
        menuButton = new ButtonView(
            tennis.textureManager.BUTTON_IMG,
            GameSettings.SCREEN_WIDTH / 2f - 50,
            GameSettings.SCREEN_HEIGHT - 50,
            100, 40,
            font,
            "Menu"
        );
    }

    public void update(float roundTime, int score) {
        timeView.setText("Time: " + (int) (ROUND_DURATION - roundTime));
        scoreView.setText("Score: " + score);
    }

    public void render(SpriteBatch batch) {
        timeView.draw(batch);
        scoreView.draw(batch);
        menuButton.draw(batch);
    }

    public void handleInput(Camera camera, Tennis tennis) {
        if (Gdx.input.justTouched()) {
            Vector3 touch = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (menuButton.isHit(touch.x, touch.y)) {
                tennis.setScreen(new MenuScreen(tennis));
            }
        }
    }
}

