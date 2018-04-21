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
    void setTimeCount(float timeCount);
    Label getCountdownLabel();
    void setCountdownLabel(Label countdownLabel);
    float getTimeCount();


}
