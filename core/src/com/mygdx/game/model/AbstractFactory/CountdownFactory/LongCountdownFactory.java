package com.mygdx.game.model.AbstractFactory.CountdownFactory;

import com.mygdx.game.model.AbstractFactory.CountdownDuration.LongCountdown;
import com.mygdx.game.model.AbstractFactory.CountdownDuration.ICountdownDuration;

public class LongCountdownFactory implements ICountdownFactory {
    public ICountdownDuration createCountDown() {
        return new LongCountdown();
    }
}
