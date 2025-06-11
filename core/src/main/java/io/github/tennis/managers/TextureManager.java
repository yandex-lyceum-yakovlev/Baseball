package io.github.tennis.managers;

import com.badlogic.gdx.graphics.Texture;

import static io.github.tennis.GameResources.*;

public class TextureManager {


    public final Texture RACKET_IMG;

    public final Texture BALL_IMG;

    public final Texture OBSTACLE_IMG;
    public final Texture TARGET_IMG;

    public final Texture BG;
    public final Texture BUTTON_IMG;

    public TextureManager() {
        RACKET_IMG = new Texture(RACKET_IMG_PATH);
        BALL_IMG = new Texture(BALL_IMG_PATH);
        OBSTACLE_IMG = new Texture(OBSTACLE_IMG_PATH);;
        TARGET_IMG = new Texture(TARGET_IMG_PATH);;
        BG = new Texture(BG_PATH);;
        BUTTON_IMG = new Texture(BUTTON_IMG_PATH);;
    }
}
