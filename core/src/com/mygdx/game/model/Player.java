package com.mygdx.game.model;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.control.aI.PreferencesSettings;
import com.mygdx.game.model.AbstractFactory.CountdownDuration.ICountdownDuration;

import java.util.HashMap;
import java.util.Map;

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

        if(toRemoveKey != getFirstSquareKey(columnKey)){
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
        if(columnKey == 0){
            toReturn = getFirstLeftSquaresKey();
        }
        else if(columnKey == 1){
            toReturn = getFirstMiddleSquaresKey();
        }
        else{
            toReturn = getFirstRightSquaresKey();
        }
        return  toReturn;
    }
    public void setFirstSquareKey(Integer columnKey, Integer newFirst){
        if(columnKey == 0){
            setFirstLeftSquaresKey(newFirst);
        }
        else if(columnKey == 1){
            setFirstMiddleSquaresKey(newFirst);
        }
        else{
            setFirstRightSquaresKey(newFirst);
        }
    }

    public Map<Integer, Square> getMap(Integer columnKey){
        Map<Integer, Square> toReturn;
        if(columnKey == 0){
            toReturn = getLeft();
        }
        else if(columnKey == 1){
            toReturn = getMiddle();
        }
        else{
            toReturn = getRight();
        }
        return  toReturn;
    }

    //returns the column's counter
    public Integer getCounter(Integer columnKey){
        Integer counter;
        if (columnKey == 0){
            counter = leftCounter;
        }
        else if (columnKey == 1){
            counter = middleCounter;
        }
        else{
            counter = rightCounter;
        }
        return counter;
    }
    public void setCounter(Integer columnKey, Integer newValue){
        if (columnKey == 0){
            leftCounter = newValue;
        }
        else if (columnKey == 1){
            middleCounter = newValue;
        }
        else{
            rightCounter = newValue;
        }
    }


    public void incrementCounter(Integer columnKey){
        if (columnKey == 0){
            leftCounter += 1;
        }
        else if (columnKey == 1){
            middleCounter += 1;
        }
        else{
            rightCounter += 1;
        }
    }

    public void handleOverLapping(Integer columnKey, Texture t, Integer counter, Map<Integer, Square> row){
        if (counter != this.getFirstSquareKey(columnKey) && counter > 0 && row.get(counter - 1).getPosition().y < t.getHeight() + 5) {
            row.get(counter).setPosition(new Vector2(WIDTH * (3+(columnKey*2))/8,
                    row.get(counter - 1).getPosition().y - t.getHeight() - 5));
        }
    }


    //----------   setters and getters

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

    public PreferencesSettings getSet() {
        return set;
    }

    public void setSet(PreferencesSettings set) {
        this.set = set;
    }
}