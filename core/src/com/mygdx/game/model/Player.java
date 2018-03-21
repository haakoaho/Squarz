package com.mygdx.game.model;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.control.aI.PreferencesSettings;
import com.mygdx.game.view.beginning.Pref;

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
    private SquareLimiter squareLimiter;
    private PreferencesSettings set;

    //key of the first square still in the game for each of the left - middle or right map
    private Integer firstLeftSquaresKey, firstMiddleSquaresKey, firstRightSquaresKey;


    public Player(PreferencesSettings set) {
        this.set = set;

        this.left = new HashMap<Integer, Square>();
        this.middle = new HashMap<Integer, Square>();
        this.right = new HashMap<Integer, Square>();
        this.leftCounter = 0;
        this.middleCounter = 0;
        this.rightCounter = 0;

        this.squareLimiter = new SquareLimiter(5);

        this.firstLeftSquaresKey = 0;
        this.firstMiddleSquaresKey = 0;
        this.firstRightSquaresKey = 0;
    }


    public void increment(Map<Integer, Square> row, Integer counter, Texture t, Integer columnKey, Integer colorkey) {
        row.put(counter, new Square(set));
        squareLimiter.counter(colorkey);
        if (columnKey == 0) {
            row.get(counter).setPosition(new Vector2(WIDTH * 5 / 16, 0));
            row.get(counter).setTexture(t);
            row.get(counter).setColorKey(colorkey);
            //overlapping
            if (counter != this.getFirstLeftSquaresKey() && counter > 0 && row.get(counter - 1).getPosition().y < t.getHeight() + 5) {
                row.get(counter).setPosition(new Vector2(Gdx.graphics.getWidth() * 5 / 16,
                        row.get(counter - 1).getPosition().y - t.getHeight() - 5));
            }
        } else if (columnKey == 1) {
            row.get(counter).setPosition(new Vector2(WIDTH * 9 / 16, 0));
            row.get(counter).setTexture(t);
            row.get(counter).setColorKey(colorkey);
            if (counter != this.getFirstMiddleSquaresKey() && counter > 0 && row.get(counter - 1).getPosition().y < t.getHeight() + 5) {
                row.get(counter).setPosition(new Vector2(Gdx.graphics.getWidth() * 9 / 16,
                        row.get(counter - 1).getPosition().y - t.getHeight() - 5));
            }
        } else if (columnKey == 2) {
            row.get(counter).setPosition(new Vector2(WIDTH * 13 / 16, 0));
            row.get(counter).setTexture(t);
            row.get(counter).setColorKey(colorkey);
            if (counter != this.getFirstRightSquaresKey() && counter > 0 && row.get(counter - 1).getPosition().y < t.getHeight() + 5) {
                row.get(counter).setPosition(new Vector2(Gdx.graphics.getWidth() * 13 / 16,
                        row.get(counter - 1).getPosition().y - t.getHeight() - 5));
            }
        }

    }
    public void decrement(Map<Integer, Square> row, Integer toRemoveKey, Integer columnKey) {
        if (columnKey == 0) {

            row.remove(toRemoveKey);

            if (toRemoveKey == getFirstLeftSquaresKey()) {
                firstLeftSquaresKey++;
            } else {
                for (int i = toRemoveKey; toRemoveKey > firstLeftSquaresKey; i--) {
                    row.put(i, row.get(i - 1));
                }
                row.remove(firstLeftSquaresKey);
                firstLeftSquaresKey++;
            }
        }if (columnKey == 1) {

            row.remove(toRemoveKey);

            if (toRemoveKey == getFirstMiddleSquaresKey()) {
                firstMiddleSquaresKey++;
            } else {
                for (int i = toRemoveKey; toRemoveKey > firstMiddleSquaresKey; i--) {
                    row.put(i, row.get(i - 1));
                }
                row.remove(firstMiddleSquaresKey);
                firstMiddleSquaresKey++;
            }
        }if (columnKey == 2) {

            row.remove(toRemoveKey);

            if (toRemoveKey == getFirstRightSquaresKey()) {
                firstRightSquaresKey++;
            } else {
                for (int i = toRemoveKey; toRemoveKey > firstRightSquaresKey; i--) {
                    row.put(i, row.get(i - 1));
                }
                row.remove(firstRightSquaresKey);
                firstRightSquaresKey++;
            }
        }
    }

    //used in collision to make code less cumbersome
    public Integer getFirstSquareKey(Integer rowKey){
        int toReturn;
        if(rowKey == 0){
            toReturn = getFirstLeftSquaresKey();
        }
        else if(rowKey == 1){
            toReturn = getFirstMiddleSquaresKey();
        }
        else{
            toReturn = getFirstRightSquaresKey();
        }
        return  toReturn;
    }
    public Integer getCounter(Integer rowKey){
        int toReturn;
        if(rowKey == 0){
            toReturn = getLeftCounter();
        }
        else if(rowKey == 1){
            toReturn = getMiddleCounter();
        }
        else{
            toReturn = getRightCounter();
        }
        return  toReturn;
    }
    public Map<Integer, Square> getMap(Integer rowKey){
        Map<Integer, Square> toReturn;
        if(rowKey == 0){
            toReturn = getLeft();
        }
        else if(rowKey == 1){
            toReturn = getMiddle();
        }
        else{
            toReturn = getRight();
        }
        return  toReturn;
    }




    //setters and getters

    public Integer getFirstLeftSquaresKey() {
        return firstLeftSquaresKey;
    }

    public void setFirstLeftSquaresKey(Integer firstLeftSquaresKey) {
        this.firstLeftSquaresKey = firstLeftSquaresKey;
    }

    public Integer getFirstMiddleSquaresKey() {
        return firstMiddleSquaresKey;
    }

    public void setFirstMiddleSquaresKey(Integer firstMiddleSquaresKey) {
        this.firstMiddleSquaresKey = firstMiddleSquaresKey;
    }

    public Integer getFirstRightSquaresKey() {
        return firstRightSquaresKey;
    }

    public void setFirstRightSquaresKey(Integer firstRightSquaresKey) {
        this.firstRightSquaresKey = firstRightSquaresKey;
    }

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

    public SquareLimiter getSquareLimiter() {
        return squareLimiter;
    }

    public void setSquareLimiter(SquareLimiter squareLimiter) {
        this.squareLimiter = squareLimiter;
    }
}