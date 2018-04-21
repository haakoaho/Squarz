package com.mygdx.game.control.aI;

import com.mygdx.game.model.Bonus;

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
    private Bonus bonus1;
    private Bonus bonus2;

    public PreferencesSettings(){
        //default AI level settings
        setMedium();

        this.bonus1 = new Bonus();
        this.bonus2 = new Bonus();
    }

    public int getLevelKey() { return levelKey; }
    private void setLevelKey(int levelKey) { this.levelKey = levelKey; }
    public int getStepX() {
        return stepX;
    }
    public void setStepX(int stepX) {
        this.stepX = stepX;
    }
    public float getDtLaunching() {
        return dtLaunching;
    }
    private void setDtLaunching(float dtLaunching) {
        this.dtLaunching = dtLaunching;
    }

    private void setDtWaves(float dtWaves) {
        this.dtWaves = dtWaves;
    }
    public Bonus getBonus1() { return bonus1; }

    public Bonus getBonus2() { return bonus2; }

    public String getStringLevel() {
        String res = "";
        int levelKey = this.getLevelKey();
        if (levelKey==0) {res="Beginner";}
        if (levelKey==1) {res="Medium";}
        if (levelKey==2) {res="Advanced";}
        if (levelKey==3) {res="Expert";}
        return res;
    }

    public String getStringBonus1() {
        String res = "";
        int bonusKey = this.bonus1.getBonusKey();
        if (bonusKey==0) {res="None";}
        if (bonusKey==1) {res="The Punisher";}
        if (bonusKey==2) {res="The Nurse";}
        if (bonusKey==3) {res="Mr. Propre";}
        return res;
    }

    public String getStringBonus2() {
        String res = "";
        int bonusKey = this.bonus2.getBonusKey();
        if (bonusKey==0) {res="None";}
        if (bonusKey==1) {res="The Punisher";}
        if (bonusKey==2) {res="The Nurse";}
        if (bonusKey==3) {res="Mr. Propre";}
        return res;
    }

    public String getDescriptionBonus1() {
        String res = "";
        int bonusKey = this.bonus1.getBonusKey();
        if (bonusKey==0) {res="None";}
        if (bonusKey==1) {res="The Punisher: an invincible square !";}
        if (bonusKey==2) {res="The Nurse: it gives 3 extra\nsquares of each color";}
        if (bonusKey==3) {res="Mr. Propre: all the existing squares\non the field are destroyed";}
        return res;
    }

    public String getDescriptionBonus2() {
        String res = "";
        int bonusKey = this.bonus2.getBonusKey();
        if (bonusKey==0) {res="None";}
        if (bonusKey==1) {res="The Punisher: an invincible square!";}
        if (bonusKey==2) {res="The Nurse: it gives you 3 extra\nsquares of each color";}
        if (bonusKey==3) {res="Mr. Propre: all the existing squares\non the field are destroyed";}
        return res;
    }

    public void AILevelUp() {
        int currentLevel = this.getLevelKey();
        if (currentLevel != 3) {
            switch (levelKey) {
                case 0:
                    setMedium();
                    break;
                case 1:
                    setAdvanced();
                    break;
                case 2:
                    setExpert();
                    break;
            }
        }
    }

    public void AILevelDown(){
        int currentLevel = this.getLevelKey();
        if (currentLevel != 0) {
            switch (levelKey) {
                case 1:
                    setBeginner();
                    break;
                case 2:
                    setMedium();
                    break;
                case 3:
                    setAdvanced();
                    break;
            }
        }
    }

    private void setBeginner(){
        setLevelKey(0);
        setStepX(HEIGHT/6);
        setDtLaunching(80);
        setDtWaves(400);
    }

    private void setMedium(){
        setLevelKey(1);
        setStepX(HEIGHT/6);
        setDtLaunching(60);
        setDtWaves(300);
    }

    private void setAdvanced(){
        setLevelKey(2);
        setStepX(HEIGHT/6);
        setDtLaunching(50);
        setDtWaves(200);
    }

    private void setExpert(){
        setLevelKey(3);
        setStepX(HEIGHT/6);
        setDtLaunching(40);
        setDtWaves(150);
    }
}