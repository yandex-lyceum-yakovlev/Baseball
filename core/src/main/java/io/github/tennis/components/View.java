package io.github.tennis.components;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Disposable;

public class View extends Sprite implements Disposable {

    public View(Texture texture, float x, float y, float width, float height) {
        super(texture != null ? texture : new Texture(1, 1, Pixmap.Format.RGBA8888)); // безопасная заглушка
        setSize(width, height);
        setPosition(x, y);
    }

    public boolean isHit(float tx, float ty) {
        return getBoundingRectangle().contains(tx, ty);
    }

    @Override
    public void dispose() {
        getTexture().dispose();
    }
}
