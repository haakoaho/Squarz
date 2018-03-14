package com.mygdx.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.control.aI.PreferencesSettings;
import com.mygdx.game.view.beginning.Pref;
import com.mygdx.game.view.beginning.Settings;

import java.util.HashMap;
import java.util.Map;

import static com.badlogic.gdx.math.MathUtils.random;

/**
 * Created by Antoine Dc on 13/03/2018.
 */

public class AIPlayer {
    private Player computer;
    private PreferencesSettings settings;
    private Integer controlSending;
    private Texture texture;
    private Square square;
    private Integer deltaLauncher;
    private Integer renderCounter;


    public AIPlayer (){
        this.computer = new Player();
        this.settings = new PreferencesSettings();
        this.controlSending = 0;
        this.texture = new Texture (Gdx.files.internal("square.png"));
        this.square = new Square();
        this.deltaLauncher = 20;
        this.renderCounter = 0;
    }

    public void send(CountDown countDown){
        this.renderCounter += 1;
        if (countDown.getWorldTimer() > 0) {
            if (this.renderCounter == deltaLauncher) {
                this.renderCounter = 0;

                //setting the random color
                int colorKey = random(2);
                setTheRandomTexture(colorKey);

                //setting the random Texture in a random row
                int row = random(2);
                setTheRandomRow(row);

                /*if (row == 0) {
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
                }*/
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

    public void setTheRandomRow(int row) {
        if (row == 0) {
            computer.incrementAI(computer.getLeft(), computer.getLeftCounter(), texture, row);
            computer.setLeftCounter(computer.getLeftCounter() + 1);
        }if(row == 1){
            computer.incrementAI(computer.getMiddle(), computer.getMiddleCounter(), texture, row);
            computer.setMiddleCounter(computer.getMiddleCounter() + 1);

        }if(row == 2){
            computer.incrementAI(computer.getRight(), computer.getRightCounter(), texture, row);
            computer.setRightCounter(computer.getRightCounter() + 1);

        }

    }






    // ---------  general getters and setters

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
}