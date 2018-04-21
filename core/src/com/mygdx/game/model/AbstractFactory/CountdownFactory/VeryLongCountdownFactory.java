package com.mygdx.game.model.AbstractFactory.CountdownFactory;

import com.mygdx.game.model.AbstractFactory.CountdownDuration.VeryLongCountdown;
import com.mygdx.game.model.AbstractFactory.CountdownDuration.ICountdownDuration;

public class VeryLongCountdownFactory implements ICountdownFactory {
    public ICountdownDuration createCountDown() {
        return new VeryLongCountdown();
    }
}
