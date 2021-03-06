package com.mygdx.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.control.aI.PreferencesSettings;

import java.util.Map;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;
import static com.mygdx.game.Squarz.format;

/**
 * Created by Max on 13/03/2018.
 */

// Color - number association
// red == 0; blue == 1; yellow == 2;
// Collision convention
//    red < blue < yellow < red

public class AIPlayer extends Player{
    private Texture texture;
    private com.mygdx.game.model.Square square;
    private Integer launcherCounter;
    private Integer deltaLauncher;
    private Integer renderCounter;

    private boolean wave1 = true;
    private boolean wave2 = true;
    private boolean wave3 = true;
    private boolean wave4 = true;
    private boolean wave5 = true;

    public AIPlayer (PreferencesSettings set, CountDown countDown){
        super(set, countDown);
        this.texture = new Texture (Gdx.files.internal(format+"/square/square.png"));
        this.square = new com.mygdx.game.model.Square(set);

        this.launcherCounter = 0;
        this.deltaLauncher = 70;

        this.renderCounter = 0;
    }

    public void send(CountDown countDown){
        this.launcherCounter += 1;
        if (countDown.getWorldTimer() > 0) {
            if (this.launcherCounter == this.getSet().getDtLaunching() ) {
                this.launcherCounter = 0;

                //setting the random color
                int colorKey = random(2);
                setTheRandomTexture(colorKey);

                //setting the random Texture in a random column
                int column = random(2);
                setTheRandomColumn(column, colorKey);
            }
        }
    }

    public void prgrmdSending(CountDown countDown) {
        this.launcherCounter += 1;
        if (countDown.getWorldTimer() > 0) {
            //random flow
            if (this.launcherCounter == this.getSet().getDtLaunching()) {
                this.launcherCounter = 0;

                //setting the random color
                int colorKey = random(2);
                setTheRandomTexture(colorKey);

                //setting the random Texture in a random row
                int row = random(2);

                setTheRandomColumn(row, colorKey);
            }

            myWave(countDown);



        }

    }

    public void myWave(CountDown countDown){
        if (wave1) {
            wave1 =  createAWave(55, countDown);
        }
        if (wave2) {
            wave2 =  createAWave(40, countDown);
            //createAWave(40, countDown);
        }
        if (wave3) {
            wave3 =  createAWave(30, countDown);
        }
        if (wave4) {
            wave4 =  createAWave(25, countDown);
        }
        if (wave5) {
            wave5 =  createAWave(10, countDown);
            //createAWave(40, countDown);
        }

    }


    public void setOneSquare(int row, int colorkey){
        setTheRandomTexture(colorkey);
        setTheRandomColumn(row,colorkey);
    }

    public boolean createAWave(int time, CountDown countDown){

        if (time == countDown.getWorldTimer()){
            setOneSquare(random(2),random(2));
            setOneSquare(random(2),random(2));
            setOneSquare(random(2),random(2));
            return false;
        }
        return true;
    }


    public void setTheRandomTexture(int colorKey){
        if (colorKey == 0) {
            this.texture = new Texture(Gdx.files.internal(format+"/square/square_red.png"));
        } else if (colorKey == 1) {
            this.texture = new Texture(Gdx.files.internal(format+"/square/square_blue.png"));
        } else {
            this.texture = new Texture(Gdx.files.internal(format+"/square/square_yellow.png"));
        }
    }

    public void setTheRandomColumn(int columnKey, int colorKey) {
        if(!this.getSquareLimiter().isOver(colorKey)) {

            if (columnKey == 0) {
                incrementOpponent(texture, columnKey, colorKey);
            }
            if (columnKey == 1) {
                incrementOpponent(texture, columnKey, colorKey);

            }
            if (columnKey == 2) {
                incrementOpponent(texture, columnKey, colorKey);
            }
        }
    }

    public void incrementOpponent(Texture t, Integer columnKey, Integer colorkey) {
        Integer counter = this.getCounter(columnKey);
        Map<Integer, Square> row = this.getMap(columnKey);
        //back end
        row.put(counter, new Square(this.getSet()));
        incrementCounter(columnKey);
        this.getSquareLimiter().minusOne(colorkey);

        //front end
        row.get(counter).setPosition(new Vector2(WIDTH * (3+(2*columnKey))/8, HEIGHT));
        row.get(counter).setTexture(t);
        row.get(counter).setColorKey(colorkey);

        handleAIOverLapping(columnKey, t, counter, row);
    }

    public void handleAIOverLapping(Integer columnKey, Texture t, Integer counter, Map<Integer, Square> map){
        if (counter != this.getFirstSquareKey(columnKey) && counter > 0 && map.get(counter - 1).getPosition().y >= HEIGHT - (t.getHeight()) - 5) {
            map.get(counter).setPosition(new Vector2(WIDTH * 3 / 8,
                    map.get(counter - 1).getPosition().y + t.getHeight() + 5));
        }
    }

    // ---------  general getters and setters

    public Texture getTexture() {
        return texture;
    }
    public void setTexture(Texture texture) {
        this.texture = texture;
    }
    public com.mygdx.game.model.Square getSquare() {
        return square;
    }
    public void setSquare(com.mygdx.game.model.Square square) {
        this.square = square;
    }
    public Integer getLauncherCounter() {
        return launcherCounter;
    }
    public void setLauncherCounter(Integer launcherCounter) {
        this.launcherCounter = launcherCounter;
    }
    public Integer getDeltaLauncher() {
        return deltaLauncher;
    }
    public void setDeltaLauncher(Integer deltaLauncher) {
        this.deltaLauncher = deltaLauncher;
    }
    public Integer getRenderCounter() {
        return renderCounter;
    }
    public void setRenderCounter(Integer renderCounter) {
        this.renderCounter = renderCounter;
    }
}