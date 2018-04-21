package com.mygdx.game.model.AbstractFactory.CountdownFactory;

import com.mygdx.game.model.AbstractFactory.CountdownDuration.ShortCountdown;
import com.mygdx.game.model.AbstractFactory.CountdownDuration.ICountdownDuration;

/**
 * Created by Max on 20/04/2018.
 */

public class ShortCountdownFactory implements ICountdownFactory{

    public ICountdownDuration createCountDown() {
        return new ShortCountdown();
    }
}
