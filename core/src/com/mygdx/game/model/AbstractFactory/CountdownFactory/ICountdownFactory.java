package com.mygdx.game.model.AbstractFactory.CountdownFactory;

import com.mygdx.game.model.AbstractFactory.CountdownDuration.ICountdownDuration;

public interface ICountdownFactory {

    ICountdownDuration createCountDown();
}
