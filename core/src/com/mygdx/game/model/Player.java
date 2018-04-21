package com.mygdx.game.model;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.control.aI.PreferencesSettings;
import com.mygdx.game.model.AbstractFactory.CountdownDuration.ICountdownDuration;

import java.util.HashMap;
import java.util.Map;

import static com.mygdx.game.Squarz.WIDTH;



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


    public Player(PreferencesSettings set, ICountdownDuration countDown) {
        this.set = set;

        this.left = new HashMap<Integer, Square>();
        this.middle = new HashMap<Integer, Square>();
        this.right = new HashMap<Integer, Square>();
        this.leftCounter = 0;
        this.middleCounter = 0;
        this.rightCounter = 0;

        if (countDown.getWorldTimer()==30) {this.squareLimiter = new SquareLimiter(7, 1);}
        if (countDown.getWorldTimer()==45) {this.squareLimiter = new SquareLimiter(10, 1);}
        if (countDown.getWorldTimer()==60) {this.squareLimiter = new SquareLimiter(15, 1);}


        this.firstLeftSquaresKey = 0;
        this.firstMiddleSquaresKey = 0;
        this.firstRightSquaresKey = 0;
    }


    public void increment(Texture t, Integer columnKey, Integer colorKey) {
            //local variables
            Integer counter = getCounter(columnKey);
            Map<Integer, Square> column = getMap(columnKey);

            //back end info
            column.put(counter, new Square(set));
            incrementCounter(columnKey);
            squareLimiter.minusOne(colorKey);

            //front end info
            column.get(counter).setPosition(new Vector2(WIDTH * (3 + (columnKey * 2)) / 8, 0));
            column.get(counter).setTexture(t);
            column.get(counter).setColorKey(colorKey);

            handleOverLapping(columnKey, t, counter, column);
        //}

    }



    public void decrement(Integer toRemoveKey, Integer columnKey) {
        Map<Integer, Square> column = getMap(columnKey);
        column.remove(toRemoveKey);

        if(!toRemoveKey.equals(getFirstSquareKey(columnKey))){
            for (int i = toRemoveKey; toRemoveKey > getFirstSquareKey(columnKey); i--) {
                column.put(i, column.get(i - 1));
            }
            column.remove(getFirstSquareKey(columnKey));
        }
        setFirstSquareKey(columnKey, getFirstSquareKey(columnKey) + 1);
    }

    //used in collision to make code less cumbersome
    public Integer getFirstSquareKey(Integer columnKey){
        int toReturn;
        switch (columnKey) {
            case 0:
                toReturn = getFirstLeftSquaresKey();
                break;
            case 1:
                toReturn = getFirstMiddleSquaresKey();
                break;
            default:
                toReturn = getFirstRightSquaresKey();
                break;
        }
        return  toReturn;
    }
    public void setFirstSquareKey(Integer columnKey, Integer newFirst){
        switch (columnKey) {
            case 0:
                setFirstLeftSquaresKey(newFirst);
                break;
            case 1:
                setFirstMiddleSquaresKey(newFirst);
                break;
            default:
                setFirstRightSquaresKey(newFirst);
                break;
        }
    }

    public Map<Integer, Square> getMap(Integer columnKey){
        Map<Integer, Square> toReturn;
        switch (columnKey) {
            case 0:
                toReturn = getLeft();
                break;
            case 1:
                toReturn = getMiddle();
                break;
            default:
                toReturn = getRight();
                break;
        }
        return  toReturn;
    }

    //returns the column's counter
    public Integer getCounter(Integer columnKey){
        Integer counter;
        switch (columnKey) {
            case 0:
                counter = leftCounter;
                break;
            case 1:
                counter = middleCounter;
                break;
            default:
                counter = rightCounter;
                break;
        }
        return counter;
    }
    public void setCounter(Integer columnKey, Integer newValue){
        switch (columnKey) {
            case 0:
                leftCounter = newValue;
                break;
            case 1:
                middleCounter = newValue;
                break;
            default:
                rightCounter = newValue;
                break;
        }
    }


    void incrementCounter(Integer columnKey){
        switch (columnKey) {
            case 0:
                leftCounter += 1;
                break;
            case 1:
                middleCounter += 1;
                break;
            default:
                rightCounter += 1;
                break;
        }
    }

    private void handleOverLapping(Integer columnKey, Texture t, Integer counter, Map<Integer, Square> row){
        if (!counter.equals(this.getFirstSquareKey(columnKey)) && counter > 0 && row.get(counter - 1).getPosition().y < t.getHeight() + 5) {
            row.get(counter).setPosition(new Vector2(WIDTH * (3+(columnKey*2))/8,
                    row.get(counter - 1).getPosition().y - t.getHeight() - 5));
        }
    }


    //----------   setters and getters

    private Integer getFirstLeftSquaresKey() {
        return firstLeftSquaresKey;
    }

    private void setFirstLeftSquaresKey(Integer firstLeftSquaresKey) {
        this.firstLeftSquaresKey = firstLeftSquaresKey;
    }

    private Integer getFirstMiddleSquaresKey() {
        return firstMiddleSquaresKey;
    }

    private void setFirstMiddleSquaresKey(Integer firstMiddleSquaresKey) {
        this.firstMiddleSquaresKey = firstMiddleSquaresKey;
    }

    private Integer getFirstRightSquaresKey() {
        return firstRightSquaresKey;
    }

    private void setFirstRightSquaresKey(Integer firstRightSquaresKey) {
        this.firstRightSquaresKey = firstRightSquaresKey;
    }

    private Map<Integer, Square> getLeft() {
        return left;
    }

    private Map<Integer, Square> getMiddle() {
        return middle;
    }

    private Map<Integer, Square> getRight() {
        return right;
    }

    public SquareLimiter getSquareLimiter() {
        return squareLimiter;
    }

    PreferencesSettings getSet() {
        return set;
    }

}