package com.mygdx.game.control.aI;

/**
 * Created by mathi on 06/03/2018.
 */

public class PreferencesSettings {
    //vitesse stepX, Between2launch dt, between2waves
    private int stepX;
    private float dtLaunching;
    private float dtWaves;
    private int squaresize;

    public PreferencesSettings(){
        //default settings
        this.stepX = 6;
        this.dtLaunching = 5;
        this.dtWaves = 100;
        this.squaresize = 2;
    }

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
}
