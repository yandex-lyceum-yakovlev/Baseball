package io.github.tennis;

public class GameSettings {

    // Device settings

    public static final int SCREEN_WIDTH = 720;
    public static final int SCREEN_HEIGHT = 1280;

    // Physics settings

    public static final float STEP_TIME = 1f / 60f;
    public static final int VELOCITY_ITERATIONS = 6;
    public static final int POSITION_ITERATIONS = 6;
    public static final float SCALE = 0.05f;

    public static float RACKET_FORCE_RATIO = 10;

    public static final float ROUND_DURATION = 30f;

    // Object sizes

    public static final int RACKET_WIDTH = 207;
    public static final int RACKET_HEIGHT = 40;

    public static final int BALL_DIAMETER = 60;
    public static final int TARGET_DIAMETER = 60;
    public static final int OBSTACLE_DIAMETER = 60;

}
