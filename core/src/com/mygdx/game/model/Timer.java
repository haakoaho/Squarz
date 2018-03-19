package com.mygdx.game.model;

/**
 * Created by mathi on 12/03/2018.
 */

public class Timer {
    private int duration;

    public Timer(){
        //default timer setting
        this.duration = 45;
    }

    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public void increment(){
        setDuration(this.getDuration()+15);
    }
    public void decrement(){
        setDuration(this.getDuration()-15);
    }
}
