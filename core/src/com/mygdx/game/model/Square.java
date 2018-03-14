package com.mygdx.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Antoine Dc on 06/03/2018.
 */

public class Square {
    private Sprite sprite;
    private Texture texture;
    private Vector2 position;
    private Vector2 speed;

    public Square (){
        this.texture = new Texture(Gdx.files.internal("square.png"));
        this.sprite = new Sprite(texture);
        this.position = new Vector2 (0, 0);
        this.speed = new Vector2(0, 4);
    }


    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getSpeed() {
        return speed;
    }

    public void setSpeed(Vector2 speed) {
        this.speed = speed;
    }

    public void move(){
        this.setPosition(new Vector2(this.getPosition().x, this.getPosition().y + this.getSpeed().y));
    }

    public void reverseMove(){
        this.setPosition(new Vector2(this.getPosition().x, this.getPosition().y - this.getSpeed().y));
    }
}
