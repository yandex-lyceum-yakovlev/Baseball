package io.github.tennis.components;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextView extends View {

    protected BitmapFont font;
    protected String text;

    public TextView(BitmapFont font, float x, float y) {
        super(null, x, y, 0, 0); // null-текстура, 0x0 размер — мы сами зададим позже
        this.font = font;
    }

    public TextView(BitmapFont font, float x, float y, String text) {
        this(font, x, y);
        setText(text);
    }

    public void setText(String text) {
        this.text = text;

        // Пересчитать размеры
        GlyphLayout layout = new GlyphLayout(font, text);
        setSize(layout.width, layout.height); // влияет на isHit()
    }

     public void draw(SpriteBatch batch) {
        if (text != null && font != null) {
            font.draw(batch, text, getX(), getY() + getHeight());
        }
    }

    @Override
    public void dispose() {
        if (font != null) font.dispose();
    }
}
