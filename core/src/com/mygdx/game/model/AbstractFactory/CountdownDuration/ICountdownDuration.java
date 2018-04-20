package com.mygdx.game.model.AbstractFactory.CountdownDuration;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * Created by Max on 20/04/2018.
 */

public interface ICountdownDuration {
    void increaseTime();
    void decreaseTime();
    Integer getWorldTimer();
    void setWorldTimer(Integer worldTimer);
    Boolean isTimeUp();
    Integer getTimerKey();
    void setTimerKey(Integer timerKey);
    void update(float dt);
    public void setTimeCount(float timeCount);
    public Label getCountdownLabel();
    public void setCountdownLabel(Label countdownLabel);
    public float getTimeCount();


}
