package io.github.tennis.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.tennis.GameSettings;
import io.github.tennis.Tennis;
import io.github.tennis.components.ButtonView;
import io.github.tennis.components.TextView;
import io.github.tennis.listeners.BallContactListener;
import io.github.tennis.objects.BallObject;
import io.github.tennis.objects.ObstacleObject;
import io.github.tennis.objects.RacketObject;
import io.github.tennis.objects.TargetObject;
import io.github.tennis.objects.WallObject;

import static io.github.tennis.GameSettings.*;
import static io.github.tennis.GameResources.*;

import java.util.ArrayList;
import java.util.List;

public class GameScreen extends ScreenAdapter {

    private float roundTime = 0f;

    public Tennis tennis;

    private Texture backgroundTexture;

    private GameHUD hud;
    public RacketObject racketObject;
    BallObject ballObject;

    TargetObject targetObject;

    WallObject bottomWallObject;

    private Box2DDebugRenderer renderer;

    Body groundBody;
    MouseJoint mouseJoint;

    List<ObstacleObject> obstacles = new ArrayList<>();

    public boolean shouldNextRound = false;

    public int score = 0;


    private TextView gameOverText;
    private ButtonView menuButton;
    public boolean gameOver = false;


    public GameScreen(Tennis tennis) {
        this.tennis = tennis;
        nextRound();
        createWalls();
        hud = new GameHUD(tennis, tennis.commonWhiteFont);
        backgroundTexture = new Texture(BG_PATH);
        BodyDef bodyDef = new BodyDef();
        groundBody = tennis.world.createBody(bodyDef);
        renderer = new Box2DDebugRenderer();
        tennis.world.setContactListener(new BallContactListener(ballObject, targetObject, bottomWallObject, this));
    }

    @Override
    public void render(float delta) {
        tennis.camera.update();
        tennis.batch.setProjectionMatrix(tennis.camera.combined);
        tennis.stepWorld();
        roundTime += delta;

        if (roundTime > ROUND_DURATION) {
            score -= 1;
            shouldNextRound = true;
        }

        if (shouldNextRound) {
            nextRound();
            shouldNextRound = false;
        }

        if (score < 0) {
            showGameOverUI();
        }

        racketObject.update();
        ballObject.update();
        targetObject.update();
        for (ObstacleObject obstacle : obstacles) obstacle.update();
        handleInput();

        draw();

        if (gameOver) {
            tennis.batch.begin();
            gameOverText.draw(tennis.batch);
            menuButton.draw(tennis.batch);
            tennis.batch.end();

            // Обработка нажатия на кнопку
            if (Gdx.input.justTouched()) {
                Vector3 touch = tennis.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
                if (menuButton.isHit(touch.x, touch.y)) {
                    // Вернуться в меню
                    tennis.setScreen(new MenuScreen(tennis)); // создаёшь MenuScreen отдельно
                }
            }
        }
        tennis.batch.begin();
        hud.update(roundTime, score);
        hud.render(tennis.batch);
        tennis.batch.end();

        if (!gameOver) {
            hud.handleInput(tennis.camera, tennis);
        }



        renderer.render(tennis.world, tennis.camera.combined.cpy().scl(1 / SCALE));
    }

    private void handleInput() {
        if (gameOver) return;
        if (Gdx.input.justTouched()) {
            Vector3 touch = tennis.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            Vector2 touchPos = new Vector2(touch.x * SCALE, touch.y * SCALE);

            // Ограничение по высоте — не выше трети экрана
            float maxY = (GameSettings.SCREEN_HEIGHT / 3f) * SCALE;
            if (touchPos.y > maxY) return;

            // Проверка, попали ли в ракетку
            if (racketObject.body.getFixtureList().first().testPoint(touchPos)) {
                MouseJointDef jointDef = new MouseJointDef();
                jointDef.bodyA = groundBody;
                jointDef.bodyB = racketObject.body;
                jointDef.collideConnected = true;
                jointDef.target.set(touchPos);
                jointDef.maxForce = 100000.0f * racketObject.body.getMass();
                jointDef.dampingRatio = 0.7f;
                jointDef.frequencyHz = 5f;
                mouseJoint = (MouseJoint) tennis.world.createJoint(jointDef);
            }
        }

        if (mouseJoint != null) {
            if (Gdx.input.isTouched()) {
                Vector3 touch = tennis.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
                Vector2 touchPos = new Vector2(touch.x * SCALE, touch.y * SCALE);

                // Ограничение по высоте (во время перемещения тоже)
                float maxY = (GameSettings.SCREEN_HEIGHT / 3f) * SCALE;
                if (touchPos.y > maxY) {
                    touchPos.y = maxY; // просто прижимаем к максимальному уровню
                }

                mouseJoint.setTarget(touchPos);
            } else {
                tennis.world.destroyJoint(mouseJoint);
                mouseJoint = null;
            }
        }
    }


