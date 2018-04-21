package com.mygdx.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.model.aI.PreferencesSettings;

import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.format;

/**
 * Created by Max on 06/03/2018.
 */

// Color - number association
// red == 0; blue == 1; yellow == 2;
// Collision convention
//    red < blue < yellow < red

public class Square {
    private static final double TIME = 1/30;
    private Sprite sprite;
    private Texture texture;
    private Vector2 position;
    private Rectangle rectangle;
    private Integer colorKey;
    private PreferencesSettings set;

    private Integer freezeSpeed;

    public Square (PreferencesSettings set){
        this.texture = new Texture(Gdx.files.internal(format+"/square/square.png"));
        this.sprite = new Sprite(texture);
        this.position = new Vector2 (0, 0);
        this.rectangle = new Rectangle(this.getPosition().x, this.getPosition().y,
                this.getTexture().getWidth(), this.getTexture().getHeight());
        this.colorKey = 0;
        this.set = set;
        this.freezeSpeed = 0;
    }


    public void move(float dt){
        this.setPosition(new Vector2(this.getPosition().x, (this.getPosition().y + this.set.getStepX()*dt)));
    }

    public void reverseMove(float dt){
        this.setPosition(new Vector2(this.getPosition().x, (this.getPosition().y - this.set.getStepX()*dt)));
    }

    public boolean isInUser(){
        if (this.getPosition().y >= HEIGHT){
            return false;
        }
        return true;
    }

    public boolean isInAi(){
        if (this.getPosition().y + this.getTexture().getHeight() <=0 ){
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
