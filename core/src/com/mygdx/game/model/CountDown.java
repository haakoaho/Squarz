package com.mygdx.game.model;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Created by Antoine Dc on 12/03/2018.
 */

public class CountDown {
    private Integer time;
    private Integer period;

    public CountDown(Integer time) {
        this.time = time;
        this.period = 1;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public void timeUpdate(){
        if ( this.getTime() > 0 ){
            this.setTime(this.getTime() - this.getPeriod());
        }
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }
}
