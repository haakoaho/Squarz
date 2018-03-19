package com.mygdx.game.control.aI;

import com.mygdx.game.model.Bonus;
import com.mygdx.game.model.Timer;

/**
 * Created by mathi on 06/03/2018.
 */

public class PreferencesSettings {
    //setAILevels parameters
    private int levelKey;
    private int stepX;
    private float dtLaunching;
    private float dtWaves;
    private int squaresize;
    //setBonus
    private Bonus bonuses;
    //setTimer
    private Timer timer;

    public PreferencesSettings(){
        //default AI level settings
        this.levelKey = 1;
        this.stepX = 3;
        this.dtLaunching = 70;
        this.dtWaves = 300;
        this.squaresize = 3;

        this.bonuses = new Bonus();

        this.timer = new Timer();
    }

    public int getLevelKey() { return levelKey; }
    public void setLevelKey(int levelKey) { this.levelKey = levelKey; }
    public int getStepX() {
        return stepX;
    }
    public void setStepX(int stepX) {
        this.stepX = stepX;
    }
    public float getDtLaunching() {
        return dtLaunching;
    }
    public void setDtLaunching(float dtLaunching) {
        this.dtLaunching = dtLaunching;
    }
    public float getDtWaves() {
        return dtWaves;
    }
    public void setDtWaves(float dtWaves) {
        this.dtWaves = dtWaves;
    }
    public int getSquaresize() { return squaresize; }
    public void setSquaresize(int squaresize) { this.squaresize = squaresize; }
    public Bonus getBonuses() { return bonuses; }
    public void setBonuses(Bonus bonuses) { this.bonuses = bonuses; }
    public Timer getTimer() { return timer; }
    public void setTimer(Timer timer) { this.timer = timer; }



    public void AILevelUp() {
        int currentLevel = this.getLevelKey();
        if (currentLevel != 3) {
            if (levelKey == 0) {
                setMedium();
            }
            else if (levelKey == 1) {
                setAdvanced();
            }
            else if (levelKey == 2) {
                setExpert();
            }
        }
    }

    public void AILevelDown(){
        int currentLevel = this.getLevelKey();
        if (currentLevel != 0) {
            if (levelKey == 1) {
                setBeginner();
            }
            else if (levelKey == 2) {
                setMedium();
            }
            else if (levelKey == 3) {
                setAdvanced();
            }
        }
    }

    public void setBeginner(){
        setLevelKey(0);
        setStepX(3);
        setDtLaunching(80);
        setDtWaves(400);
        setSquaresize(2);
    }

    public void setMedium(){
        setLevelKey(1);
        setStepX(3);
        setDtLaunching(60);
        setDtWaves(300);
        setSquaresize(3);
    }

    public void setAdvanced(){
        setLevelKey(2);
        setStepX(4);
        setDtLaunching(50);
        setDtWaves(200);
        setSquaresize(5);
    }

    public void setExpert(){
        setLevelKey(3);
        setStepX(5);
        setDtLaunching(40);
        setDtWaves(150);
        setSquaresize(7);
    }
}