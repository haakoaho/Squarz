package com.mygdx.game.model;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;
import java.util.Map;

import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;

/**
 * Created by mathi on 13/03/2018.
 */

// Color - number association
// red == 0; blue == 1; yellow == 2;
// Collision convention
//    red < blue < yellow < red

public class Player {
    private Map<Integer, Square> left, middle, right;
    private Integer leftCounter, middleCounter, rightCounter;
    private Map<Integer, Integer> leftColor, middleColor, rightColor;

    public Player(){
        this.left = new HashMap<Integer, Square>();
        this.middle = new HashMap<Integer, Square>();
        this.right = new HashMap<Integer, Square>();
        this.leftCounter = 0;
        this.middleCounter = 0;
        this.rightCounter = 0;
        this.leftColor = new HashMap<Integer, Integer>();
        this.middleColor = new HashMap<Integer, Integer>();
        this.rightColor = new HashMap<Integer, Integer>();
    }

    //setters and getters;

    public Map<Integer, Square> getLeft() {
        return left;
    }
    public void setLeft(Map<Integer, Square> left) {
        this.left = left;
    }
    public Map<Integer, Square> getMiddle() {
        return middle;
    }
    public void setMiddle(Map<Integer, Square> middle) {
        this.middle = middle;
    }
    public Map<Integer, Square> getRight() {
        return right;
    }
    public void setRight(Map<Integer, Square> right) {
        this.right = right;
    }
    public Integer getLeftCounter() {
        return leftCounter;
    }
    public void setLeftCounter(Integer leftCounter) {
        this.leftCounter = leftCounter;
    }
    public Integer getMiddleCounter() {
        return middleCounter;
    }
    public void setMiddleCounter(Integer middleCounter) {
        this.middleCounter = middleCounter;
    }
    public Integer getRightCounter() {
        return rightCounter;
    }
    public void setRightCounter(Integer rightCounter) {
        this.rightCounter = rightCounter;
    }
    public Map<Integer, Integer> getLeftColor() {
        return leftColor;
    }
    public void setLeftColor(Map<Integer, Integer> leftColor) {
        this.leftColor = leftColor;
    }
    public Map<Integer, Integer> getMiddleColor() {
        return middleColor;
    }
    public void setMiddleColor(Map<Integer, Integer> middleColor) {
        this.middleColor = middleColor;
    }
    public Map<Integer, Integer> getRightColor() {
        return rightColor;
    }
    public void setRightColor(Map<Integer, Integer> rightColor) {
        this.rightColor = rightColor;
    }

    public void increment(Map<Integer, Square> row, Integer counter, Texture t, Integer columnKey, Integer colorkey){
        row.put(counter, new Square());
        if(columnKey == 0) {
            row.get(counter).setPosition(new Vector2(WIDTH * 5 / 16, 0));
            row.get(counter).setTexture(t);
            row.get(counter).setColorKey(colorkey);
            if (counter != 0 && row.get(counter - 1).getPosition().y < t.getHeight() + 5) {
                row.get(counter).setPosition(new Vector2(Gdx.graphics.getWidth() * 5 / 16,
                        row.get(counter - 1).getPosition().y - t.getHeight() - 5));
            }
        }
        else if(columnKey == 1){
            row.get(counter).setPosition(new Vector2(WIDTH * 9 / 16, 0));
            row.get(counter).setTexture(t);
            row.get(counter).setColorKey(colorkey);
            if (counter != 0 && row.get(counter - 1).getPosition().y < t.getHeight() + 5) {
                row.get(counter).setPosition(new Vector2(Gdx.graphics.getWidth() * 9 / 16,
                        row.get(counter - 1).getPosition().y - t.getHeight() - 5));
            }
        }
        else if( columnKey == 2){
            row.get(counter).setPosition(new Vector2(WIDTH * 13 / 16, 0));
            row.get(counter).setTexture(t);
            row.get(counter).setColorKey(colorkey);
            if (counter != 0 && row.get(counter - 1).getPosition().y < t.getHeight() + 5) {
                row.get(counter).setPosition(new Vector2(Gdx.graphics.getWidth() * 13 / 16,
                        row.get(counter - 1).getPosition().y - t.getHeight() - 5));
            }
        }
    }

    public void incrementAI(Map<Integer, Square> row, Integer counter, Texture t, Integer columnKey, Integer colorkey){
        row.put(counter, new Square());
        if(columnKey == 0) {
            row.get(counter).setPosition(new Vector2(WIDTH * 5 / 16, HEIGHT));
            row.get(counter).setTexture(t);
            row.get(counter).setColorKey(colorkey);
            if (counter != 0 && row.get(counter - 1).getPosition().y >= HEIGHT - (t.getHeight()) - 5) {
                row.get(counter).setPosition(new Vector2(Gdx.graphics.getWidth() * 5 / 16,
                        row.get(counter - 1).getPosition().y - t.getHeight() - 5));
            }
        }
        else if(columnKey == 1){
            row.get(counter).setPosition(new Vector2(WIDTH * 9 / 16, HEIGHT));
            row.get(counter).setTexture(t);
            row.get(counter).setColorKey(colorkey);
            if (counter != 0 && row.get(counter - 1).getPosition().y >= HEIGHT - (t.getHeight()) - 5) {
                row.get(counter).setPosition(new Vector2(Gdx.graphics.getWidth() * 9 / 16,
                        row.get(counter - 1).getPosition().y - t.getHeight() - 5));
            }
        }
        else if( columnKey == 2){
            row.get(counter).setPosition(new Vector2(WIDTH * 13 / 16, HEIGHT));
            row.get(counter).setTexture(t);
            row.get(counter).setColorKey(colorkey);
            if (counter != 0 && row.get(counter - 1).getPosition().y >= HEIGHT - (t.getHeight()) - 5) {
                row.get(counter).setPosition(new Vector2(Gdx.graphics.getWidth() * 13 / 16,
                        row.get(counter - 1).getPosition().y - t.getHeight() - 5));
            }
        }
    }
}
