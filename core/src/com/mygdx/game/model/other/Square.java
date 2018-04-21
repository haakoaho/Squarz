package com.mygdx.game.model.other;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.model.ai_settings.PreferencesSettings;

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
    private Texture texture;
    private Vector2 position;
    private final Rectangle rectangle;
    private Integer colorKey;
    private final PreferencesSettings set;

    public Square (PreferencesSettings set){
        this.texture = new Texture(Gdx.files.internal(format+"/square/square.png"));
        Sprite sprite = new Sprite(texture);
        this.position = new Vector2 (0, 0);
        this.rectangle = new Rectangle(this.getPosition().x, this.getPosition().y,
                this.getTexture().getWidth(), this.getTexture().getHeight());
        this.colorKey = 0;
        this.set = set;
    }


    public void move(float dt){
        this.setPosition(new Vector2(this.getPosition().x, (this.getPosition().y + this.set.getStepX()*dt)));
    }

    public void reverseMove(float dt){
        this.setPosition(new Vector2(this.getPosition().x, (this.getPosition().y - this.set.getStepX()*dt)));
    }

    public boolean isInUser(){
        return !(this.getPosition().y >= HEIGHT);
    }

    public boolean isInAi(){
        return !(this.getPosition().y + this.getTexture().getHeight() <= 0);
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

    public Integer getColorKey() {
        return colorKey;
    }

    public void setColorKey(Integer colorKey) {
        this.colorKey = colorKey;
    }
}
