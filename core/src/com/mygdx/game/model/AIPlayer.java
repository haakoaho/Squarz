package com.mygdx.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.control.aI.PreferencesSettings;

import static com.badlogic.gdx.math.MathUtils.random;

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
    private Square square;
    private Integer launcherCounter;


    public AIPlayer (){
        this.computer = new Player();
        this.settings = new PreferencesSettings();
        this.texture = new Texture (Gdx.files.internal("square.png"));
        this.square = new Square();
        this.launcherCounter = 0;
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
<<<<<<< HEAD
                setTheRandomRow(row);
=======
                setTheRandomRow(row, colorKey);


>>>>>>> Maxime
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
        if (row == 0) {
<<<<<<< HEAD
            computer.incrementAI(computer.getLeft(), computer.getLeftCounter(), texture, row);
            computer.getLeft().get(computer.getLeftCounter()).setSpeed(settings.getStepX());
            computer.setLeftCounter(computer.getLeftCounter() + 1);
        }if(row == 1){
            computer.incrementAI(computer.getMiddle(), computer.getMiddleCounter(), texture, row);
            computer.getMiddle().get(computer.getMiddleCounter()).setSpeed(settings.getStepX());
            computer.setMiddleCounter(computer.getMiddleCounter() + 1);

        }if(row == 2){
            computer.incrementAI(computer.getRight(), computer.getRightCounter(), texture, row);
            computer.getRight().get(computer.getRightCounter()).setSpeed(settings.getStepX());
=======
            computer.incrementAI(computer.getLeft(), computer.getLeftCounter(), texture, row, colorKey);
            computer.setLeftCounter(computer.getLeftCounter() + 1);
        }if(row == 1){
            computer.incrementAI(computer.getMiddle(), computer.getMiddleCounter(), texture, row, colorKey);
            computer.setMiddleCounter(computer.getMiddleCounter() + 1);

        }if(row == 2){
            computer.incrementAI(computer.getRight(), computer.getRightCounter(), texture, row, colorKey);
>>>>>>> Maxime
            computer.setRightCounter(computer.getRightCounter() + 1);
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
}