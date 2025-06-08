package io.github.tennis.objects;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

import static io.github.tennis.GameSettings.*;
public class WallObject {

    public Body body;

    public WallObject(float x, float y, float width, float height, World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x * SCALE, y * SCALE);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((width * SCALE) / 2f, (height * SCALE) / 2f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 1f; // 100% отскок

        body.createFixture(fixtureDef);
        shape.dispose();
    }
}

