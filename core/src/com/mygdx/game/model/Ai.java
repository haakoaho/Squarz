package com.mygdx.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;
import java.util.Map;

import static com.badlogic.gdx.math.MathUtils.random;

/**
 * Created by Max on 13/03/2018.
 */

public class Ai {
    private Map<Integer, Square> leftMap;
    private Map<Integer, Square> centerMap;
    private Map<Integer, Square> rightMap;
    private Integer leftCounter;
    private Integer centerCounter;
    private Integer rightCounter;
    private Integer controlSending;
    private Texture texture;
    private Square square;
    private Integer deltaLauncher;
    private Integer renderCounter;


    public Ai (){
        this.leftMap = new HashMap<Integer, Square>();
        this.centerMap = new HashMap<Integer, Square>();
        this.rightMap = new HashMap<Integer, Square>();
        this.leftCounter = 0;
        this.centerCounter = 0;
        this.rightCounter = 0;
        this.controlSending = 0;
        this.texture = new Texture (Gdx.files.internal("square.png"));
        this.square = new Square();
        this.deltaLauncher = 70;
        this.renderCounter = 0;
    }

    public void send(CountDown countDown){
        this.renderCounter += 1;
        if (countDown.getWorldTimer() > 0) {
            if (this.renderCounter == deltaLauncher) {
                this.renderCounter = 0;

                int color = random(2);
                int row = random(2);
                if (color == 0) {
                    this.texture = new Texture(Gdx.files.internal("square_red.png"));
                } else if (color == 1) {
                    this.texture = new Texture(Gdx.files.internal("square_blue.png"));
                } else {
                    this.texture = new Texture(Gdx.files.internal("square_yellow.png"));
                }
                if (row == 0) {
                    this.leftMap.put(this.leftCounter, new Square());
                    this.leftMap.get(this.leftCounter).setPosition(new Vector2(Gdx.graphics.getWidth() * 5 / 16, Gdx.graphics.getHeight()));
                    this.leftMap.get(this.leftCounter).setTexture(this.texture);
                    if (this.leftCounter != 0 && this.leftMap.get(this.leftCounter - 1).getPosition().y > Gdx.graphics.getHeight() - this.square.getTexture().getHeight() + 5) {
                        this.leftMap.get(this.leftCounter).setPosition(new Vector2(Gdx.graphics.getWidth() * 5 / 16,
                                this.leftMap.get(this.leftCounter - 1).getPosition().y + 2*this.square.getTexture().getHeight() + 5));
                    }
                    this.leftCounter += 1;
                } else if (row == 1) {
                    this.centerMap.put(this.centerCounter, new Square());
                    this.centerMap.get(this.centerCounter).setPosition(new Vector2(Gdx.graphics.getWidth() * 9 / 16, Gdx.graphics.getHeight()));
                    this.centerMap.get(this.centerCounter).setTexture(this.texture);
                    if (this.centerCounter != 0 && this.centerMap.get(this.centerCounter - 1).getPosition().y > Gdx.graphics.getHeight() - this.square.getTexture().getHeight() + 5) {
                        this.centerMap.get(this.centerCounter).setPosition(new Vector2(Gdx.graphics.getWidth() * 9 / 16,
                                this.centerMap.get(this.centerCounter - 1).getPosition().y  + 2*this.square.getTexture().getHeight() + 5));
                    }
                    this.centerCounter += 1;
                } else {
                    this.rightMap.put(this.rightCounter, new Square());
                    this.rightMap.get(this.rightCounter).setPosition(new Vector2(Gdx.graphics.getWidth() * 13 / 16, Gdx.graphics.getHeight()));
                    this.rightMap.get(this.rightCounter).setTexture(this.texture);
                    if (this.rightCounter != 0 && this.rightMap.get(this.rightCounter - 1).getPosition().y > Gdx.graphics.getHeight() - this.square.getTexture().getHeight() + 5) {
                        this.rightMap.get(this.rightCounter).setPosition(new Vector2(Gdx.graphics.getWidth() * 13 / 16,
                                this.rightMap.get(rightCounter - 1).getPosition().y  + 2*this.square.getTexture().getHeight() + 5));
                    }
                    this.rightCounter += 1;
                }
            }
        }
    }

    public Map<Integer, Square> getLeftMap() {
        return leftMap;
    }

    public void setLeftMap(Map<Integer, Square> leftMap) {
        this.leftMap = leftMap;
    }

    public Map<Integer, Square> getCenterMap() {
        return centerMap;
    }

    public void setCenterMap(Map<Integer, Square> centerMap) {
        this.centerMap = centerMap;
    }

    public Map<Integer, Square> getRightMap() {
        return rightMap;
    }

    public void setRightMap(Map<Integer, Square> rightMap) {
        this.rightMap = rightMap;
    }

    public Integer getLeftCounter() {
        return leftCounter;
    }

    public void setLeftCounter(Integer leftCounter) {
        this.leftCounter = leftCounter;
    }

    public Integer getCenterCounter() {
        return centerCounter;
    }

    public void setCenterCounter(Integer centerCounter) {
        this.centerCounter = centerCounter;
    }

    public Integer getRightCounter() {
        return rightCounter;
    }

    public void setRightCounter(Integer rightCounter) {
        this.rightCounter = rightCounter;
    }

    public Integer getControlSending() {
        return controlSending;
    }

    public void setControlSending(Integer controlSending) {
        this.controlSending = controlSending;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
