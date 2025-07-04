package io.github.tennis.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

import static io.github.tennis.GameSettings.*;

public class GameObject extends Sprite {

    public Body body;

    public GameObject(Texture texture, float x, float y, float width, float height, World world) {
        super(texture);
        setSize(width, height);
        setOriginCenter();

        body = createBody(x, y, width, height, world);
    }

    Body createBody(float x, float y, float width, float height, World world) {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(x * SCALE, y * SCALE);

        Body body = world.createBody(def);

        CircleShape shape = new CircleShape();
        shape.setRadius(Math.max(width, height) * SCALE);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.1f;
        fixtureDef.friction = 1f;

        body.createFixture(fixtureDef);
        shape.dispose();

        return body;
    }

    public void update() {
        setPosition(
            body.getPosition().x / SCALE - getWidth() / 2f,
            body.getPosition().y / SCALE - getHeight() / 2f
        );
        setRotation((float) Math.toDegrees(body.getAngle()));
    }

    public void dispose() {
        getTexture().dispose();
    }
}
