package com.mygdx.game.model.AbstractFactory.CountdownFactory;

import com.mygdx.game.model.AbstractFactory.CountdownDuration.LongCountdown;
import com.mygdx.game.model.AbstractFactory.CountdownDuration.ICountdownDuration;

/**
 * Created by Max on 20/04/2018.
 */

public class LongCountdownFactory implements ICountdownFactory {
    @Override
    public ICountdownDuration createCountDown() {
        return new LongCountdown();
    }
}
