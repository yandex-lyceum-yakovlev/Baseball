package io.github.tennis.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

import static io.github.tennis.GameSettings.*;

public class ObstacleObject extends GameObject {

    public ObstacleObject(Texture texture, float x, float y, float diameter, World world) {
        super(texture, x, y, diameter, diameter, world);
    }

    @Override
    Body createBody(float x, float y, float width, float height, World world) {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.StaticBody;
        def.position.set(x * SCALE, y * SCALE);

        Body body = world.createBody(def);

        CircleShape shape = new CircleShape();
        shape.setRadius(Math.max(width, height) * SCALE / 2f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.8f;

        body.createFixture(fixtureDef);
        shape.dispose();

        return body;
    }
}

