package com.mygdx.game.model.AbstractFactory.CountdownDuration;

public interface ICountdownDuration {

    Integer getWorldTimer();
    void setWorldTimer(Integer worldTimer);
    Boolean isTimeUp();
    Integer getTimerKey();
    void setTimerKey(Integer timerKey);
    void update(float dt);

    float getTimeCount();


}
