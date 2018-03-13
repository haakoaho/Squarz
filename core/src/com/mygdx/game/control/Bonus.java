package com.mygdx.game.control;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mathi on 12/03/2018.
 */

public class Bonus {
    private Map<Integer, Texture> bonuses;

    public Bonus(){
        this.bonuses = new HashMap<Integer, Texture>();
    }
}
