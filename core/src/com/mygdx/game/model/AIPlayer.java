package com.mygdx.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.control.aI.PreferencesSettings;

import java.util.Map;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.mygdx.game.Squarz.HEIGHT;
import static com.mygdx.game.Squarz.WIDTH;

/**
 * Created by Max on 13/03/2018.
 */

// Color - number association
// red == 0; blue == 1; yellow == 2;
// Collision convention
//    red < blue < yellow < red

public class AIPlayer {
    private Player computer;
    private PreferencesSettings settings;
    private Texture texture;
    private com.mygdx.game.model.Square square;
    private Integer launcherCounter;
    private Integer deltaLauncher;
    private Integer renderCounter;
    //private SquareLimiter squareLimiterAi;

    public AIPlayer (PreferencesSettings set){
        this.settings = set;
        this.computer = new Player(set);
        this.texture = new Texture (Gdx.files.internal("square.png"));
        this.square = new com.mygdx.game.model.Square(set);
        //this.squareLimiterAi = new SquareLimiter(5);

        this.launcherCounter = 0;
        this.deltaLauncher = 70;

        this.renderCounter = 0;

    }

    public void send(CountDown countDown){
        this.launcherCounter += 1;
        if (countDown.getWorldTimer() > 0) {
            if (this.launcherCounter == this.settings.getDtLaunching()) {
                this.launcherCounter = 0;

                //setting the random color
                int colorKey = random(2);
                setTheRandomTexture(colorKey);

                //setting the random Texture in a random row
                int row = random(2);

                setTheRandomRow(row, colorKey);

            }
        }
    }


    public void setTheRandomTexture(int colorKey){
        if (colorKey == 0) {
            this.texture = new Texture(Gdx.files.internal("square_red.png"));
        } else if (colorKey == 1) {
            this.texture = new Texture(Gdx.files.internal("square_blue.png"));
        } else {
            this.texture = new Texture(Gdx.files.internal("square_yellow.png"));
        }
    }

    public void setTheRandomRow(int row, int colorKey) {
        if(!this.getComputer().getSquareLimiter().isOver(colorKey)) {

            if (row == 0) {
                incrementAI(computer.getLeft(), computer.getLeftCounter(), texture, row, colorKey);
                computer.setLeftCounter(computer.getLeftCounter() + 1);
            }
            if (row == 1) {
                incrementAI(computer.getMiddle(), computer.getMiddleCounter(), texture, row, colorKey);
                computer.setMiddleCounter(computer.getMiddleCounter() + 1);

            }
            if (row == 2) {
                incrementAI(computer.getRight(), computer.getRightCounter(), texture, row, colorKey);
                computer.setRightCounter(computer.getRightCounter() + 1);
            }
        }
    }

    public void incrementAI(Map<Integer, Square> row, Integer counter, Texture t, Integer columnKey, Integer colorkey) {
        row.put(counter, new Square(settings));
        this.computer.getSquareLimiter().counter(colorkey);
        if (columnKey == 0) {
            row.get(counter).setPosition(new Vector2(WIDTH * 5 / 16, HEIGHT));
            row.get(counter).setTexture(t);
            row.get(counter).setColorKey(colorkey);
            if (counter != this.getComputer().getFirstLeftSquaresKey() && counter > 0 && row.get(counter - 1).getPosition().y >= HEIGHT - (t.getHeight()) - 5) {
                row.get(counter).setPosition(new Vector2(Gdx.graphics.getWidth() * 5 / 16,
                        row.get(counter - 1).getPosition().y + t.getHeight() + 5));
            }
        } else if (columnKey == 1) {
            row.get(counter).setPosition(new Vector2(WIDTH * 9 / 16, HEIGHT));
            row.get(counter).setTexture(t);
            row.get(counter).setColorKey(colorkey);
            if (counter != this.getComputer().getFirstMiddleSquaresKey() && counter > 0 && row.get(counter - 1).getPosition().y >= HEIGHT - (t.getHeight()) - 5) {
                row.get(counter).setPosition(new Vector2(Gdx.graphics.getWidth() * 9 / 16,
                        row.get(counter - 1).getPosition().y + t.getHeight() + 5));
            }
        } else if (columnKey == 2) {
            row.get(counter).setPosition(new Vector2(WIDTH * 13 / 16, HEIGHT));
            row.get(counter).setTexture(t);
            row.get(counter).setColorKey(colorkey);
            if (counter != this.getComputer().getFirstRightSquaresKey() && counter > 0 && row.get(counter - 1).getPosition().y >= HEIGHT - (t.getHeight()) - 5) {
                row.get(counter).setPosition(new Vector2(Gdx.graphics.getWidth() * 13 / 16,
                        row.get(counter - 1).getPosition().y + t.getHeight() + 5));
            }
        }
    }


    // ---------  general getters and setters



    public PreferencesSettings getSettings() {
        return settings;
    }
    public void setSettings(PreferencesSettings settings) {
        this.settings = settings;
    }
    public Player getComputer() {
        return computer;
    }
    public void setComputer(Player computer) {
        this.computer = computer;
    }
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