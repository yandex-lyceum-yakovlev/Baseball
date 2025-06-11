package io.github.tennis.objects;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

import static io.github.tennis.GameSettings.*;
public class TargetObject extends GameObject {

    public TargetObject(Texture texture, float x, float y, float diameter, World world) {
        super(texture, x, y, diameter, diameter, world);
    }

    @Override
    Body createBody(float x, float y, float width, float height, World world) {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.StaticBody;
        def.position.set(x * SCALE, y * SCALE);

        Body body = world.createBody(def);

        CircleShape shape = new CircleShape();
        shape.setRadius((width * SCALE) / 2f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 1f;

        body.createFixture(fixtureDef);
        shape.dispose();

        return body;
    }
}

