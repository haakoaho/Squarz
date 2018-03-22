package com.mygdx.game.model;


import com.badlogic.gdx.graphics.Texture;

public class Opponent extends Player {


    public void incrementOpponent(Texture t, Byte b) {
        String value = b.toString();
        Integer columnKey = Integer.valueOf(value.substring(0,1));
        Integer colorKey = Integer.valueOf(value.substring(2,3));
        super.incrementOpponent( t, columnKey, colorKey);
    }
}
