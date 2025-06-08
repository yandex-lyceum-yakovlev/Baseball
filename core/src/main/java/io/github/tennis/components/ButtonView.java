package io.github.tennis.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ButtonView extends View {

    private BitmapFont font;
    private String text;

    public ButtonView(String texturePath, float x, float y, float width, float height, BitmapFont font, String text) {
        super(new Texture(texturePath), x, y, width, height);
        this.font = font;
        this.text = text;
    }

    public ButtonView(String texturePath, float x, float y, float width, float height) {
        super(new Texture(texturePath), x, y, width, height);
    }


    public void draw(SpriteBatch batch) {
        super.draw(batch); // рисует спрайт

        if (font != null && text != null) {
            GlyphLayout layout = new GlyphLayout(font, text);
            float textX = getX() + (getWidth() - layout.width) / 2f;
            float textY = getY() + (getHeight() + layout.height) / 2f;

            font.draw(batch, layout, textX, textY);
        }
    }

    public void setText(String text) {
        this.text = text;

        // Пересчитать размеры
//        GlyphLayout layout = new GlyphLayout(font, text);
//        setSize(layout.width, layout.height); // влияет на isHit()
    }

    @Override
    public void dispose() {
        super.dispose();
        if (font != null) font.dispose();
    }
}
