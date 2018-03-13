package com.mygdx.game.model;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Disposable;


/**
 * Created by Antoine Dc on 12/03/2018.
 */

public class CountDown implements Disposable {
    private Integer worldTimer;
    private float timeCount;
    private Label countdownLabel;
    private Boolean timeUp;

    public CountDown(Integer worldTimer, float timeCount) {
        this.worldTimer = worldTimer;
        this.timeCount = timeCount;
        this.countdownLabel = new Label(String.format("%03d", worldTimer),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        this.timeUp = false;
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

    public Label getCountdownLabel() {
        return countdownLabel;
    }

    public void setCountdownLabel(Label countdownLabel) {
        this.countdownLabel = countdownLabel;
    }

    public Boolean getTimeUp() {
        return timeUp;
    }

    public void setTimeUp(Boolean timeUp) {
        this.timeUp = timeUp;
    }

    @Override
    public void dispose() {

    }
}
