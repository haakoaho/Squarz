package com.mygdx.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Max on 06/03/2018.
 */

// Color - number association
// red == 0; blue == 1; yellow == 2;
// Collision convention
//    red < blue < yellow < red

public class Square {
    private Sprite sprite;
    private Texture texture;
    private Vector2 position;
    private Integer speed;
    private Rectangle rectangle;
    private Integer colorKey;

    public Square (){
        this.texture = new Texture(Gdx.files.internal("square.png"));
        this.sprite = new Sprite(texture);
        this.position = new Vector2 (0, 0);
        this.speed = Gdx.graphics.getHeight()/400;
        this.rectangle = new Rectangle(this.getPosition().x, this.getPosition().y,
                this.getTexture().getWidth(), this.getTexture().getHeight());
        this.colorKey = 0;
    }


    public void move(){
        this.setPosition(new Vector2(this.getPosition().x, this.getPosition().y + this.getSpeed()));
    }

    public void reverseMove(){
        this.setPosition(new Vector2(this.getPosition().x, this.getPosition().y - this.getSpeed()));
    }

    public boolean isInUser(){
        if (this.getPosition() == new Vector2(10, 10)
                || this.getPosition().y > Gdx.graphics.getHeight() + this.getSpeed() ){
            return false;
        }
        return true;
    }

    public boolean isInAi(){
        if (this.getPosition() == new Vector2(10, 10)
                || (this.getPosition().y + this.getSpeed() ) < 0){
            return false;
        }
        return true;
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
        this.rectangle.set(position.x, position.y, this.getTexture().getWidth(), this.getTexture().getHeight());
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public Integer getColorKey() {
        return colorKey;
    }

    public void setColorKey(Integer colorKey) {
        this.colorKey = colorKey;
    }
}
