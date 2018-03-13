package com.mygdx.game.model;

import com.badlogic.gdx.graphics.Texture;

import java.awt.Rectangle;

import static com.mygdx.game.Squarz.HEIGHT;

/**
 * Created by Lucas on 12/03/2018.
 */

public class Icon extends Rectangle {
    private Texture texture;
    private int posx,posy;

    public Icon (Texture texture, int posx, int posy) {
        super();
        this.texture = texture;
        this.posx=posx;
        this.posy=posy;
    }

    public Texture getTexture() {
        return this.texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public int getPosx() {
        return this.posx;
    }

    public void setPosx (int posx) {
        this.posx = posx;
    }

    public int getPosy() {
        return this.posy;
    }

    public void setPosy (int posy) {
        this.posy = posy;
    }

    public boolean isIn(int x, int y) {  // return true if (x,y) is in the icon (this = the icon)
        return (this.posx < x && x < this.posx + this.texture.getWidth() && this.posy < y && y < this.posy + this.texture.getHeight());
    }
}
