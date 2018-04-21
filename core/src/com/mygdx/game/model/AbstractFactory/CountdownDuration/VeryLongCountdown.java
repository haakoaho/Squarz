package com.mygdx.game.model.AbstractFactory.CountdownDuration;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class VeryLongCountdown implements ICountdownDuration {
    private Integer worldTimer;

    private float timeCount;
    private Label countdownLabel;
    private Boolean timeUp;
    private Integer timerKey;

    public VeryLongCountdown() {
        this.worldTimer = 60;
        this.timeCount = 0f;
        this.timerKey = 60;

        this.countdownLabel = new Label(String.format("%02d", worldTimer),
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

        public Boolean isTimeUp() {
        return timeUp;
    }

    public Integer getTimerKey() {
        return timerKey;
    }

    public void setTimerKey(Integer timerKey) {
        this.timerKey = timerKey;
    }

    public void update(float dt) {
        this.timeCount += dt;
        if (this.timeCount >= 1) {
            if (this.worldTimer > 0) {
                this.worldTimer--;
            } else {
                this.timeUp = true;
            }
            this.countdownLabel.setText(String.format("%02d", worldTimer));
            this.timeCount = 0;
        }
    }

}
