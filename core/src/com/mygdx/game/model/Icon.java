package com.mygdx.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Lucas on 12/03/2018.
 */

public class Icon {
    private Texture texture;
    private Texture selected, off;
    private int posX, posY;

    public Icon (Texture texture, int posx, int posy) {
        this.texture = texture;
        this.selected = texture;
        this.off = texture;
        this.posX = posx;
        this.posY = posy;
    }

    public Texture getTexture() {
        return this.texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Texture getSelected() {
        return this.selected;
    }

    public void setSelected(Texture selected) {
        this.selected = selected;
    }

    public Texture getOff() {
        return this.off;
    }

    public void setOff(Texture off) {
        this.off = off;
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

    public boolean contains(int x, int y) {  // return true if (x,y) is in the icon (this = the icon)
        return (this.posX < x+15 && x-15 < this.posX + this.texture.getWidth() && this.posY < y+15 && y-15 < this.posY + this.texture.getHeight());
    }
}
