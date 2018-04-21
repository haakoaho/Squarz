package com.mygdx.game.model.AbstractFactory.CountdownFactory;

import com.mygdx.game.model.AbstractFactory.CountdownDuration.ShortCountdown;
import com.mygdx.game.model.AbstractFactory.CountdownDuration.ICountdownDuration;

public class ShortCountdownFactory implements ICountdownFactory{

    public ICountdownDuration createCountDown() {
        return new ShortCountdown();
    }
}
