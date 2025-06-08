package io.github.tennis.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

import static io.github.tennis.GameSettings.*;

public class BallObject extends GameObject {

    public BallObject(String texturePath, float x, float y, float diameter, World world) {
        super(texturePath, x, y, diameter, diameter, world);
        body.setUserData("ball"); // по желанию, если будешь использовать в ContactListener
    }

    @Override
    Body createBody(float x, float y, float width, float height, World world) {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(x * SCALE, y * SCALE);

        Body body = world.createBody(def);

        CircleShape shape = new CircleShape();
        shape.setRadius((width * SCALE) / 2f); // диаметр = width

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;      // можешь подбирать под игру
        fixtureDef.friction = 0.2f;
        fixtureDef.restitution = 0.9f;  // отскок

        body.createFixture(fixtureDef);
        shape.dispose();

        return body;
    }
}
