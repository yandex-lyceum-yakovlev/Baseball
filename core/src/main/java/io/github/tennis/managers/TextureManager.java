package io.github.tennis.managers;

import com.badlogic.gdx.graphics.Texture;

import static io.github.tennis.GameResources.*;

public class TextureManager {


    public final Texture racketImg;

    public final Texture ballImg;

    public final Texture obstacleImg;
    public final Texture targetImg;

    public final Texture backgroundImg;
    public final Texture buttonImg;

    public TextureManager() {
        racketImg = new Texture(RACKET_IMG_PATH);
        ballImg = new Texture(BALL_IMG_PATH);
        obstacleImg = new Texture(OBSTACLE_IMG_PATH);;
        targetImg = new Texture(TARGET_IMG_PATH);;
        backgroundImg = new Texture(BG_PATH);;
        buttonImg = new Texture(BUTTON_IMG_PATH);;
    }

    public void dispose() {
        racketImg.dispose();
        ballImg.dispose();
        obstacleImg.dispose();
        targetImg.dispose();
        backgroundImg.dispose();
        buttonImg.dispose();
    }

}
