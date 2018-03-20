package com.mygdx.game.model;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.graphics.Color;


/**
 * Created by Max on 12/03/2018.
 */

public class CountDown {
    private Integer worldTimer;

    private float timeCount;
    private Label countdownLabel;
    private Boolean timeUp;
    private Label timeLabel;//

    public CountDown() {
        this.worldTimer = 45;
        this.timeCount = 0f;
        this.countdownLabel = new Label(String.format("%03d", worldTimer),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        this.timeUp = false;
    }

    public void increaseTime(){
        setWorldTimer(this.getWorldTimer()+15);
    }

    public void decreaseTime(){
        setWorldTimer(this.getWorldTimer()-15);
    }

    public Integer getWorldTimer() {
        return worldTimer;
    }

    public void setWorldTimer(Integer worldTimer) {
        this.worldTimer = worldTimer;
    }

    public float getTimeCount() {
        return timeCount;
    }

    public void setTimeCount(float timeCount) {
        this.timeCount = timeCount;
    }

    public Label getCountdownLabel() {
        return countdownLabel;
    }

    public void setCountdownLabel(Label countdownLabel) {
        this.countdownLabel = countdownLabel;
    }

    public Boolean isTimeUp() {
        return timeUp;
    }

    public void setTimeUp(Boolean timeUp) {
        this.timeUp = timeUp;
    }


    public void update(float dt) {
        this.timeCount += dt;
        if (this.timeCount >= 1) {
            if (this.worldTimer > 0) {
                this.worldTimer--;
            } else {
                this.timeUp = true;
            }
            this.countdownLabel.setText(String.format("%03d", worldTimer));
            this.timeCount = 0;
        }
    }
}