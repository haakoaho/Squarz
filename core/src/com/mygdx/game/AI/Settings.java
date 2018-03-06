package com.mygdx.game.AI;

/**
 * Created by mathi on 06/03/2018.
 */

public class Settings {
    //vitesse stepX, Between2launch dt, between2waves
    private int stepX;
    private float dtLaunching;
    private float dtWaves;

    public Settings(){
        //default settings
        this.stepX = 6;
        this.dtLaunching = 5;
        this.dtWaves = 100;
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
