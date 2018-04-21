package com.mygdx.game.model.other;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import static com.mygdx.game.Squarz.font3;

public class Icon {
    private Texture texture;
    private Texture selected, off;
    private GlyphLayout legend;
    private int posX, posY;

    public Icon (Texture texture, int posx, int posy) {
        this.texture = texture;
        this.selected = texture;
        this.off = texture;
        this.posX = posx;
        this.posY = posy;
        this.legend = new GlyphLayout(font3, "");
    }

    public Texture getTexture() {
        return this.texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public int getPosX() {
        return this.posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setLegend(String leg) {
        this.legend = new GlyphLayout(font3, leg);
    }

    public GlyphLayout getLegend() {
        return this.legend;
    }

    public boolean contains(int x, int y) {  // return true if (x,y) is in the icon (this = the icon)
        return (this.posX < x+15 && x-15 < this.posX + this.texture.getWidth() && this.posY < y+15 && y-15 < this.posY + this.texture.getHeight());
    }
}
