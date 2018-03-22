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

    public Player(){
        this.left = new HashMap<Integer, Square>();
        this.middle = new HashMap<Integer, Square>();
        this.right = new HashMap<Integer, Square>();
        this.leftCounter = -1;
        this.middleCounter = -1;
        this.rightCounter = -1;
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
    public Integer getMiddleCounter() {
        return middleCounter;
    }

    public Integer getRightCounter() {
        return rightCounter;
    }



    public void increment(  Texture t, Integer columnKey, Integer colorKey){
        Integer counter = getCounter(columnKey);
        Map<Integer, Square> row = getRow(columnKey);
        row.put(counter, new Square());
        if(columnKey == 0) {
            row.get(counter).setPosition(new Vector2(WIDTH * 5 / 16, 0));
            row.get(counter).setTexture(t);
            row.get(counter).setColorKey(colorKey);
            if (counter > 0 && row.get(counter - 1).getPosition().y < t.getHeight() + 5) {
                row.get(counter).setPosition(new Vector2(Gdx.graphics.getWidth() * 5 / 16,
                        row.get(counter - 1).getPosition().y - t.getHeight() - 5));
            }
        }
        else if(columnKey == 1){
            row.get(counter).setPosition(new Vector2(WIDTH * 9 / 16, 0));
            row.get(counter).setTexture(t);
            row.get(counter).setColorKey(colorKey);
            if (counter > 0 && row.get(counter - 1).getPosition().y < t.getHeight() + 5) {
                row.get(counter).setPosition(new Vector2(Gdx.graphics.getWidth() * 9 / 16,
                        row.get(counter - 1).getPosition().y - t.getHeight() - 5));
            }
        }
        else if( columnKey == 2){
            row.get(counter).setPosition(new Vector2(WIDTH * 13 / 16, 0));
            row.get(counter).setTexture(t);
            row.get(counter).setColorKey(colorKey);
            if (counter > 0 && row.get(counter - 1).getPosition().y < t.getHeight() + 5) {
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
            if (counter > 0 && row.get(counter - 1).getPosition().y >= HEIGHT - (t.getHeight()) - 5) {
                row.get(counter).setPosition(new Vector2(Gdx.graphics.getWidth() * 5 / 16,
                        row.get(counter - 1).getPosition().y + t.getHeight() + 5));
            }
        }
        else if(columnKey == 1){
            row.get(counter).setPosition(new Vector2(WIDTH * 9 / 16, HEIGHT));
            row.get(counter).setTexture(t);
            row.get(counter).setColorKey(colorkey);
            if (counter > 0 && row.get(counter - 1).getPosition().y >= HEIGHT - (t.getHeight()) - 5) {
                row.get(counter).setPosition(new Vector2(Gdx.graphics.getWidth() * 9 / 16,
                        row.get(counter - 1).getPosition().y + t.getHeight() + 5));
            }
        }
        else if( columnKey == 2){
            row.get(counter).setPosition(new Vector2(WIDTH * 13 / 16, HEIGHT));
            row.get(counter).setTexture(t);
            row.get(counter).setColorKey(colorkey);
            if (counter > 0 && row.get(counter - 1).getPosition().y >= HEIGHT - (t.getHeight()) - 5) {
                row.get(counter).setPosition(new Vector2(Gdx.graphics.getWidth() * 13 / 16,
                        row.get(counter - 1).getPosition().y + t.getHeight() + 5));
            }
        }
    }


    //returns the column's counter and increments it
    private Integer getCounter(Integer columnKey){
        Integer counter;
        if (columnKey == 0){
            counter = leftCounter;
            leftCounter += 1;

        }
        else if (columnKey == 1){
            counter = middleCounter;
            middleCounter += 1;
        }
        else{
            counter = rightCounter;
            rightCounter += 1;
        }
        return counter;

    }

    private Map<Integer, Square> getRow(Integer columnKey){
        if (columnKey == 0){
            return getLeft();
        }
        else if (columnKey == 1){
            return getMiddle();
        }
        return getRight();
    }

    public void decrement(Map<Integer, Square> row, Integer counter, Integer columnKey){
        if(columnKey == 0) {
            row.get(counter).setPosition(new Vector2(10, 10));
            row.get(counter).setSpeed(0);
        }
        else if(columnKey == 1){
            row.get(counter).setPosition(new Vector2(10, 10));
            row.get(counter).setSpeed(0);
        }
        else if( columnKey == 2){
            row.get(counter).setPosition(new Vector2(10, 10));
            row.get(counter).setSpeed(0);
        }
    }
}
