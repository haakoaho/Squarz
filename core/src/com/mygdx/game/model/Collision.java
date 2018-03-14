package com.mygdx.game.model;

/**
 * Created by Antoine Dc on 14/03/2018.
 */

public class Collision {
    private Square square;

    public Collision(Square square) {
        this.square = square;
    }

    public boolean collision(Square square){
        return ( this.getSquare().getRectangle().overlaps(square.getRectangle()));
    }

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }

    
}
