package com.mygdx.game.control.aI;

import com.mygdx.game.model.Bonus;
import com.mygdx.game.model.CountDown;

import static com.mygdx.game.Squarz.HEIGHT;

/**
 * Created by mathi on 06/03/2018.
 */

public class PreferencesSettings {
    //setAILevels parameters
    private int levelKey;
    private int stepX;
    private float dtLaunching;
    private float dtWaves;
    private Bonus bonuses;

    public PreferencesSettings(){
        //default AI level settings
        setMedium();

        this.bonuses = new Bonus();
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
    public Bonus getBonuses() { return bonuses; }
    public void setBonuses(Bonus bonuses) { this.bonuses = bonuses; }

    public String getStringLevel() {
        String res = "";
        int levelKey = this.getLevelKey();
        if (levelKey==0) {res="Beginner";}
        if (levelKey==1) {res="Medium";}
        if (levelKey==2) {res="Advanced";}
        if (levelKey==3) {res="Expert";}
        return res;
    }

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
        setStepX(HEIGHT/6);
        setDtLaunching(80);
        setDtWaves(400);
    }

    public void setMedium(){
        setLevelKey(1);
        setStepX(HEIGHT/6);
        setDtLaunching(60);
        setDtWaves(300);
    }

    public void setAdvanced(){
        setLevelKey(2);
        setStepX(HEIGHT/6);
        setDtLaunching(50);
        setDtWaves(200);
    }

    public void setExpert(){
        setLevelKey(3);
        setStepX(HEIGHT/6);
        setDtLaunching(40);
        setDtWaves(150);
    }
}