    private void draw() {

        tennis.camera.update();
        tennis.batch.setProjectionMatrix(tennis.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        tennis.batch.begin();
        tennis.batch.draw(backgroundTexture, 0, 0, GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);
        racketObject.draw(tennis.batch);
        ballObject.draw(tennis.batch);
        targetObject.draw(tennis.batch);
        for (ObstacleObject obstacle : obstacles) obstacle.draw(tennis.batch);
//        scoreView.draw(tennis.batch);
        tennis.batch.end();
    }

    private void createWalls() {
        new WallObject(0, SCREEN_HEIGHT / 2f, 10, SCREEN_HEIGHT, tennis.world);                     // левая стена
        new WallObject(SCREEN_WIDTH, SCREEN_HEIGHT / 2f, 10, SCREEN_HEIGHT, tennis.world);         // правая стена
        new WallObject(SCREEN_WIDTH / 2f, SCREEN_HEIGHT, SCREEN_WIDTH, 10, tennis.world);          // потолок
        bottomWallObject = new WallObject(SCREEN_WIDTH / 2f, 0, SCREEN_WIDTH, 10, tennis.world);
    }

    public void nextRound() {

        if (mouseJoint != null) {
            tennis.world.destroyJoint(mouseJoint);
            mouseJoint = null;
        }

        // Удалить старые объекты
        if (ballObject != null) tennis.world.destroyBody(ballObject.body);
        if (racketObject != null) tennis.world.destroyBody(racketObject.body);
        if (targetObject != null) tennis.world.destroyBody(targetObject.body);
        for (ObstacleObject o : obstacles) {
            tennis.world.destroyBody(o.body);
        }
        obstacles.clear();

        // Сбросить время раунда
        roundTime = 0f;

        // Создать мяч
        float ballX = SCREEN_WIDTH / 2f;
        float ballY = SCREEN_HEIGHT * 0.4f;
        ballObject = new BallObject(BALL_IMG_PATH, ballX, ballY, BALL_DIAMETER, tennis.world);

        // Создать ракетку
        float racketX = SCREEN_WIDTH / 2f;
        float racketY = SCREEN_HEIGHT * 0.1f;
        racketObject = new RacketObject(RACKET_IMG_PATH, racketX, racketY, RACKET_WIDTH, RACKET_HEIGHT, tennis.world);

        // Создать цель
        float targetX = MathUtils.random(100, SCREEN_WIDTH - 100);
        float targetY = MathUtils.random(SCREEN_HEIGHT * 2f / 3f, SCREEN_HEIGHT - 100);
        targetObject = new TargetObject(TARGET_IMG_PATH, targetX, targetY, TARGET_DIAMETER, tennis.world);

        // Создать препятствия
        for (int i = 0; i < score; i++) {
            float x = MathUtils.random(100, SCREEN_WIDTH - 100);
            float y = MathUtils.random(SCREEN_HEIGHT * 2f / 3f, SCREEN_HEIGHT - 100);

            ObstacleObject obstacle = new ObstacleObject(OBSTACLE_IMG_PATH, x, y, OBSTACLE_DIAMETER, tennis.world);
            obstacles.add(obstacle);
        }

        // Обновить contactListener, чтобы он ссылался на новые объекты
        tennis.world.setContactListener(new BallContactListener(ballObject, targetObject, bottomWallObject, this));
    }

    private void showGameOverUI() {
        gameOver = true;

        // Текст "You lost"
        gameOverText = new TextView(
            tennis.largeWhiteFont, // Можно заменить на заранее загруженный шрифт
            SCREEN_WIDTH / 2f - 100, // Центр по X
            SCREEN_HEIGHT / 2f + 40,
            "Game Over"
        );

        // Кнопка Menu
        menuButton = new ButtonView(
            BUTTON_IMG_PATH, // путь к изображению кнопки
            SCREEN_WIDTH / 2f - 100,
            SCREEN_HEIGHT / 2f - 40,
            200, 50,
            tennis.largeWhiteFont, // или общий font
            "Menu"
        );
    }


}
