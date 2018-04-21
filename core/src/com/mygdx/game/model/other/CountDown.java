package com.mygdx.game.model.other;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.graphics.Color;

public class CountDown {
    private Integer worldTimer;

    private float timeCount;
    private final Label countdownLabel;
    private Boolean timeUp;

    public CountDown(Integer time) {
        this.worldTimer = time;
        this.timeCount = 0f;
        Integer timerKey = time;

        this.countdownLabel = new Label(String.format("%02d", worldTimer),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        this.timeUp = false;
    }

    public Integer getWorldTimer() {
        return worldTimer;
    }

    public Boolean isTimeUp() {
        return timeUp;
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